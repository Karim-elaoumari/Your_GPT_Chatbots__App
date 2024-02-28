package com.chatbots.app.repositories;

import com.chatbots.app.models.entities.Embedding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EmbeddingRepository extends JpaRepository<Embedding,Long> {
//    @Query(nativeQuery = true, value =
//            "SELECT id,created_at,updated_at,text,1 - (e.embedding <=> CAST(:userPromptEmbedding AS vector)) as embedding FROM embedding e " +
//                    "WHERE 1 - (e.embedding <=> CAST(:userPromptEmbedding AS vector)) > :matchThreshold " +
//                    "ORDER BY e.embedding <=> CAST(:userPromptEmbedding AS vector) DESC LIMIT :matchCount")
//    List<Embedding> searchEmbeddings(
//            @Param("userPromptEmbedding") double[] userPromptEmbedding,
//            @Param("matchThreshold") double matchThreshold,
//            @Param("matchCount") int matchCount);

}
