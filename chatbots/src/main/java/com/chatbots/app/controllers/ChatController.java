package com.chatbots.app.controllers;

import com.chatbots.app.SecurityExceptionsHandlers.response.ResponseMessage;
import com.chatbots.app.helpers.UserPrincipleHelper;
import com.chatbots.app.models.dto.*;
import com.chatbots.app.models.entities.ChatBot;
import com.chatbots.app.models.entities.User;
import com.chatbots.app.services.OriginsService;
import com.chatbots.app.services.impl.ChatBotServiceImpl;
import com.chatbots.app.services.impl.EmbeddingServiceImpl;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.stringtemplate.v4.ST;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/main")
@RequiredArgsConstructor
public class ChatController {
    private final EmbeddingServiceImpl embeddingService;
    private final ChatBotServiceImpl chatBotService;
    private final UserPrincipleHelper userPrincipleHelper;
    private final OriginsService originsService;
    @PostMapping("/embed/text")
    public ResponseEntity createEmbedding(@Valid @RequestBody ChatTextEmbeddingRequest request){
        User user = userPrincipleHelper.getUserPrincipalFromContextHolder();
        ChatBot chatBot = chatBotService.getChatBotById(UUID.fromString(request.chatBotId()),false,null);
        if(chatBot.getUser().getId()!=user.getId()){return ResponseMessage.forbidden("chat bot not owned by user");}

        request = new ChatTextEmbeddingRequest(request.text(), request.chatBotId());

        return  ResponseMessage.ok(embeddingService.create(request,user),"embeddings created");
    }
    @PostMapping("/embed/document/{chatBotId}")
    public ResponseEntity createEmbeddingFromDocument(@RequestParam("file") MultipartFile file, @PathVariable("chatBotId") String chatBotId){
        if(file==null){return ResponseMessage.badRequest("file is required");}
        User user = userPrincipleHelper.getUserPrincipalFromContextHolder();
        ChatBot chatBot = chatBotService.getChatBotById(UUID.fromString(chatBotId),false,null);
        if(chatBot.getUser().getId()!=user.getId()){return ResponseMessage.forbidden("chat bot not owned by user");}
        return  ResponseMessage.ok(embeddingService.createFromDocument(user.getId(),chatBot.getId(),file),"embeddings created");
    }
    @PostMapping("prompt")
    public ResponseEntity postQuestion(@Valid @RequestBody ChatQuestionRequest request){
        return ResponseMessage.ok(chatBotService.getResponse(request),"response from chat bot");
    }
    @PostMapping("chatbot")
    public ResponseEntity createChatBot(@Valid @RequestBody ChatBotCreate request){
        User user = this.userPrincipleHelper.getUserPrincipalFromContextHolder();
        return ResponseMessage.ok(chatBotService.createChatBot(request.toChatBot(),user),"chat bot created");
    }
    @PutMapping("chatbot")
    public ResponseEntity updateChatBot(@Valid @RequestBody ChatBotUpdate request){
        return ResponseMessage.ok(chatBotService.updateChatBot(request.toChatBot()),"chat bot updated");
    }
    @GetMapping("chatbot/ui")
    public ResponseEntity getChatBotUI(@Valid @RequestParam String chatBotId,@RequestParam String origin){
        if(validateUUID(chatBotId)){return ResponseMessage.badRequest("invalid chat bot id");}

        if(origin==null){return ResponseMessage.badRequest("origin is required");}

        ChatBotUiResponse chatBot= ChatBotUiResponse.fromChatBot(chatBotService.getChatBotById(UUID.fromString(chatBotId),true,origin));
        return ResponseMessage.ok(chatBot,"chat bot ui retrieved");
    }
    @GetMapping("chatbot/my")
    public ResponseEntity getMyChatBots(){
        User user = userPrincipleHelper.getUserPrincipalFromContextHolder();

        if(user==null){return ResponseMessage.notFound("user not in context");}

        return ResponseMessage.ok(chatBotService.getMyChatBots(user.getId()),"chat bots retrieved");
    }
    @GetMapping("chatbot/{chatBotId}")
    public ResponseEntity getChatBotById(@PathVariable("chatBotId") String chatBotId){

        User user = userPrincipleHelper.getUserPrincipalFromContextHolder();

        if(user==null){return ResponseMessage.notFound("user not in context");}

        ChatBot chatBot = chatBotService.getChatBotById(UUID.fromString(chatBotId),false,null);

        if(chatBot.getUser().getId()!=user.getId()){return ResponseMessage.forbidden("chat bot not owned by user");}
        return ResponseMessage.ok(chatBot,"chat bot retrieved");
    }
    @GetMapping("chatbot/logo/{chatBotId}")
    public ResponseEntity<String> getChatBotLogo(@PathVariable("chatBotId") String chatBotId){

        ChatBot chatBot = chatBotService.getChatBotById(UUID.fromString(chatBotId),false,null);
        return ResponseEntity.ok(chatBot.getLogoUrl());

    }
    @PostMapping("chatbot/allowed-origins/{chatBotId}")
    public ResponseEntity addAllowedOrigin(@PathVariable("chatBotId") String chatBotId,@RequestBody String origin){
        if(origin==null){return ResponseMessage.badRequest("origin is required");}
        User user = userPrincipleHelper.getUserPrincipalFromContextHolder();
        ChatBot chatBot = chatBotService.getChatBotById(UUID.fromString(chatBotId),false,null);
        if(chatBot.getUser().getId()!=user.getId()){return ResponseMessage.forbidden("chat bot not owned by user");}
        return ResponseMessage.ok(originsService.addChatBotAllowedOrigin(UUID.fromString(chatBotId),origin),"allowed origin added");
    }
    @DeleteMapping("chatbot/allowed-origins/{chatBotId}")
    public ResponseEntity removeAllowedOrigin(@PathVariable("chatBotId") String chatBotId,@RequestParam String origin){
        if(origin==null){return ResponseMessage.badRequest("origin is required");}
        User user = userPrincipleHelper.getUserPrincipalFromContextHolder();
        ChatBot chatBot = chatBotService.getChatBotById(UUID.fromString(chatBotId),false,null);
        if(chatBot.getUser().getId()!=user.getId()){return ResponseMessage.forbidden("chat bot not owned by user");}
        return ResponseMessage.ok(originsService.removeChatBotAllowedOrigin(UUID.fromString(chatBotId),origin),"allowed origin removed");
    }
    @DeleteMapping("chatbot/{chatBotId}")
    public ResponseEntity removeChatbot(@PathVariable("chatBotId") String chatBotId){
        User user = userPrincipleHelper.getUserPrincipalFromContextHolder();
        ChatBot chatBot = chatBotService.getChatBotById(UUID.fromString(chatBotId),false,null);
        if(chatBot.getUser().getId()!=user.getId()){return ResponseMessage.forbidden("chat bot not owned by user");}
         this.chatBotService.deleteChatBot(UUID.fromString(chatBotId));
         return  ResponseMessage.ok(null,"Chat bot deleted");
    }
    private Boolean validateUUID(String uuid){
        if(uuid==null){
            return false;
        }
        return uuid.matches( "^[A-Fa-f0-9]{8}(-[A-Fa-f0-9]{4}){4}[A-Fa-f0-9]{12}$");
    }


}
