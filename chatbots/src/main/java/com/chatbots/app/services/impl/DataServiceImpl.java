package com.chatbots.app.services.impl;

import com.chatbots.app.models.entities.ChatBot;
import com.chatbots.app.models.entities.ChatEntry;
import com.chatbots.app.models.entities.Data;
import com.chatbots.app.repositories.ChatBotRepository;
import com.chatbots.app.repositories.ChatEntryRepository;
import com.chatbots.app.repositories.DataRepository;

import com.chatbots.app.services.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {
    private final DataRepository dataRepository;
    private final ChatBotRepository chatBotRepository;
    private final JdbcClient jdbcClient;
    private final ChatEntryRepository chatEntryRepository;

    @Override
    public Data saveData(Data data){
        return dataRepository.save(data);
    }
    @Override
    public List<Data> findByChatBotId(UUID chatBotId, Pageable pageable){
        return dataRepository.findAllByChatBotId(chatBotId,pageable).getContent();
    }
    @Override
    public void deleteData(String dataId,Integer userId) {
        Data data = dataRepository.findById(Long.parseLong(dataId)).orElseThrow(()->new RuntimeException("Data not found"));
        ChatBot chatBot = chatBotRepository.findByIdAndDeletedIs(data.getChatBotId(),false).orElseThrow(()->new RuntimeException("ChatBot not found"));
        if(chatBot.getUser().getId()!=userId){
            throw new RuntimeException("You are not allowed to delete this data");
        }
        deleteEmbeddings(Long.parseLong(dataId));
        dataRepository.deleteById(Long.parseLong(dataId));
    }
    @Override
    public Integer getAllDataCount(UUID chatBotId) {
        List<Data> datas = dataRepository.findAllByChatBotId(chatBotId,null).getContent();
        Integer count = datas.stream().map(Data::getCharactersCount).reduce(0, Integer::sum);
        return count;
    }
    @Override
    public List<ChatEntry> getChatEntries(UUID chatBotId, Pageable pageable){
        return chatEntryRepository.findAllByChatBotId(chatBotId,pageable).getContent();
    }
    private void deleteEmbeddings(Long dataId) {
        JdbcClient.StatementSpec query = jdbcClient.sql(
                "DELETE FROM embedding WHERE data_id = :data_id ")
                .param("data_id", dataId);
    }
}
