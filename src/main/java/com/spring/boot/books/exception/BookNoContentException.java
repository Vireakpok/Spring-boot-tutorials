package com.spring.boot.books.exception;

public class BookNoContentException extends RuntimeException {

  public BookNoContentException(String message) {
    super(message);
  }
}
