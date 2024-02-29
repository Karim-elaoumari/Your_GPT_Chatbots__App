package com.chatbots.app.helpers;

import com.chatbots.app.models.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserPrincipleHelper {
    public User getUserPrincipalFromContextHolder() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof User) {
            return (User) principal;
        }
        return null;
    }
}
