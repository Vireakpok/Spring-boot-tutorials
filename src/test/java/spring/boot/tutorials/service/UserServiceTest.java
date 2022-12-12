package spring.boot.tutorials.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import com.spring.boot.books.config.ModelMapperConfig;
import com.spring.boot.books.dto.UserDetailsDTO;
import com.spring.boot.books.entity.Role;
import com.spring.boot.books.entity.User;
import com.spring.boot.books.exception.EmailExistException;
import com.spring.boot.books.exception.RoleNotFoundException;
import com.spring.boot.books.repository.RoleRepository;
import com.spring.boot.books.repository.UserRepository;
import com.spring.boot.books.service.UserService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.CollectionUtils;
import spring.boot.tutorials.constant.UserConstant;

@ExtendWith(MockitoExtension.class)
@Slf4j
class UserServiceTest {

  UserService userService;
  @Mock
  UserRepository userRepository;
  @Mock
  RoleRepository roleRepository;

  @BeforeEach
  public void init() {
    this.userService = new UserService(userRepository, roleRepository, new ModelMapperConfig());
  }

  @Test
  void whenSaveExistEmail_thenThrowEmailException() {
    UserDetailsDTO userDetail = UserConstant.MOCK_USER;

    User user = new User();

    user.setName(userDetail.getName());
    user.setEmail(userDetail.getEmail());
    user.setPassword(userDetail.getPassword());
    Role role = new Role();
    role.setName(userDetail.getRoles().get(0).getName());
    user.setRoles(List.of(role));

    lenient().when(userRepository.save(any())).thenReturn(user);
    when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
    Assertions.assertThrows(EmailExistException.class, () -> userService.saveUser(userDetail));
  }

  @Test
  void whenSaveUserWithRoleNotExist_throwRoleNotFoundException() {
    UserDetailsDTO userDetail = UserConstant.MOCK_USER;

    User user = new User();

    user.setName(userDetail.getName());
    user.setEmail(userDetail.getEmail());
    user.setEmail(userDetail.getPassword());

    Role role = new Role();
    role.setName(userDetail.getRoles().get(0).getName());
    user.setRoles(List.of(role));

    Assertions.assertThrows(RoleNotFoundException.class,
        () -> this.userService.saveUser(userDetail), "Role should be not found");
  }

  @Test
  void whenSaveUser_thenSuccess() {
    UserDetailsDTO expect = UserConstant.MOCK_USER;

    Role role = new Role();
    role.setName(expect.getRoles().get(0).getName());

    User user = new User();
    user.setName(expect.getName());
    user.setEmail(expect.getEmail());
    user.setPassword(expect.getPassword());
    user.setRoles(List.of(role));

    when(roleRepository.findByName(anyString())).thenReturn(Optional.of(role));
    when(userRepository.save(any())).thenReturn(user);

    UserDetailsDTO actual = userService.saveUser(expect);

    assertEquals(expect.getRoles().get(0).getName(),
        actual.getRoles().get(0).getName());
    assertEquals(expect.getName(), actual.getName());
    assertEquals(expect.getEmail(), actual.getEmail());
    assertEquals(expect.getPassword(), actual.getPassword());

  }

  @Test
  void whenFindAllUser_thenGetResultNotNull() {
    UserDetailsDTO userDetails = UserConstant.MOCK_USER;

    User user = new User();
    user.setName(userDetails.getName());
    user.setEmail(userDetails.getEmail());
    user.setPassword(userDetails.getPassword());

    Role role = new Role();
    role.setName(userDetails.getRoles().get(0).getName());
    user.setRoles(List.of(role));

    List<User> users = List.of(user);

    when(userRepository.findAll()).thenReturn(users);
    List<UserDetailsDTO> allUsers = userService.getAllUsers();

    assertFalse(CollectionUtils.isEmpty(allUsers), "Result not null");
    assertEquals(1, allUsers.size(), "allUsers should have only 1 size");
  }
}
