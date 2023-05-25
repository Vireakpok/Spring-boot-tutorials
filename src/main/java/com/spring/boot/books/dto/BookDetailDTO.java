package com.spring.boot.books.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookDetailDTO extends BookDTO {

  private CategoryDetailDTO category;
}
