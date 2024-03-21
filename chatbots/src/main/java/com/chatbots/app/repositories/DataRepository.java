package com.chatbots.app.repositories;

import com.chatbots.app.models.entities.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DataRepository extends JpaRepository<Data,Long> {
    Page<Data> findAllByChatBotId(UUID chatBotId, Pageable pageable);
}
