package com.chatbots.app.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer textDataCharactersLimit;
    private Integer filesDataCharactersLimit;
    private Integer QuestionsAndAnswersLimit;
    private Integer initialDataCountLimit;
    private Integer dataCountLimit;
    private Integer userQuestionsLimitPerMonth;
    private Integer chatBotsLimit;
    @OneToMany(mappedBy = "subscription", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User> users;
    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime updatedAt;
}
