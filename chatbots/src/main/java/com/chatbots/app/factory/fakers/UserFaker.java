package com.chatbots.app.factory.fakers;

import com.chatbots.app.models.entities.User;
import com.chatbots.app.models.enums.RegisterProvider;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFaker {
    private final Faker faker = new Faker();
    private final PasswordEncoder passwordEncoder;
    public User makeUser(){
        return User.builder()
                .username(faker.name().username())
                .password(passwordEncoder.encode("qwerty1234@Q"))
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .provider(RegisterProvider.EMAIL)
                .email(faker.internet().emailAddress())
                .build();
    }

}
