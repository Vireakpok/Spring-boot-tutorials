package com.spring.boot.books.service;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class JwtResponse implements Serializable {
  private static final long serialVersionUID = -5638263048511548570L;
  private final String jwtToken;
}
