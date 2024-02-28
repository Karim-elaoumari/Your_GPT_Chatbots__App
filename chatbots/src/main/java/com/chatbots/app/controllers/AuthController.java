package com.chatbots.app.controllers;

import com.chatbots.app.SecurityExceptionsHandlers.response.ResponseMessage;
import com.chatbots.app.config.google.GoogleOpaqueTokenIntrospector;
import com.chatbots.app.models.dto.*;
import com.chatbots.app.models.entities.User;
import com.chatbots.app.models.enums.RegisterProvider;
import com.chatbots.app.models.enums.UserRole;
import com.chatbots.app.services.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final UserService userService;
    private final GoogleOpaqueTokenIntrospector googleOpaqueTokenIntrospector;
    private final RoleService roleService;

    @Value("${spring.security.oauth2.resourceserver.opaque-token.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.resourceserver.opaque-token.client-secret}")
    private String clientSecret;
    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody UserLoginRequestDTO loginRequest) {
        UserResponseDTO userDTO = authenticationService.login(loginRequest);
        return ResponseMessage.ok(userDTO,"you have authenticated");
    }
    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody UserRegisterRequestDTO registerRequest) {
        if(!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())){
            return ResponseMessage.badRequest("password and confirm password are not the same");
        }
        UserResponseDTO userDTO = authenticationService.register(registerRequest.toUser());
        return ResponseMessage.ok(userDTO,"you have registred");
    }
    @PostMapping("/refresh-token")
    public ResponseEntity refreshToken(@Valid @RequestBody RefreshhTokenRequestDTO request) {
        return ResponseMessage.ok(refreshTokenService.generateNewToken(request.getRefreshToken()),"you have refreshed your token");
    }
    @PostMapping("/logout")
    public ResponseEntity logout(@Valid @RequestBody RefreshhTokenRequestDTO request) {
        refreshTokenService.revokeRefreshToken(request.getRefreshToken());
        return ResponseMessage.ok(null,"you have logged out");
    }
    @GetMapping("/me")
    public ResponseEntity me( ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Object principal = authentication.getPrincipal();
            return ResponseMessage.ok(UserInfoResponseDTO.fromUser((User) principal),"you are authenticated");
    }
    @GetMapping("/google/url")
    public ResponseEntity<UrlDTO> auth() {
        String url = new GoogleAuthorizationCodeRequestUrl(clientId,
                "http://localhost:4200",
                Arrays.asList(
                        "email",
                        "profile",
                        "openid")).build();

        return ResponseEntity.ok(new UrlDTO(url));
    }

    @GetMapping("/google/callback")
    public ResponseEntity<UserResponseDTO> callback(@RequestParam("code") String code) throws URISyntaxException {

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
        try {
            UserInfoDTO userInfoDTO = googleOpaqueTokenIntrospector.introspect(token);
            Optional<User> user = userService.findUserByEmail(userInfoDTO.email());
            if(user.isEmpty()){
                user = Optional.of(userService.save(User.builder()
                        .firstName(userInfoDTO.name())
                        .lastName(userInfoDTO.name())
                        .email(userInfoDTO.email())
                        .provider(RegisterProvider.GOOGLE)
                        .role(roleService.getRoleByName(UserRole.USER))
                        .build()));
            }

            return ResponseEntity.ok(UserResponseDTO.fromUser(user.get(),jwtService.generateToken(user.get()) , refreshTokenService.createRefreshToken(user.get().getId()).getToken()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
