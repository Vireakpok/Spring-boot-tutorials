package com.spring.boot.books.config.security;

import com.spring.boot.books.config.security.PasswordBCryptEncoder;
import com.spring.boot.books.constant.UserConstant;
import com.spring.boot.books.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class CustomAuthenticationManager implements AuthenticationManager {

  private final PasswordBCryptEncoder passwordBCryptEncoder;
  private final CustomUserDetailsService customUserDetailsService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    final UserDetails userDetail = customUserDetailsService.loadUserByUsername(
        authentication.getName());
    if (invalidPassword(authentication, userDetail)) {
      log.error(userDetail.getUsername().concat(" ").concat(UserConstant.WRONG_PASSWORD));
      throw new BadCredentialsException(UserConstant.WRONG_PASSWORD);
    }
    return new UsernamePasswordAuthenticationToken(userDetail.getUsername(),
        userDetail.getPassword(), userDetail.getAuthorities());
  }

  public boolean invalidPassword(Authentication authentication, UserDetails userDetails) {
    return !passwordBCryptEncoder.passwordEncoder()
        .matches(authentication.getCredentials().toString(), userDetails.getPassword());
  }
}
