package com.chatbots.app.repositories;

import com.chatbots.app.models.entities.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DataRepository extends JpaRepository<Data,Long> {
    List<Data> findDataByChatBotId(UUID chatBotId);
}
