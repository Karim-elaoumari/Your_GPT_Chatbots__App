package com.chatbots.app.models.entities;

import com.chatbots.app.models.enums.RegisterProvider;
import com.chatbots.app.models.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private Long id;
    private  String name;
    private  String email;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Enumerated(EnumType.STRING)
    private RegisterProvider provider;
}
