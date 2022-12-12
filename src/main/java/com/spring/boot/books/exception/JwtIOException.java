package com.spring.boot.books.exception;

public class JwtIOException extends RuntimeException {

  public JwtIOException(String message) {
    super(message);
  }
}
