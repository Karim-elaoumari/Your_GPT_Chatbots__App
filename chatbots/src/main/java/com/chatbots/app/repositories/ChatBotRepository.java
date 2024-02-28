package com.chatbots.app.repositories;

import com.chatbots.app.models.entities.ChatBot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface ChatBotRepository extends JpaRepository<ChatBot, UUID> {
}
