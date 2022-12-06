package com.spring.boot.books.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.books.config.security.jwt.JwtCustomAuthentication;
import com.spring.boot.books.config.security.jwt.JwtCustomToken;
import com.spring.boot.books.config.security.jwt.JwtConfig;
import com.spring.boot.books.config.security.jwt.JwtRequest;
import com.spring.boot.books.exception.JwtIOException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@AllArgsConstructor
public class UsernameAndPasswordAuthenticationFilter extends
    UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private final JwtConfig jwtConfig;
  private final JwtCustomAuthentication customAuthentication;
  private final JwtCustomToken customToken;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    try {
      JwtRequest jwtRequest = new ObjectMapper().readValue(
          request.getInputStream(), JwtRequest.class);
      return customAuthentication.getAuthentication(jwtRequest, authenticationManager);
    } catch (IOException ex) {
      throw new JwtIOException(ex.getMessage());
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) {
    response.addHeader(HttpHeaders.AUTHORIZATION,
        jwtConfig.getJwtPrefix().concat(customToken.getToken(authResult, jwtConfig)));
  }
}
