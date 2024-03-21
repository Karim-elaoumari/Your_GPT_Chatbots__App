package com.chatbots.app.services;

import com.chatbots.app.models.entities.ChatEntry;
import com.chatbots.app.models.entities.Data;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface DataService {
    Data saveData(Data data);
    List<Data> findByChatBotId(UUID chatBotId, Pageable pageable);
    void  deleteData(String dataId,Integer userId);
    Integer getAllDataCount(UUID chatBotId);
    List<ChatEntry> getChatEntries(UUID chatBotId, Pageable pageable);
}
