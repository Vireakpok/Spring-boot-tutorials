package com.spring.boot.books.service;

import com.spring.boot.books.constant.UserConstant;
import com.spring.boot.books.entity.Role;
import com.spring.boot.books.repository.UserRepository;
import java.util.Collection;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    com.spring.boot.books.entity.User user = userRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException(
            UserConstant.USER_NOT_FOUND.concat(" ").concat(username)));
    return new User(user.getEmail(), user.getPassword(), getAuthorities(user));
  }

  private static Collection<? extends GrantedAuthority> getAuthorities(
      com.spring.boot.books.entity.User user) {
    String[] userRoles = user.getRoles()
        .stream()
        .map(Role::getName)
        .toArray(String[]::new);
    return AuthorityUtils.createAuthorityList(userRoles);
  }
}
