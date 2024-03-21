package com.chatbots.app.services.impl;

import com.chatbots.app.models.entities.ChatEntry;
import com.chatbots.app.models.entities.Data;
import com.chatbots.app.repositories.ChatBotRepository;
import com.chatbots.app.repositories.ChatEntryRepository;
import com.chatbots.app.repositories.DataRepository;
import com.chatbots.app.services.DataService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DataServiceImplTest {

    @Mock
    private DataRepository dataRepository;


    @Mock
    private ChatEntryRepository chatEntryRepository;

    @InjectMocks
    private DataServiceImpl dataService;
    @Test
    void saveData_Success() {
        Data data = new Data(); // Assuming Data has a no-arg constructor
        when(dataRepository.save(any(Data.class))).thenReturn(data);

        Data savedData = dataService.saveData(data);

        assertNotNull(savedData);
        verify(dataRepository).save(data);
    }
    @Test
    void findByChatBotId_Success() {
        UUID chatBotId = UUID.randomUUID();
        Pageable pageable = Pageable.unpaged();
        List<Data> dataList = List.of(new Data()); // Assuming Data has a no-arg constructor
        Page<Data> page = new PageImpl<>(dataList);

        when(dataRepository.findAllByChatBotId(eq(chatBotId), any(Pageable.class))).thenReturn(page);

        List<Data> result = dataService.findByChatBotId(chatBotId, pageable);

        assertFalse(result.isEmpty());
        assertEquals(dataList.size(), result.size());
        verify(dataRepository).findAllByChatBotId(chatBotId, pageable);
    }
    @Test
    void getChatEntries_Success() {
        UUID chatBotId = UUID.randomUUID();
        Pageable pageable = Pageable.unpaged();
        List<ChatEntry> chatEntryList = List.of(new ChatEntry()); // Assuming ChatEntry has a no-arg constructor
        Page<ChatEntry> page = new PageImpl<>(chatEntryList);

        when(chatEntryRepository.findAllByChatBotId(eq(chatBotId), any(Pageable.class))).thenReturn(page);

        List<ChatEntry> result = dataService.getChatEntries(chatBotId, pageable);

        assertFalse(result.isEmpty());
        assertEquals(chatEntryList.size(), result.size());
        verify(chatEntryRepository).findAllByChatBotId(chatBotId, pageable);
    }

}