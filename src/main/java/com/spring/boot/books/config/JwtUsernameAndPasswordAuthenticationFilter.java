package com.spring.boot.books.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.books.constant.JwtConstant;
import com.spring.boot.books.exception.JwtIOException;
import com.spring.boot.books.service.JwtRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@AllArgsConstructor
public class JwtUsernameAndPasswordAuthenticationFilter extends
    UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private final JwtConfig jwtConfig;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    try {
      JwtRequest jwtRequest = new ObjectMapper().readValue(
          request.getInputStream(), JwtRequest.class);
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          jwtRequest.getUsername(),
          jwtRequest.getPassword());
      return authenticationManager.authenticate(authentication);
    } catch (IOException ex) {
      throw new JwtIOException(ex.getMessage());
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {
    String token = Jwts.builder()
        .setSubject(authResult.getName())
        .claim(JwtConstant.AUTHORITIES, authResult.getAuthorities())
        .setIssuer(jwtConfig.getIssuerUri())
        .setIssuedAt(new Date())
        .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getExpiresDate())))
        .signWith(Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes()))
        .compact();
    response.addHeader(HttpHeaders.AUTHORIZATION, jwtConfig.getJwtPrefix().concat(token));
  }
}
