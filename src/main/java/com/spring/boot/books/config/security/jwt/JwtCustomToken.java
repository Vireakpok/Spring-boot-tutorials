package com.spring.boot.books.config.security.jwt;

import com.spring.boot.books.constant.JwtConstant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.LocalDate;
import java.util.Date;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtCustomToken {

  public String getToken(Authentication authResult, JwtConfig jwtConfig) {
    return Jwts.builder()
        .setSubject(authResult.getName())
        .claim(JwtConstant.AUTHORITIES, authResult.getAuthorities())
        .setIssuer(jwtConfig.getIssuerUri())
        .setIssuedAt(new Date())
        .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getExpiresDate())))
        .signWith(Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes()))
        .compact();
  }
}
