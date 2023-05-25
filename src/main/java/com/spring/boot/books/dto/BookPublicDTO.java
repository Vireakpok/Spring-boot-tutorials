package com.spring.boot.books.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookPublicDTO extends BookDTO {

  @JsonProperty(access = Access.READ_ONLY)
  private CategoryDTO category;
}
