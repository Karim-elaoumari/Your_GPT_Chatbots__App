package com.chatbots.security;

import com.chatbots.app.dtos.UserInfoDTO;
import com.chatbots.app.models.entities.UserEntity;
import com.chatbots.app.models.enums.RegisterProvider;
import com.chatbots.app.models.enums.UserRole;
import com.chatbots.app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class GoogleOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    private final WebClient userInfoClient;
    private final UserService userService;

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        UserInfoDTO userInfo = userInfoClient.get()
                .uri( uriBuilder -> uriBuilder
                        .path("/oauth2/v3/userinfo")
                        .queryParam("access_token", token)
                        .build())
                .retrieve()
                .bodyToMono(UserInfoDTO.class)
                .block();
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", userInfo.sub());
        attributes.put("name", userInfo.name());
        attributes.put("email", userInfo.email());
        attributes.put("picture", userInfo.picture());

        UserEntity user = userService.findUserByEmail(userInfo.email()).orElse(
                userService.save(UserEntity.builder()
                                .name(userInfo.name())
                                .email(userInfo.email())
                                .provider(RegisterProvider.GOOGLE)
                                .role(UserRole.USER)
                        .build())
        );
        attributes.put("user_id", user.getId());
        return new OAuth2IntrospectionAuthenticatedPrincipal(userInfo.name(), attributes, List.of(new SimpleGrantedAuthority(user.getRole().name())));
    }
}
