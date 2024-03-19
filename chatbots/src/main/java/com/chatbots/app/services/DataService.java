package com.chatbots.app.services;

import com.chatbots.app.models.entities.Data;

import java.util.List;
import java.util.UUID;

public interface DataService {
    Data saveData(Data data);
    List<Data> findByChatBotId(UUID chatBotId);
    void  deleteData(String dataId);
    Integer getAllDataCount(UUID chatBotId);
}
