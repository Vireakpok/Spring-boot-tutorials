package spring.boot.tutorials.constant;

import com.spring.boot.books.dto.RoleDTO;
import com.spring.boot.books.dto.UpdateRoleDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleConstant {

  public static final String URL = "/api/v1/role";
  public static final RoleDTO ROLE_DTO = new RoleDTO("ROLE_CUSTOMER");
  public static final RoleDTO NEW_ROLE_DTO = new RoleDTO("ROLE_CLIENT");
  public static final UpdateRoleDTO ROLE_UPDATE_DTO = new UpdateRoleDTO("ROLE_CUSTOMER", "ROLE_CLIENT");
  public static final UpdateRoleDTO ROLE_SELF_UPDATE_DTO = new UpdateRoleDTO("ROLE_CUSTOMER", "ROLE_CUSTOMER");
}
