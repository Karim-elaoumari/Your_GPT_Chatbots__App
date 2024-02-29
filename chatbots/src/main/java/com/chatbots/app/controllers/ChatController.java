package com.chatbots.app.controllers;

import com.chatbots.app.SecurityExceptionsHandlers.response.ResponseMessage;
import com.chatbots.app.helpers.UserPrincipleHelper;
import com.chatbots.app.models.dto.ChatBotCreate;
import com.chatbots.app.models.dto.ChatBotUpdate;
import com.chatbots.app.models.dto.ChatQuestionRequest;
import com.chatbots.app.models.dto.ChatTextEmbeddingRequest;
import com.chatbots.app.models.entities.User;
import com.chatbots.app.services.impl.ChatBotServiceImpl;
import com.chatbots.app.services.impl.EmbeddingServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/main")
@RequiredArgsConstructor
public class ChatController {
    private final EmbeddingServiceImpl embeddingService;
    private final ChatBotServiceImpl chatBotService;
    private final UserPrincipleHelper userPrincipleHelper;
    @PostMapping("/embed/text")
    public ResponseEntity createEmbedding(@Valid @RequestBody ChatTextEmbeddingRequest request){
        User user = userPrincipleHelper.getUserPrincipalFromContextHolder();
        if(user==null){
            return ResponseMessage.notFound("user not in context");
        }
        request = new ChatTextEmbeddingRequest(request.text(), request.chatBotId(), user.getId());
        return  ResponseMessage.ok(embeddingService.create(request),"embeddings created");
    }
    @PostMapping("/embed/document/{chatBotId}/")
    public ResponseEntity createEmbeddingFromDocument(@RequestParam("file") MultipartFile file,@Valid @Pattern(regexp = "^[a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{12}$", message = "Invalid UUID") @PathVariable("chatBotId") UUID chatBotId){
        User user = userPrincipleHelper.getUserPrincipalFromContextHolder();
        if(user==null){
            return ResponseMessage.notFound("user not in context");
        }
        return  ResponseMessage.ok(embeddingService.createFromDocument(user.getId(),chatBotId,file),"embeddings created");
    }
    @GetMapping("question")
    public ResponseEntity getResponse(@Valid @RequestBody ChatQuestionRequest request, HttpServletRequest httpRequest){
        return ResponseMessage.ok(chatBotService.getResponse(request,httpRequest.getHeader("Origin")),"response from chat bot");
    }
    @PostMapping("chatbot")
    public ResponseEntity createChatBot(@Valid @RequestBody ChatBotCreate request){
        return ResponseMessage.ok(chatBotService.createChatBot(request.toChatBot()),"chat bot created");
    }
    @PutMapping("chatbot")
    public ResponseEntity updateChatBot(@Valid @RequestBody ChatBotUpdate request){
        return ResponseMessage.ok(chatBotService.updateChatBot(request.toChatBot()),"chat bot updated");
    }

}
