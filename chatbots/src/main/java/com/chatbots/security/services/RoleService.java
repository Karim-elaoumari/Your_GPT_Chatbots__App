package com.chatbots.security.services;

import com.chatbots.security.models.entities.Role;
import com.chatbots.security.models.enums.UserRole;

public interface RoleService {

  Role getRoleByName(UserRole name);

}
