package com.spring.boot.books.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookDTO {
  private String title;
  private String description;
  private boolean published;
}
