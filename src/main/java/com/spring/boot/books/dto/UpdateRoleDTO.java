package com.spring.boot.books.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateRoleDTO extends RoleDTO {

  @NotBlank
  @Size(max = 25, min = 5)
  private String newName;

  public UpdateRoleDTO(String name, String newName) {
    super(name);
    this.newName = newName;
  }
}
