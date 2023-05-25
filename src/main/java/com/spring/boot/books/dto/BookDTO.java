package com.spring.boot.books.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

  @Size(max = 25, min = 5, message = "criteria not met")
  @NotEmpty
  private String title;
  @Size(max = 255, min = 5)
  @NotEmpty
  private String description;
  private boolean published;
}
