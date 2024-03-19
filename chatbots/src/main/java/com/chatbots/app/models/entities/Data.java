package com.chatbots.app.models.entities;

import com.chatbots.app.models.enums.DataType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Currency;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@lombok.Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private DataType type;
    private String name;
    private Integer charactersCount;
    private String source;
    @Column(nullable = false)
    private UUID chatBotId;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;



}
