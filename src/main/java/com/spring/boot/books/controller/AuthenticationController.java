package com.spring.boot.books.controller;

import com.spring.boot.books.config.security.jwt.JwtRequest;
import com.spring.boot.books.service.JwtResponseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/auth/token")
@AllArgsConstructor
public class AuthenticationController {

  private final JwtResponseService jwtResponse;

  @PostMapping
  public ResponseEntity<String> authentication(@RequestBody JwtRequest request) {
    String headerToken = jwtResponse.getHeaderToken(request);
    return new ResponseEntity<>(headerToken, HttpStatus.OK);
  }
}
