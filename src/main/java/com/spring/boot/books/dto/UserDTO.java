package com.spring.boot.books.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

  @NotNull
  @Size(max = 25, min = 5)
  private String name;
  @NotNull
  @UniqueElements()
  private String email;
  @NotNull
  @Size(max = 15, min = 8, message = "Password must be at least 8 characters")
  private String password;
}
