package com.chatbots.security.models.dto;

import com.chatbots.security.models.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserResponseDTO {
    private Integer id;
    private String email;
    private String fisrtName;
    private String lastName;
    private String picture;
    private String username;
    private TokenResponse token;
    public static UserResponseDTO fromUser(User user, String access_token, String refresh_token){
        return UserResponseDTO.builder()
                .id(user.getId())
                .fisrtName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .picture(user.getPicture())
                .token(TokenResponse.builder()
                        .accessToken(access_token)
                        .refreshToken(refresh_token)
                        .build())
                .build();

    }
}
