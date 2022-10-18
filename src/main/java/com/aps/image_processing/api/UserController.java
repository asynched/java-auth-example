package com.aps.image_processing.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.aps.image_processing.domain.Role;
import com.aps.image_processing.domain.User;
import com.aps.image_processing.dto.CreateUserRoleDto;
import com.aps.image_processing.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
  @Autowired
  private UserService userService;

  @GetMapping("/users")
  public ResponseEntity<List<User>> getUsers() {
    return ResponseEntity.ok().body(userService.getUsers());
  }

  @PostMapping("/users")
  public ResponseEntity<User> createUser(@RequestBody User user) {
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
  }

  @PostMapping("/role")
  public ResponseEntity<Role> createRole(@RequestBody Role role) {
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveRole(role));
  }

  @PostMapping("/users/roles")
  public ResponseEntity<?> addRoleToUser(@RequestBody() CreateUserRoleDto createUserRoleDto) {
    userService.addRoleToUser(createUserRoleDto.getUsername(), createUserRoleDto.getRolename());
    return ResponseEntity.ok().build();
  }
}
