package com.spring.boot.books.service;

import com.spring.boot.books.config.ModelMapperConfig;
import com.spring.boot.books.constant.UserConstant;
import com.spring.boot.books.dto.UserDetailsDTO;
import com.spring.boot.books.entity.Role;
import com.spring.boot.books.entity.User;
import com.spring.boot.books.exception.EmailExistException;
import com.spring.boot.books.exception.RoleNotFoundException;
import com.spring.boot.books.repository.RoleRepository;
import com.spring.boot.books.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final ModelMapperConfig config;

  public UserDetailsDTO saveUser(UserDetailsDTO user) {
    User userSave = config.modelMapper().map(user, User.class);
    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
      throw new EmailExistException(
          user.getEmail().concat(" ").concat(UserConstant.IS_ALREADY_EXIST));
    }
    List<Role> roles = userSave.getRoles().stream().map(
        role -> {
          Optional<Role> result = roleRepository.findByName(role.getName());
          if (result.isPresent()) {
            return config.modelMapper().map(result, Role.class);
          }
          throw new RoleNotFoundException(UserConstant.ROLE_NOT_EXIST);
        }).collect(
        Collectors.toList());
    userSave.setPassword(new BCryptPasswordEncoder().encode(userSave.getPassword()));
    userSave.setRoles(roles);
    return config.modelMapper().map(userRepository.save(userSave), UserDetailsDTO.class);
  }

  public List<UserDetailsDTO> getAllUsers() {
    List<User> results = userRepository.findAll();
    return results.stream()
        .map(user -> config.modelMapper().map(user, UserDetailsDTO.class))
        .collect(Collectors.toList());
  }
}
