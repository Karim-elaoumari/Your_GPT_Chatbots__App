package com.chatbots.app.services.impl;

import com.chatbots.app.models.dto.ChatQuestionRequest;
import com.chatbots.app.models.entities.*;
import com.chatbots.app.repositories.ChatBotRepository;
import com.chatbots.app.repositories.ChatEntryHistoryRepository;
import com.chatbots.app.repositories.ChatEntryRepository;
import com.chatbots.app.repositories.UserRepository;
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
    @Value("${application.frontend.url}")
    private   String  defaultOrigin;
    private final ChatBotRepository chatBotRepository;
    private final ChatEntryRepository chatEntryRepository;
    private final ChatEntryHistoryRepository chatEntryHistoryRepository;
    private final UserRepository userRepository;
    @Override
    public String getResponse(ChatQuestionRequest request) {
//        valdiate chatbot request origin
        ChatBot chatBot = chatBotRepository.findByIdAndDeletedIs(UUID.fromString(request.chatBotId()),false).orElseThrow( () -> new RuntimeException("ChatBot not found"));

        if(validateOrigin(request.origin(),chatBot)==false){
            throw new RuntimeException("Origin not allowed");
        }
        validateQuestionsLimit(chatBot.getUser());
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
    public ChatBot createChatBot(ChatBot chatBot,User user) {
        User user1 = this.userRepository.findById(user.getId()).orElseThrow(()-> new RuntimeException("User not found "));
        List<ChatBot> chatBots = chatBotRepository.findByUser_IdAndDeletedIs(user1.getId(),false);
        if(chatBots.size()>=user1.getSubscription().getChatBotsLimit()){
            throw new RuntimeException("You have reached the maximum number of chatbots allowed");
        }
        chatBot.setUser(user1);
        chatBot.setDeleted(false);
        chatBot.setInstructions("I want you to act as a support agent. Your name is \"AI Assistant\". You will provide me with answers using the given data. If the answer can't be generated using the given data, say I am not sure. and stop after that. Refuse to answer any question not about (the info or your name,your job,hello word). Never break character.");
        chatBot.setChatBackgroundColor("#f3f6f4");
        chatBot.setMessageBackgroundColor("#ffffff");
        chatBot.setButtonBackgroundColor("#4f46e5");
        chatBot.setUserMessageColor("#E5EDFF");
        chatBot.setBotMessageColor("#ffffff");
        chatBot.setHeaderColor("#ffffff");
        String randomName = UUID.randomUUID().toString().substring(0, 5);
        chatBot.setLogoUrl("https://img.freepik.com/free-vector/chatbot-chat-message-vectorart_78370-4104.jpg");
        chatBot.setTextColor("#362e2e");
        chatBot.setInitialMessage("Hello, I am AI Assistant. I am here to help you with the information you need. \n feel free to ask me anything.");
        return chatBotRepository.save(chatBot);
    }
    @Override
    public ChatBot updateChatBot(ChatBot chatBot) {
        ChatBot chatBotToUpdate = chatBotRepository.findByIdAndDeletedIs(chatBot.getId(),false).orElseThrow( () -> new RuntimeException("ChatBot not found"));
        chatBotToUpdate.setName(chatBot.getName());
        chatBotToUpdate.setInstructions(chatBot.getInstructions());
        chatBotToUpdate.setChatBackgroundColor(chatBot.getChatBackgroundColor());
        chatBotToUpdate.setHeaderColor(chatBot.getHeaderColor());
        chatBotToUpdate.setLogoUrl(chatBot.getLogoUrl());
        chatBotToUpdate.setBotMessageColor(chatBot.getBotMessageColor());
        chatBotToUpdate.setUserMessageColor(chatBot.getUserMessageColor());
        chatBotToUpdate.setMessageBackgroundColor(chatBot.getMessageBackgroundColor());
        chatBotToUpdate.setButtonBackgroundColor(chatBot.getButtonBackgroundColor());
        chatBotToUpdate.setTextColor(chatBot.getTextColor());
        chatBotToUpdate.setInitialMessage(chatBot.getInitialMessage());
        return chatBotRepository.save(chatBotToUpdate);
    }
    @Override
    public Boolean validateOrigin(String encodedOrigin, ChatBot chatBot){
        byte[] decodedBytes = Base64.getDecoder().decode(encodedOrigin);
        String decodedString = new String(decodedBytes);
        if(decodedString.equals(defaultOrigin)){
            return true;
        }else if(chatBot.getAllowedOrigins()!=null && chatBot.getAllowedOrigins().stream().anyMatch(allowedOrigin -> allowedOrigin.getOrigin().equals(decodedString))){
            return true;
        }
        return false;
    }

    @Override
    public List<ChatBot> getMyChatBots(Integer userId) {
        return chatBotRepository.findByUser_IdAndDeletedIs(userId,false);
    }
    @Override
    public ChatBot getChatBotById(UUID chatBotId,Boolean validateOrigin,String origin) {
        ChatBot chatBot =  chatBotRepository.findByIdAndDeletedIs(chatBotId,false).orElseThrow(() -> new RuntimeException("ChatBot not found"));
        if(validateOrigin){
            if(validateOrigin(origin,chatBot)==false){
                throw new RuntimeException("Origin not allowed");
            }
        }
        return chatBot;
    }
    @Override
    public void deleteChatBot(UUID chatbot_id){
        chatBotRepository.deleteById(chatbot_id);
    }
    private void validateQuestionsLimit(User user){
        List<ChatEntry> chatEntries = chatEntryRepository.findByMonthAndYearAndUserId(new Date().getMonth(), new Date().getYear(), user.getId());
        Integer questions = 0;
        for (ChatEntry chatEntry : chatEntries) {
            questions += chatEntry.getChatEntryHistories().size();
        }
        if(questions>=user.getSubscription().getUserQuestionsLimitPerMonth()){
            throw new RuntimeException("You have reached the maximum number of questions allowed");
        }
    }


}
