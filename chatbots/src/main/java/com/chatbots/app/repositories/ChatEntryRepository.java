package com.chatbots.app.repositories;

import com.chatbots.app.models.entities.ChatEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

public interface ChatEntryRepository extends JpaRepository<ChatEntry, Long> {
    Optional<ChatEntry> findByUserCode(String userCode);
    @Query("SELECT c FROM ChatEntry c WHERE FUNCTION('MONTH', c.createdAt) = :month AND FUNCTION('YEAR', c.createdAt) = :year AND c.chatBot.user.id = :userId")
    List<ChatEntry> findByMonthAndYearAndUserId(@Param("month") Integer month, @Param("year") Integer year, @Param("userId") Integer userId);
}
