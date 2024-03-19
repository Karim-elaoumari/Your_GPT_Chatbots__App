package com.chatbots.app.services.impl;

import com.chatbots.app.models.entities.Data;
import com.chatbots.app.repositories.DataRepository;
import com.chatbots.app.repositories.EmbeddingRepository;
import com.chatbots.app.services.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {
    private final DataRepository dataRepository;
    private final EmbeddingRepository embeddingRepository;
    @Override
    public Data saveData(Data data){
        return dataRepository.save(data);
    }
    @Override
    public List<Data> findByChatBotId(UUID chatBotId) {
        return dataRepository.findDataByChatBotId(chatBotId);
    }
    @Override
    public void deleteData(String dataId) {
        dataRepository.deleteById(Long.parseLong(dataId));
        embeddingRepository.deleteByDataId(Long.parseLong(dataId));
    }
    @Override
    public Integer getAllDataCount(UUID chatBotId) {
        List<Data> datas = dataRepository.findDataByChatBotId(chatBotId);
        Integer count = datas.stream().map(Data::getCharactersCount).reduce(0, Integer::sum);
        return count;
    }
}
