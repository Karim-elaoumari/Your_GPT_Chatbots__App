package com.chatbots.app.services.impl;

import com.chatbots.app.models.dto.ChatTextEmbeddingRequest;
import com.chatbots.app.models.entities.ChatBot;
import com.chatbots.app.models.entities.Embedding;
import com.chatbots.app.repositories.ChatBotRepository;
import com.chatbots.app.repositories.EmbeddingRepository;
import com.chatbots.app.services.EmbeddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmbeddingServiceImpl implements EmbeddingService{
    private static final float MATCH_THRESHOLD = 0.7f;
    private static final int MATCH_CNT = 3;
    private final EmbeddingClient aiClient;
    private final EmbeddingRepository embeddingRepository;
    private final JdbcClient jdbcClient;
    private final ChatBotRepository chatBotRepository;
    @Override
    public List<Embedding> searchPlaces(UUID chatBotId,String prompt) {
        List<Double> promptEmbedding = aiClient.embed(prompt);
        JdbcClient.StatementSpec query = jdbcClient.sql(
                        "SELECT e.id, e.text, e.created_at, e.updated_at, 1 - (e.embedding <=> :user_prompt::vector) as embedding " +
                                "FROM embedding e WHERE 1 - (e.embedding <=> :user_prompt::vector) > :match_threshold " +
                                "AND e.chat_bot_id = :chat_bot_id"+
                                "ORDER BY e.embedding <=> :user_prompt::vector LIMIT :match_cnt")
                .param("user_prompt", promptEmbedding.toArray(new Double[0])) // Use appropriate conversion
                .param("match_threshold", MATCH_THRESHOLD)
                .param("chat_bot_id", chatBotId.toString())
                .param("match_cnt", MATCH_CNT);


        return query.query(Embedding.class).list();
    }
    @Override
    public List<Embedding> create(ChatTextEmbeddingRequest request) {

        ChatBot chatBot = chatBotRepository.findByIdAndUser_Id(UUID.fromString(request.chatBotId()), request.ownerId()).orElseThrow( () -> new RuntimeException("ChatBot not found for this user"));
        List<String> splitedText = new TokenTextSplitter().split(request.text(), 1000);
        List<Embedding> embeddings = new ArrayList<>();
        List<List<Double>> embededText = aiClient.embed(splitedText);
        for (int i = 0; i < splitedText.size(); i++) {
            double[] embeddingArray = embededText.get(i).stream()
                    .mapToDouble(Double::doubleValue)
                    .toArray();
            embeddings.add(
                    Embedding.builder()
                            .text(splitedText.get(i))
                            .embedding(embeddingArray)
                            .chatBot(chatBot)
                            .build()
            );

        }
        return  embeddingRepository.saveAll(embeddings);

    }
    @Override
    public List<Embedding> createFromDocument(Integer ownerId,UUID chatBotId,MultipartFile file){
        List<String> allowedFileTypes = List.of("application/pdf", "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "text/plain", "text/csv", "application/rtf", "text/html", "application/xml", "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        if(!allowedFileTypes.contains(file.getContentType())){
            throw new RuntimeException("File type not allowed");
        }
        ChatBot chatBot = chatBotRepository.findByIdAndUser_Id(chatBotId,ownerId).orElseThrow( () -> new RuntimeException("ChatBot not found for this user"));
         TikaDocumentReader documentReader = new TikaDocumentReader(file.getResource());
         List<Document> documents = documentReader.get();
         List<Document> splitDocuments = new TokenTextSplitter().apply(documents);
         List<Embedding> embeddings = new ArrayList<>();
         for (Document document : splitDocuments) {
             List<Double> embedding = aiClient.embed(document.getContent());
             double[] embeddingArray = embedding.stream()
                     .mapToDouble(Double::doubleValue)
                     .toArray();
             embeddings.add(
                     Embedding.builder()
                             .text(document.getContent())
                             .embedding(embeddingArray)
                             .chatBot(chatBot)
                             .build()
             );
         }
         return embeddingRepository.saveAll(embeddings);

    }

}
