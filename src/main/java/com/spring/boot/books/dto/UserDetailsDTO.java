package com.spring.boot.books.dto;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserDetailsDTO extends UserDTO {

  private List<RoleDTO> roles;

  public UserDetailsDTO(
      @NotNull @Size(max = 25, min = 5) String name,
      @NotNull @NotEmpty String email,
      @NotNull @Size(max = 15, min = 8, message = "Password must be at least 8 characters") String password,
      List<RoleDTO> roles) {
    super(name, email, password);
    this.roles = roles;
  }
}
