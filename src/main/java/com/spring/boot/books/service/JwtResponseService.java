package com.spring.boot.books.service;

import com.spring.boot.books.config.security.jwt.JwtCustomAuthentication;
import com.spring.boot.books.config.security.CustomAuthenticationManager;
import com.spring.boot.books.config.security.jwt.JwtCustomToken;
import com.spring.boot.books.config.security.jwt.JwtConfig;
import com.spring.boot.books.config.security.jwt.JwtRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtResponseService {

  private final CustomAuthenticationManager authenticationManager;
  private final JwtConfig jwtConfig;
  private final JwtCustomAuthentication customAuthentication;
  private final JwtCustomToken customToken;

  public String getHeaderToken(JwtRequest request) {
    Authentication authentication = customAuthentication.getAuthentication(request,
        authenticationManager);
    if (authentication.isAuthenticated()) {
      return jwtConfig.getJwtPrefix()
          .concat(customToken.getToken(authentication, jwtConfig));
    }
    return null;
  }
}
