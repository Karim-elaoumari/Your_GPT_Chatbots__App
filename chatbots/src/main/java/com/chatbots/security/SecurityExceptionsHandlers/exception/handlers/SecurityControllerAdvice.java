package com.chatbots.security.SecurityExceptionsHandlers.exception.handlers;

import com.chatbots.security.SecurityExceptionsHandlers.exception.handlers.exceptions.TokenException;
import com.chatbots.security.SecurityExceptionsHandlers.exception.handlers.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class SecurityControllerAdvice {
    @ExceptionHandler(TokenException.class)
    public ResponseEntity handleRefreshTokenException(TokenException ex, WebRequest request){
        return ResponseMessage.badRequest(ex.getMessage());
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseMessage> authenticationException(BadCredentialsException ex, WebRequest request) {
        ResponseMessage message = ResponseMessage.builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .timestamp(LocalDateTime.now()).build();
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }
}
