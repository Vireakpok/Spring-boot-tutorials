package com.spring.boot.books.service;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest implements Serializable {

  private static final long serialVersionUID = 3027434573545821044L;
  private String username;
  private String password;
}
