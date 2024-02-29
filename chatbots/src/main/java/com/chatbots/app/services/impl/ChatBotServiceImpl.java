package com.chatbots.app.services.impl;

import com.chatbots.app.models.dto.ChatQuestionRequest;
import com.chatbots.app.models.entities.ChatBot;
import com.chatbots.app.models.entities.ChatEntry;
import com.chatbots.app.models.entities.ChatEntryHistory;
import com.chatbots.app.models.entities.Embedding;
import com.chatbots.app.repositories.ChatBotRepository;
import com.chatbots.app.repositories.ChatEntryHistoryRepository;
import com.chatbots.app.repositories.ChatEntryRepository;
import com.chatbots.app.services.ChatBotService;
import jakarta.validation.Valid;
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
    @Value("${application.frontend.url}")
    private   String  defaultOrigin;
    private final ChatBotRepository chatBotRepository;
    private final ChatEntryRepository chatEntryRepository;
    private final ChatEntryHistoryRepository chatEntryHistoryRepository;
    @Override
    public String getResponse(ChatQuestionRequest request,String origin) {
//        valdiate chatbot request origin
        ChatBot chatBot = chatBotRepository.findById(UUID.fromString(request.chatBotId())).orElseThrow( () -> new RuntimeException("ChatBot not found"));
        if(ValidateOrigin(origin,chatBot)==false){
            throw new RuntimeException("Origin not allowed");
        }
//        get embedding data and create prompt
        String data  = getEmbeddingData(UUID.fromString(request.chatBotId()),request.question());
        PromptTemplate promptTemplate = new PromptTemplate(assistantTemplate);
        Map<String, Object> promptParameters = new HashMap<>();
        String instructions = chatBot.getInstructions();
        promptParameters.put("instructions", instructions);
        promptParameters.put("data", data);
//        get chat entry and create new if not exist
        Optional<ChatEntry> chatEntry = chatEntryRepository.findByUserCode(request.userCode());
        if(chatEntry.isEmpty()){
            chatEntry = Optional.of(createNewChatEntry(request.userCode(), chatBot));
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

        messages.add(new UserMessage(request.question()));
        System.out.println(messages);
//        call chat client and save chat entry history
        Prompt fullPrompt =  new Prompt(messages);
        String answer =  chatClient.call(fullPrompt).getResult().getOutput().getContent();
        saveChatEntryHistory(chatEntry.get(), request.question(), answer);
        return answer;


    }
    private ChatEntry createNewChatEntry(String userCode, ChatBot chatBot){
        ChatEntry chatEntry = ChatEntry.builder()
                .userCode(userCode)
                .chatBot(chatBot)
                .build();
        return chatEntryRepository.save(chatEntry);
    }
    private String getEmbeddingData(UUID chatBotId,String question){
        List<Embedding> embeddings = embeddingService.searchPlaces(chatBotId,question);
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
    @Override
    public ChatBot createChatBot(ChatBot chatBot) {
        chatBot.setInstructions("I want you to act as a support agent. Your name is \"AI Assistant\". You will provide me with answers from the given data. If the answer is not included, say exactly \"Hmm, I am not sure.\" and stop after that. Refuse to answer any question not about (the info or your name,your job,hello word). Never break character.");
        chatBot.setChatBackgroundColor("#ffffff");
        chatBot.setHeaderColor("#000000");
        chatBot.setHeaderTitle("AI Assistant");
        chatBot.setLogoUrl("https://cdn-icons-png.freepik.com/512/8943/8943377.png");
        chatBot.setTextColor("#000000");
        chatBot.setInitialMessage("Hello, I am AI Assistant. I am here to help you with the information you need.");
        return chatBotRepository.save(chatBot);
    }
    @Override
    public ChatBot updateChatBot(ChatBot chatBot) {
        ChatBot chatBotToUpdate = chatBotRepository.findById(chatBot.getId()).orElseThrow( () -> new RuntimeException("ChatBot not found"));
        chatBotToUpdate.setName(chatBot.getName());
        chatBotToUpdate.setInstructions(chatBot.getInstructions());
        chatBotToUpdate.setChatBackgroundColor(chatBot.getChatBackgroundColor());
        chatBotToUpdate.setHeaderColor(chatBot.getHeaderColor());
        chatBotToUpdate.setHeaderTitle(chatBot.getHeaderTitle());
        chatBotToUpdate.setLogoUrl(chatBot.getLogoUrl());
        chatBotToUpdate.setTextColor(chatBot.getTextColor());
        chatBotToUpdate.setInitialMessage(chatBot.getInitialMessage());
        return chatBotRepository.save(chatBotToUpdate);
    }
    private Boolean ValidateOrigin(String origin, ChatBot chatBot){

        if(origin.equals(defaultOrigin)){
            return true;
        }else if(chatBot.getAllowedOrigins()!=null && chatBot.getAllowedOrigins().stream().anyMatch(allowedOrigin -> allowedOrigin.getOrigin().equals(origin))){
            return true;
        }
        return false;
    }
    @Override
    public List<ChatBot> getMyChatBots(Integer userId) {
        return chatBotRepository.findByUser_Id(userId);
    }

}
