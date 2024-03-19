package com.chatbots.app.repositories;

import com.chatbots.app.models.entities.ChatBot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ChatBotRepository extends JpaRepository<ChatBot, UUID> {
    Optional<ChatBot> findByIdAndUser_IdAndDeletedIs(UUID id, Integer userId, Boolean deleted);
    List<ChatBot> findByUser_IdAndDeletedIs(Integer userId, Boolean deleted);
    @Query("SELECT c FROM ChatBot c WHERE c.id = :id AND c.deleted = :deleted")
    Optional<ChatBot> findByIdAndDeletedIs(@Param("id") UUID id, @Param("deleted") Boolean deleted);


}
