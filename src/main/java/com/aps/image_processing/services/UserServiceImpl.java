package com.aps.image_processing.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aps.image_processing.domain.Role;
import com.aps.image_processing.domain.User;
import com.aps.image_processing.repositories.RoleRepository;
import com.aps.image_processing.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public void addRoleToUser(String username, String roleName) {
    log.info("Adding role '{}' to user '{}'", roleName, username);
    User user = userRepository.findByUsername(username);
    Role role = roleRepository.findByName(roleName);
    user.getRoles().add(role);
  }

  @Override
  public User getUser(String username) {
    log.info("Fetching user '{}'", username);
    return userRepository.findByUsername(username);
  }

  @Override
  public List<User> getUsers() {
    log.info("Fetching all users");
    return userRepository.findAll();
  }

  @Override
  public Role saveRole(Role role) {
    log.info("Saving role '{}' to the database", role.getName());
    return roleRepository.save(role);
  }

  @Override
  public User saveUser(User user) {
    log.info("Saving user '{}' to the database", user.getUsername());
    return userRepository.save(user);
  }

  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);

    if (user == null) {
      log.error("User not found in the database, user: {}", username);
      throw new UsernameNotFoundException("Invalid username or password.");
    }

    log.info("User found in the database, user: {}", username);

    var authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());

    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
        authorities);
  }
}
