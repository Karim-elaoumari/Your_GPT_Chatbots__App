package com.chatbots.app.repositories;

import com.chatbots.app.models.entities.ChatBot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ChatBotRepository extends JpaRepository<ChatBot, UUID> {
    Optional<ChatBot> findByIdAndUser_Id(UUID id, Integer userId);
    List<ChatBot> findByUser_Id(Integer userId);
}
