package com.chatbots.app.controllers;

import com.chatbots.app.SecurityExceptionsHandlers.response.ResponseMessage;
import com.chatbots.app.helpers.UserPrincipleHelper;
import com.chatbots.app.models.entities.ChatBot;
import com.chatbots.app.models.entities.User;
import com.chatbots.app.services.DataService;
import com.chatbots.app.services.impl.ChatBotServiceImpl;
import com.chatbots.app.services.impl.EmbeddingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    private final EmbeddingServiceImpl embeddingService;
    private final ChatBotServiceImpl chatBotService;
    private final UserPrincipleHelper userPrincipleHelper;
    private final DataService dataService;

    @GetMapping("/chatbot/data-characters/{chatBotId}")
    public ResponseEntity getChatBotDataCaracters(@PathVariable String chatBotId){
        ChatBot chatBot = chatBotService.getChatBotById(UUID.fromString(chatBotId),false,null);
        User user = userPrincipleHelper.getUserPrincipalFromContextHolder();
        if(chatBot.getUser().getId()==user.getId()){
            return ResponseMessage.ok(dataService.getAllDataCount(chatBot.getId()),"caracters count");
        }else {
            return ResponseMessage.forbidden("you are not allowed to access this resource");
        }
    }
    @GetMapping("chatbot/data/{chatBotId}")
    public ResponseEntity getChatBotData(@PathVariable String chatBotId){
        ChatBot chatBot = chatBotService.getChatBotById(UUID.fromString(chatBotId),false,null);
        User user = userPrincipleHelper.getUserPrincipalFromContextHolder();
        if(chatBot.getUser().getId()==user.getId()){
            return ResponseMessage.ok(dataService.findByChatBotId(chatBot.getId()),"chatbot data");
        }else {
            return ResponseMessage.forbidden("you are not allowed to access this resource");
        }
    }
    @DeleteMapping("chatbot/data/{dataId}")
    public ResponseEntity deleteChatBotData(@PathVariable String dataId){
        dataService.deleteData(dataId);
        return ResponseMessage.ok(null,"data deleted");
    }

}
