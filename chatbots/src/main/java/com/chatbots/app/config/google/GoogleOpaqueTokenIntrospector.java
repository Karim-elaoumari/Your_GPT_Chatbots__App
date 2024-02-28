package com.chatbots.app.config.google;

import com.chatbots.app.models.dto.UserInfoDTO;

import com.chatbots.app.services.RoleService;
import com.chatbots.app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class GoogleOpaqueTokenIntrospector  {

    private final WebClient userInfoClient;
    private final UserService userService;
    private final RoleService roleService;

    public UserInfoDTO introspect(String token) {
        UserInfoDTO userInfo = userInfoClient.get()
                .uri( uriBuilder -> uriBuilder
                        .path("/oauth2/v3/userinfo")
                        .queryParam("access_token", token)
                        .build())
                .retrieve()
                .bodyToMono(UserInfoDTO.class)
                .block();
//        Map<String, Object> attributes = new HashMap<>();
//        attributes.put("sub", userInfo.sub());
//        attributes.put("name", userInfo.name());
//        attributes.put("email", userInfo.email());
//        attributes.put("picture", userInfo.picture());
//        User user = userService.findUserByEmail(userInfo.email()).orElse(
//                userService.save(User.builder()
//                                .firstName(userInfo.name())
//                                .lastName(userInfo.name())
//                                .email(userInfo.email())
//                                .provider(RegisterProvider.GOOGLE)
//                                .role(roleService.getRoleByName(UserRole.USER))
//                        .build())
//        );
        return userInfo;
    }
}
