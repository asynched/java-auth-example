package com.aps.image_processing.services;

import java.util.List;

import com.aps.image_processing.domain.Role;
import com.aps.image_processing.domain.User;

public interface UserService {
  User saveUser(User user);

  Role saveRole(Role role);

  void addRoleToUser(String username, String roleName);

  User getUser(String username);

  List<User> getUsers();
}
