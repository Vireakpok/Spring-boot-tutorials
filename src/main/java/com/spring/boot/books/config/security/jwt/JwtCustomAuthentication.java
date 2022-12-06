package com.spring.boot.books.config.security.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtCustomAuthentication {

  public Authentication getAuthentication(JwtRequest jwtRequest,
      AuthenticationManager authenticationManager) {
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        jwtRequest.getUsername(),
        jwtRequest.getPassword());
    return authenticationManager.authenticate(authentication);
  }

}
