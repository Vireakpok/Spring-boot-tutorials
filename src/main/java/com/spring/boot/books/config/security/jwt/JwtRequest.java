package com.spring.boot.books.config.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class JwtRequest {

  private String username;
  private String password;
}
