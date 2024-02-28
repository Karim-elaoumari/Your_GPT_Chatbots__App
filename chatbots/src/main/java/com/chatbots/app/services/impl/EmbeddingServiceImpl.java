package com.chatbots.app.services.impl;

import com.chatbots.app.models.entities.Embedding;
import com.chatbots.app.repositories.EmbeddingRepository;
import com.chatbots.app.services.EmbeddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmbeddingServiceImpl implements EmbeddingService{
    private static final float MATCH_THRESHOLD = 0.7f;
    private static final int MATCH_CNT = 3;
    private final EmbeddingClient aiClient;
    private final EmbeddingRepository embeddingRepository;
    private final JdbcClient jdbcClient;
    @Override
    public List<Embedding> searchPlaces(String prompt) {
        List<Double> promptEmbedding = aiClient.embed(prompt);
        JdbcClient.StatementSpec query = jdbcClient.sql(
                        "SELECT e.id, e.text, e.created_at, e.updated_at, 1 - (e.embedding <=> :user_prompt::vector) as embedding " +
                                "FROM embedding e WHERE 1 - (e.embedding <=> :user_prompt::vector) > :match_threshold " +
                                "ORDER BY e.embedding <=> :user_prompt::vector LIMIT :match_cnt")
                .param("user_prompt", promptEmbedding.toArray(new Double[0])) // Use appropriate conversion
                .param("match_threshold", MATCH_THRESHOLD)
                .param("match_cnt", MATCH_CNT);

        return query.query(Embedding.class).list();
    }
    @Override
    public Embedding create(String text){
        List<Double> embededText = aiClient.embed(text);
        double[] embeddingArray = embededText.stream()
                .mapToDouble(Double::doubleValue)
                .toArray();
        return embeddingRepository.save(
                Embedding.builder()
                        .text(text)
                        .embedding(embeddingArray)
                        .build()
        );
    }

}
