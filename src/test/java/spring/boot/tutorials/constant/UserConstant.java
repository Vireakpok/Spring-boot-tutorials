package spring.boot.tutorials.constant;

import com.spring.boot.books.dto.RoleDTO;
import com.spring.boot.books.dto.UpdateRoleDTO;
import com.spring.boot.books.dto.UserDetailsDTO;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class UserConstant {

  public static final String URL = "/api/v1/users";
  public static final String BLANK = "";
  public static final UserDetailsDTO MOCK_USER = new UserDetailsDTO("firestorm",
      "vireaksstorm@gmail.com", "firestorm", List.of(new RoleDTO("ROLE_ADMIN")));
  public static final UserDetailsDTO USER_NAME_UN_VALID = new UserDetailsDTO("ff",
      "vireaksstorm@gmail", "vireak", List.of(new RoleDTO("ROLE_ADMIN")));
  public static final UserDetailsDTO USER_EMAIL_UN_VALID = new UserDetailsDTO("firestorm", BLANK,
      "firestorm",
      List.of(new RoleDTO("ROLE_ADMIN")));
  public static final UserDetailsDTO USER_PASSWORD_UN_VALID = new UserDetailsDTO("firestorm",
      "vireaksstorm@gmail.com",
      BLANK,
      List.of(new RoleDTO("ROLE_ADMIN")));
}
