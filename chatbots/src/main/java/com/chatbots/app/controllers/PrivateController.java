package com.chatbots.app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private")
public class PrivateController {

    @GetMapping()
    public ResponseEntity<String> privateMessages(@AuthenticationPrincipal(expression = "name") String name) {
        return ResponseEntity.ok("private content " + name);
    }
}