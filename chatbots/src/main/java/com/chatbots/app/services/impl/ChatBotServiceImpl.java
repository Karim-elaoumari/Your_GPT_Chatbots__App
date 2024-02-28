package com.chatbots.app.services.impl;

import com.chatbots.app.models.entities.ChatBot;
import com.chatbots.app.models.entities.ChatEntry;
import com.chatbots.app.models.entities.ChatEntryHistory;
import com.chatbots.app.models.entities.Embedding;
import com.chatbots.app.repositories.ChatBotRepository;
import com.chatbots.app.repositories.ChatEntryHistoryRepository;
import com.chatbots.app.repositories.ChatEntryRepository;
import com.chatbots.app.services.ChatBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatBotServiceImpl   implements ChatBotService{
    private final EmbeddingServiceImpl embeddingService;
    private final ChatClient chatClient;
    @Value("classpath:templates/assistant-template.st")
    private  Resource assistantTemplate;
    private final ChatBotRepository chatBotRepository;
    private final ChatEntryRepository chatEntryRepository;
    private final ChatEntryHistoryRepository chatEntryHistoryRepository;
    @Override
    public String getResponse(String question, String userCode, UUID chatBotId) {

        String data  = getEmbeddingData(question);

        PromptTemplate promptTemplate = new PromptTemplate(assistantTemplate);
        Map<String, Object> promptParameters = new HashMap<>();
        ChatBot chatBot  = chatBotRepository.findById(chatBotId).orElseThrow( () -> new RuntimeException("ChatBot not found"));
        String instructions = chatBot.getInstructions();
        promptParameters.put("instructions", instructions);
        promptParameters.put("data", data);

        Optional<ChatEntry> chatEntry = chatEntryRepository.findByUserCode(userCode);
        if(chatEntry.isEmpty()){
            chatEntry = Optional.of(createNewChatEntry(userCode, chatBot));
        }
        System.out.println(chatEntry.get().getChatEntryHistories());
        List<Message> messages = new ArrayList<>();
        messages.add(new SystemMessage(promptTemplate.create(promptParameters).getContents()));
        if(chatEntry.get().getChatEntryHistories()!=null && !chatEntry.get().getChatEntryHistories().isEmpty()){
            chatEntry.get().getChatEntryHistories().forEach(chatEntryHistory -> {
                messages.add(new UserMessage(chatEntryHistory.getQuestion()));
                messages.add(new AssistantMessage(chatEntryHistory.getAnswer()));
            });
        }

        messages.add(new UserMessage(question));
        System.out.println(messages);

        Prompt fullPrompt =  new Prompt(messages);
        String answer =  chatClient.call(fullPrompt).getResult().getOutput().getContent();
        saveChatEntryHistory(chatEntry.get(), question, answer);
        return answer;


    }
    private ChatEntry createNewChatEntry(String userCode, ChatBot chatBot){
        ChatEntry chatEntry = ChatEntry.builder()
                .userCode(userCode)
                .chatBot(chatBot)
                .build();
        return chatEntryRepository.save(chatEntry);
    }
    private String getEmbeddingData(String question){
        List<Embedding> embeddings = embeddingService.searchPlaces(question);
        List<Embedding> bestMatches = embeddings.subList(0, Math.min(2, embeddings.size()));
        return bestMatches.stream()
                .map(Embedding::getText)
                .reduce((a, b) -> a + "\n" + b)
                .orElse("");
    }
    private ChatEntryHistory saveChatEntryHistory(ChatEntry chatEntry, String question, String answer){
        ChatEntryHistory chatEntryHistory = ChatEntryHistory.builder()
                .question(question)
                .answer(answer)
                .chatEntry(chatEntry)
                .build();
        return chatEntryHistoryRepository.save(chatEntryHistory);
    }

}
