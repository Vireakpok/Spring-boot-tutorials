package com.spring.boot.books.config.security.jwt;


import static ch.qos.logback.core.CoreConstants.EMPTY_STRING;

import com.spring.boot.books.constant.JwtConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@AllArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

  private final JwtConfig jwtConfig;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws ServletException, IOException {
    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtils.isEmpty(authorizationHeader) || !authorizationHeader.startsWith(
        jwtConfig.getJwtPrefix())) {
      chain.doFilter(request, response);
      return;
    }
    String token = authorizationHeader.replace(jwtConfig.getJwtPrefix(), EMPTY_STRING);
    try {
      Jws<Claims> claimsJws = Jwts.parserBuilder()
          .setSigningKey(Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes())).build()
          .parseClaimsJws(token);
      Claims body = claimsJws.getBody();
      if (!isCurrentIssuer(body.getIssuer())) {
        chain.doFilter(request, response);
        return;
      }
      String username = body.getSubject();
      var authorities = (List<Map<String, String>>) body.get(JwtConstant.AUTHORITIES);
      Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
          .map(result -> new SimpleGrantedAuthority(result.get(JwtConstant.AUTHORITY)))
          .collect(
              Collectors.toSet());
      Authentication authentication = new UsernamePasswordAuthenticationToken(
          username, null, simpleGrantedAuthorities);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (JwtException ex) {
      log.error(ex.getMessage(), ex);
      throw new IllegalStateException("Token %s cannot be truest".concat(" ").concat(token));
    }
    chain.doFilter(request, response);
  }

  private boolean isCurrentIssuer(String issuer) {
    return issuer.startsWith(jwtConfig.getIssuerUri());
  }
}
