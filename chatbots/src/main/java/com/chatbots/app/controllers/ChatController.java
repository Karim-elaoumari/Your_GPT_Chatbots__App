package com.chatbots.app.controllers;

import com.chatbots.app.SecurityExceptionsHandlers.response.ResponseMessage;
import com.chatbots.app.services.impl.ChatBotServiceImpl;
import com.chatbots.app.services.impl.EmbeddingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/main")
@RequiredArgsConstructor
public class ChatController {
    private final EmbeddingServiceImpl embeddingService;
    private final ChatBotServiceImpl chatBotService;
//    @GetMapping
//    @PreAuthorize(value = "(hasRole('USER') or hasRole('ADMIN')) and hasAuthority('READ_USER')")
//    public ResponseEntity main() {
//        return ResponseMessage.ok("main content",null);
//    }
//    @GetMapping("/2")
//    @PreAuthorize(value = "(hasRole('USER') or hasRole('ADMIN'))")
//    public ResponseEntity main2() {
//        return ResponseMessage.ok("main content",null);
//    }
    @PostMapping("/{text}")
    public ResponseEntity createEmbedding(@PathVariable("text") String text){
        return  ResponseMessage.ok(embeddingService.create(text),"good");
    }
    @GetMapping
    public  ResponseEntity getEmbedding(){
        return ResponseMessage.ok(embeddingService.searchPlaces("hello"),"good");
    }
    @GetMapping("question/{chatBotId}/{userCode}/{question}")
    public ResponseEntity getResponse(@PathVariable("question") String question,@PathVariable("chatBotId") String chatBotId,@PathVariable("userCode") String userCode){
        return ResponseMessage.ok(chatBotService.getResponse(question,userCode, UUID.fromString(chatBotId)),"good");
    }


}
