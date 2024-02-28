package com.chatbots.app.SecurityExceptionsHandlers;

import com.chatbots.app.SecurityExceptionsHandlers.exceptions.ResourceNotFoundException;
import com.chatbots.app.SecurityExceptionsHandlers.exceptions.TokenException;
import com.chatbots.app.SecurityExceptionsHandlers.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class SecurityControllerAdvice {
    @ExceptionHandler(TokenException.class)
    public ResponseEntity handleRefreshTokenException(TokenException ex, WebRequest request){
        return ResponseMessage.badRequest(ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String,Map<Integer,String>> error  = new HashMap<>();
        Map<Integer,String> errors  = new HashMap<>();
        for (int i = 0; i < ex.getBindingResult().getFieldErrors().size(); i++) {
            errors.put(i,ex.getBindingResult().getFieldErrors().get(i).getDefaultMessage());
        }
        error.put("errors",errors);
        ResponseMessage message = ResponseMessage.builder()
                .message("Validation failed")
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .data(error).build();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ResponseMessage message = ResponseMessage.builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now()).build();

        return new ResponseEntity<ResponseMessage>(message, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public  ResponseEntity<ResponseMessage> mainException(Exception ex, WebRequest request){
        ResponseMessage message = ResponseMessage.builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now()).build();
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
