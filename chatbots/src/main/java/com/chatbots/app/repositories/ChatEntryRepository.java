package com.chatbots.app.repositories;

import com.chatbots.app.models.entities.ChatEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatEntryRepository extends JpaRepository<ChatEntry, Long> {
    Optional<ChatEntry> findByUserCode(String userCode);
    @Query(value = "SELECT c.* FROM chat_entry c INNER JOIN chat_bot cb ON c.chat_bot_id = cb.id WHERE EXTRACT(MONTH FROM c.created_at) = :month AND EXTRACT(YEAR FROM c.created_at) = :year AND cb.user_id = :userId", nativeQuery = true)
    List<ChatEntry> findByMonthAndYearAndUserId(@Param("month") Integer month, @Param("year") Integer year, @Param("userId") Integer userId);
    Page<ChatEntry> findAllByChatBotId(UUID chatBotId, Pageable pageable);




}
