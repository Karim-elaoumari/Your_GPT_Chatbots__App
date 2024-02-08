package com.chatbots.security.controllers;

import com.chatbots.security.SecurityExceptionsHandlers.exception.handlers.response.ResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/main")

public class MainController {
    @GetMapping
    @PreAuthorize(value = "(hasRole('USER') or hasRole('ADMIN')) and hasAuthority('READ_USER')")
    public ResponseEntity main() {
        return ResponseMessage.ok("main content",null);
    }
    @GetMapping("/2")
    @PreAuthorize(value = "(hasRole('USER') or hasRole('ADMIN'))")
    public ResponseEntity main2() {
        return ResponseMessage.ok("main content",null);
    }
}
