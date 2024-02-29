package com.chatbots.app.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllowedOrigins {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String origin;
    @ManyToOne
    private ChatBot chatBot;
}
