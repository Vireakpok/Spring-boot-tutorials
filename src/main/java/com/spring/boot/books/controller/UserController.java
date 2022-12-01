package com.spring.boot.books.controller;

import com.spring.boot.books.dto.UserDetailsDTO;
import com.spring.boot.books.service.UserService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@AllArgsConstructor
@Validated
public class UserController {

  private final UserService userService;

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @GetMapping(path = "user")
  public String helloUser() {
    return "Hello User";
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping(path = "admin")
  public String helloAdmin() {
    return "Hello Admin";
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<UserDetailsDTO> save(@RequestBody UserDetailsDTO userDTO) {
    return ResponseEntity.ok(userService.saveUser(userDTO));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  public ResponseEntity<List<UserDetailsDTO>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }
}
