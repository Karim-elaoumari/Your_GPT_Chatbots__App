package com.chatbots.app.repositories;

import com.chatbots.app.models.entities.ChatEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatEntryRepository extends JpaRepository<ChatEntry, Long> {
    Optional<ChatEntry> findByUserCode(String userCode);
}
