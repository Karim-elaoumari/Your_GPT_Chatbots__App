package com.chatbots.security.google;

import com.chatbots.security.models.dto.UserInfoDTO;
import com.chatbots.security.models.entities.User;
import com.chatbots.security.models.enums.RegisterProvider;
import com.chatbots.security.models.enums.UserRole;

import com.chatbots.security.services.RoleService;
import com.chatbots.security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
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
