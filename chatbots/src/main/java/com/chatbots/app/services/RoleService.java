package com.chatbots.app.services;

import com.chatbots.app.models.entities.Role;
import com.chatbots.app.models.enums.UserRole;

public interface RoleService {

  Role getRoleByName(UserRole name);

}
