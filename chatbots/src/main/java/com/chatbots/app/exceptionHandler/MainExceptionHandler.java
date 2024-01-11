package com.chatbots.app.exceptionHandler;

import com.chatbots.app.exceptionHandler.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class MainExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return ResponseMessage.notFound(ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> notValid(MethodArgumentNotValidException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));
        Map<String, List<String>> result = new HashMap<>();
        result.put("errors", errors);
        return ResponseMessage.badRequest("Validation errors", result);
    }
    @ExceptionHandler(OperationException.class)
    public ResponseEntity operationException(OperationException ex, WebRequest request) {

        return ResponseMessage.badRequest(ex.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        ResponseMessage message = ResponseMessage.builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(null).build();
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
