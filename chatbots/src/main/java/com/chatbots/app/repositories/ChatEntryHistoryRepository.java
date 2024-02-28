package com.chatbots.app.repositories;

import com.chatbots.app.models.entities.ChatEntryHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatEntryHistoryRepository extends JpaRepository<ChatEntryHistory, Long>{
}
