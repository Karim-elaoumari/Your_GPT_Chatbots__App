package com.chatbots.app.services.impl;

import com.chatbots.app.models.dto.ChatTextEmbeddingRequest;
import com.chatbots.app.models.entities.ChatBot;
import com.chatbots.app.models.entities.Data;
import com.chatbots.app.models.entities.Embedding;
import com.chatbots.app.models.entities.User;
import com.chatbots.app.models.enums.DataType;
import com.chatbots.app.repositories.ChatBotRepository;
import com.chatbots.app.repositories.EmbeddingRepository;
import com.chatbots.app.repositories.UserRepository;
import com.chatbots.app.services.DataService;
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
    private final UserRepository userRepository;
    private final DataService dataService;
    @Override
    public List<Embedding> searchPlaces(UUID chatBotId,String prompt) {
        List<Double> promptEmbedding = aiClient.embed(prompt);
        JdbcClient.StatementSpec query = jdbcClient.sql(
                        "SELECT e.id, e.text, e.created_at, e.updated_at, 1 - (e.embedding <=> :user_prompt::vector) as embedding " +
                                "FROM embedding e WHERE 1 - (e.embedding <=> :user_prompt::vector) > :match_threshold " +
                                "AND e.chat_bot_id = :chat_id "+
                                "ORDER BY e.embedding <=> :user_prompt::vector LIMIT :match_cnt")
                .param("user_prompt", promptEmbedding.toArray(new Double[0])) // Use appropriate conversion
                .param("match_threshold", MATCH_THRESHOLD)
                .param("chat_id", chatBotId)
                .param("match_cnt", MATCH_CNT);



        return query.query(Embedding.class).list();
    }
    @Override
    public Data create(ChatTextEmbeddingRequest request,User user){
        validateTextDataCharactersLimit(user.getId(), request.text().length(), request.chatBotId());
        ChatBot chatBot = chatBotRepository.findByIdAndUser_IdAndDeletedIs(UUID.fromString(request.chatBotId()), user.getId(),false).orElseThrow( () -> new RuntimeException("ChatBot not found for this user"));
        if( chatBot.getUser().getSubscription().getDataCountLimit()==0){
            throw new RuntimeException("Allowed Data Count limit has been exceeded");
        }
        Data data = saveData(chatBot,null, DataType.TEXT, request.text().substring(0,10), request.text().length());
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
                            .dataId(data.getId())
                            .chatBot(chatBot)
                            .build()
            );

        }
        updateChatBotTrainingStatus(chatBot,true);
        embeddingRepository.saveAll(embeddings);

        return  data;

    }
    @Override
    public Data createFromDocument(Integer ownerId,UUID chatBotId,MultipartFile file){
        List<String> allowedFileTypes = List.of(
                "application/pdf", // PDF
                "application/msword", // Word (Legacy)
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // Word (DOCX)
                "text/plain", // TXT
                "text/csv", // CSV
                "application/rtf", // RTF
                "text/html", // HTML
                "application/xml", // XML
                "application/json", // JSON
                "application/x-yaml", // YAML
                "application/vnd.ms-excel", // Excel (Legacy)
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // Excel (XLSX)
                "application/vnd.ms-powerpoint", // PowerPoint (Legacy)
                "application/vnd.openxmlformats-officedocument.presentationml.presentation"
        );
        if(!allowedFileTypes.contains(file.getContentType())){
            throw new RuntimeException("File type not allowed");
        }

        ChatBot chatBot = chatBotRepository.findByIdAndUser_IdAndDeletedIs(chatBotId,ownerId,false).orElseThrow( () -> new RuntimeException("ChatBot not found for this user"));
        if( chatBot.getUser().getSubscription().getDataCountLimit()==0){
            throw new RuntimeException("Allowed Data Count limit has been exceeded");
        }

         TikaDocumentReader documentReader = new TikaDocumentReader(file.getResource());
         List<Document> documents = documentReader.get();
//          validate the characters limit
         validateDataFilesCharactersLimit(ownerId, documents.stream().mapToInt(d -> d.getContent().length()).sum(), chatBotId.toString());
//         save the data
         Data data = saveData(chatBot,file.getOriginalFilename(), DataType.DOCUMENT, file.getOriginalFilename(), documents.stream().mapToInt(d -> d.getContent().length()).sum());
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
                             .dataId(data.getId())
                             .chatBot(chatBot)
                             .build()
             );
         }
        updateChatBotTrainingStatus(chatBot,true);
        embeddingRepository.saveAll(embeddings);
         return data;
    }
    private void updateChatBotTrainingStatus(ChatBot chatBot,boolean trained){
        chatBot.setTrained(trained);
        chatBotRepository.save(chatBot);
    }
    private  void validateDataFilesCharactersLimit(Integer ownerId, Integer textLength, String chatBotId){
        User user = userRepository.findById(ownerId).orElseThrow( () -> new RuntimeException("User not found"));
        if(textLength > user.getSubscription().getFilesDataCharactersLimit()){
            throw new RuntimeException("The file data characters limit has been exceeded");
        }
    }
    private  void validateTextDataCharactersLimit(Integer ownerId, Integer textLength, String chatBotId){
        User user = userRepository.findById(ownerId).orElseThrow( () -> new RuntimeException("User not found"));
        if(textLength > user.getSubscription().getTextDataCharactersLimit()){
            throw new RuntimeException("The text data characters limit has been exceeded");
        }
    }
    private void updateSupscriptionDataCount(Integer userId){
        User user = userRepository.findById(userId).orElseThrow( () -> new RuntimeException("User not found"));
        user.getSubscription().setDataCountLimit(user.getSubscription().getDataCountLimit() - 1);
        userRepository.save(user);
    }
    private Data saveData(ChatBot chatBot, String source, DataType type, String name, Integer charactersCount){
        Data data = Data.builder()
                .source(source)
                .type(type)
                .chatBotId(chatBot.getId())
                .name(name)
                .charactersCount(charactersCount)
                .build();
        return dataService.saveData(data);
    }

}
