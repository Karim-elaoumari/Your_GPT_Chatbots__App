package com.chatbots.app.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
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
    @Column(columnDefinition = "boolean default false")
    private Boolean trained;
    @Column(columnDefinition = "TEXT")
    private String instructions;
    private String logoUrl;
    private String initialMessage;
    private String headerColor;
    private String botMessageColor;
    private String userMessageColor;
    private String buttonBackgroundColor;
    private String chatBackgroundColor;
    private String messageBackgroundColor;
    @Column(nullable = false)
    private Float temperature;
    @Column(nullable = false)
    private Boolean deleted = false;
    private String textColor;
    @OneToMany(mappedBy = "chatBot", fetch = FetchType.EAGER)
    private List<AllowedOrigins> allowedOrigins;
    @ManyToOne
    @JsonIgnore
    private User user;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
