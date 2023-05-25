package com.spring.boot.books.config.security;

import com.spring.boot.books.config.security.jwt.JwtAuthenticationEntryPoint;
import com.spring.boot.books.config.security.jwt.JwtConfig;
import com.spring.boot.books.config.security.jwt.JwtCustomAuthentication;
import com.spring.boot.books.config.security.jwt.JwtCustomToken;
import com.spring.boot.books.config.security.jwt.JwtTokenFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtConfig jwtConfig;
  private final CustomAuthenticationManager customAuthenticationManager;
  private final JwtCustomAuthentication customAuthentication;
  private final JwtCustomToken customToken;

  @Bean
  @SuppressWarnings({"java:S4502"})
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/resources/", "/webjars/", "/assets/").permitAll()
        .antMatchers("/api/v1/auth/token").permitAll()
        .antMatchers("/swagger-ui-custom.html", "/api-docs/**", "/swagger-ui/**").permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
        .and()
        .addFilter(
            new UsernameAndPasswordAuthenticationFilter(
                customAuthenticationManager
                , jwtConfig, customAuthentication, customToken))
        .addFilterBefore(new JwtTokenFilter(jwtConfig),
            UsernameAndPasswordAuthenticationFilter.class);
    return http.build();
  }

}
