package com.spring.boot.books.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class RoleDTO {

  public RoleDTO(String name) {
    this.name = name;
  }

  @NotNull
  @Size(max = 255, min = 4)
  private String name;
}
