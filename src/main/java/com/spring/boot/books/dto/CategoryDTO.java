package com.spring.boot.books.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryDTO {

  @Size(max = 25, min = 5,message = "Criteria for category")
  @NotEmpty
  private String title;
}
