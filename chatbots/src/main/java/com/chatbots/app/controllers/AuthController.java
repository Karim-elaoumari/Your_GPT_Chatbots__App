package com.chatbots.app.controllers;

import com.chatbots.app.dtos.TokenDTO;
import com.chatbots.app.dtos.UrlDTO;
import com.chatbots.app.dtos.UserInfoDTO;
import com.chatbots.app.services.UserService;
import com.chatbots.security.GoogleOpaqueTokenIntrospector;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @Value("${spring.security.oauth2.resourceserver.opaque-token.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.resourceserver.opaque-token.client-secret}")
    private String clientSecret;


    @GetMapping("/auth/url")
    public ResponseEntity<UrlDTO> auth() {
        String url = new GoogleAuthorizationCodeRequestUrl(clientId,
                "http://localhost:4200",
                Arrays.asList(
                        "email",
                        "profile",
                        "openid")).build();

        return ResponseEntity.ok(new UrlDTO(url));
    }

    @GetMapping("/auth/callback")
    public ResponseEntity<TokenDTO> callback(@RequestParam("code") String code) throws URISyntaxException {

        String token;
        try {
            token = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(), new GsonFactory(),
                    clientId,
                    clientSecret,
                    code,
                    "http://localhost:4200"
            ).execute().getAccessToken();


        } catch (IOException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(new TokenDTO(token));
    }
    @GetMapping("/auth/me")
    public ResponseEntity<UserInfoDTO> me(@AuthenticationPrincipal  OAuth2AuthenticatedPrincipal principal) {
        UserInfoDTO userinfo  = new UserInfoDTO(
                principal.getAttribute("user_id"),
                principal.getAttribute("sub"),
                principal.getAttribute("name"),
                principal.getAttribute("picture"),
                principal.getAttribute("email"),
                principal.getAuthorities().stream().findFirst().get().getAuthority()
        );
        return ResponseEntity.ok(userinfo);
    }
}