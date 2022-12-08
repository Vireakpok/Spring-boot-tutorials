package spring.boot.tutorials.constant;

import com.spring.boot.books.dto.RoleDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleConstant {

  public static final String URL = "/role";
  public static final RoleDTO ROLE_DTO = new RoleDTO("ROLE_CUSTOMER");
  public static final RoleDTO NEW_ROLE_DTO = new RoleDTO("ROLE_CLIENT");
}
