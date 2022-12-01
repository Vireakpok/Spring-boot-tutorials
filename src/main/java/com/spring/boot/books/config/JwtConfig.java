package com.spring.boot.books.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class JwtConfig {

  @Value("${security.jwt.secret}")
  private String secret;
  @Value("${security.jwt.issuer}")
  private String issuerUri;
  @Value("${security.jwt.expires-date}")
  private Long expiresDate;
  private String jwtPrefix = "Bearer ";
}
