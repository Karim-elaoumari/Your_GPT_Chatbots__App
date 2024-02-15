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
public class UserInfoResponseDTO {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String picture;
    private String username;
    private String provider;
    private String role;
    public static UserInfoResponseDTO fromUser(User user){
        return UserInfoResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .picture(user.getPicture())
                .role(user.getRole()!=null?user.getRole().getName().name():null)
                .provider(user.getProvider()!=null?user.getProvider().name():null)
                .build();


    }

}
