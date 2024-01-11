package com.chatbots.app.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatBot {
    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private Long id;
}
