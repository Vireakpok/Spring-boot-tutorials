package com.spring.boot.books.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConstant {

  public static final String USER_NOT_FOUND = "User not found";
  public static final String IS_ALREADY_EXIST = "is already exist";
  public static final String ROLE_NOT_EXIST = "Role not exist";
  public static final String WRONG_PASSWORD = "wrong password";

}
