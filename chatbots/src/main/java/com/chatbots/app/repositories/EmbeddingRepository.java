package com.chatbots.app.repositories;

import com.chatbots.app.models.entities.Embedding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmbeddingRepository extends JpaRepository<Embedding,Long> {
  List<Embedding> findAllByChatBotId(UUID chatBotId);
  void deleteByDataId(Long dataId);

}
