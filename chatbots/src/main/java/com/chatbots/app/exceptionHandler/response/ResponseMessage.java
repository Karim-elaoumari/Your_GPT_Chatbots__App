package com.chatbots.app.exceptionHandler.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseMessage {
    private int statusCode;
    private LocalDateTime timestamp ;
    private String message;
    private Object data;

    public static ResponseEntity ok(Object data, String message){
           return new ResponseEntity<>(ResponseMessage.builder().data(data).message(message).statusCode(HttpStatus.OK.value()).timestamp(LocalDateTime.now()).build(),HttpStatus.OK);
    }
    public static ResponseEntity notFound(String message){
        return new ResponseEntity<>(ResponseMessage.builder().message(message).statusCode(HttpStatus.NOT_FOUND.value()).timestamp(LocalDateTime.now()).build(),HttpStatus.NOT_FOUND);
    }
    public static ResponseEntity created(Object data, String message){
        return new ResponseEntity<>(ResponseMessage.builder().data(data).message(message).statusCode(HttpStatus.CREATED.value()).timestamp(LocalDateTime.now()).build(),HttpStatus.CREATED);
    }
    public static ResponseEntity badRequest(String message){
        return new ResponseEntity<>(ResponseMessage.builder().message(message).statusCode(HttpStatus.BAD_REQUEST.value()).timestamp(LocalDateTime.now()).build(),HttpStatus.BAD_REQUEST);
    }
    public static ResponseEntity badRequest(String message,Object data){
        return new ResponseEntity<>(ResponseMessage.builder().data(data).message(message).statusCode(HttpStatus.BAD_REQUEST.value()).timestamp(LocalDateTime.now()).build(),HttpStatus.BAD_REQUEST);
    }
    public static ResponseEntity unauthorized(String message){
        return new ResponseEntity<>(ResponseMessage.builder().message(message).statusCode(HttpStatus.UNAUTHORIZED.value()).timestamp(LocalDateTime.now()).build(),HttpStatus.UNAUTHORIZED);
    }
    public static ResponseEntity forbidden(String message){
        return new ResponseEntity<>(ResponseMessage.builder().message(message).statusCode(HttpStatus.FORBIDDEN.value()).timestamp(LocalDateTime.now()).build(),HttpStatus.FORBIDDEN);
    }
    public static ResponseEntity noContent(String message){
        return new ResponseEntity<>(ResponseMessage.builder().message(message).statusCode(HttpStatus.NO_CONTENT.value()).timestamp(LocalDateTime.now()).build(),HttpStatus.NO_CONTENT);
    }



}
