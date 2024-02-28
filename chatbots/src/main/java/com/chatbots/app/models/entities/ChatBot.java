package com.chatbots.app.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatBot {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String instructions;
    private String logoUrl;
    private String initialMessage;
    private String headerTitle;
    private String headerColor;
    private String chatBackgroundColor;
    private String messageBackgroundColor;
    private String textColor;
    @ManyToOne
    private User user;
}
