package com.chatbots.app.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "chatEntry",  fetch = FetchType.EAGER)
    private List<ChatEntryHistory> chatEntryHistories;
    @ManyToOne()
    private ChatBot chatBot;
    private String userCode;
}
