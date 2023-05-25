package spring.boot.tutorials.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.spring.boot.books.config.ModelMapperConfig;
import com.spring.boot.books.dto.RoleDTO;
import com.spring.boot.books.entity.Role;
import com.spring.boot.books.exception.RoleExistException;
import com.spring.boot.books.exception.RoleNotFoundException;
import com.spring.boot.books.repository.RoleRepository;
import com.spring.boot.books.service.RoleService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.boot.tutorials.constant.RoleConstant;

@ExtendWith(MockitoExtension.class)
@Slf4j
class RoleServiceTest {

  RoleService roleService;
  @Mock
  RoleRepository roleRepository;

  @BeforeEach
  public void init() {
    this.roleService = new RoleService(roleRepository, new ModelMapperConfig());
  }

  @Test
  void whenSaveExistRole_thenThrowRoleExistException() {
    when(roleRepository.existsByNameLikeIgnoreCase(anyString())).thenReturn(true);
    Assertions.assertThrows(RoleExistException.class,
        () -> roleService.save(RoleConstant.ROLE_DTO));
  }

  @Test
  void whenAddRole_thenSucceed() {
    Role role = new Role();
    role.setName(RoleConstant.ROLE_DTO.getName());

    when(roleRepository.save(any())).thenReturn(role);

    RoleDTO actual = roleService.save(RoleConstant.ROLE_DTO);

    assertEquals(RoleConstant.ROLE_DTO, actual);
  }

  @Test
  void whenUpdateExistRole_thenThrowRoleExistException() {
    Role oldRole = new Role();
    oldRole.setName(RoleConstant.ROLE_DTO.getName());

    Role newRole = new Role();
    newRole.setName(RoleConstant.NEW_ROLE_DTO.getName());

    when(roleRepository.existsByNameLikeIgnoreCase(anyString())).thenReturn(true);
    when(roleRepository.findByName(anyString())).thenReturn(Optional.of(oldRole));

    Assertions.assertThrows(RoleExistException.class,
        () -> roleService.updateRole(RoleConstant.ROLE_UPDATE_DTO));
  }

  @Test
  void whenUpdateFindNameNotFound_thenThrowRoleNotFoundException() {
    Role role = new Role();
    role.setName(RoleConstant.ROLE_DTO.getName());

    when(roleRepository.findByName(anyString())).thenReturn(Optional.empty());

    Assertions.assertThrows(RoleNotFoundException.class,
        () -> roleService.updateRole(RoleConstant.ROLE_UPDATE_DTO));
  }

  @Test
  void whenSelfUpdate_thenReturnSuccess() {
    Role oldRole = new Role();
    oldRole.setName(RoleConstant.ROLE_DTO.getName());

    when(roleRepository.findByName(any())).thenReturn(Optional.of(oldRole));
    when(roleRepository.save(any())).thenReturn(oldRole);
    RoleDTO actual = roleService.updateRole(RoleConstant.ROLE_SELF_UPDATE_DTO);

    assertEquals(RoleConstant.ROLE_DTO.getName(), actual.getName());
  }

  @Test
  void whenGetRole_thenReturnAllRoles() {

    Role role = new Role();
    role.setName(RoleConstant.ROLE_DTO.getName());

    when(roleRepository.findAll()).thenReturn(List.of(role));
    List<RoleDTO> actual = roleService.getRoles();

    assertNotNull(actual);
    assertEquals(1, actual.size());
    assertEquals(RoleConstant.ROLE_DTO.toString(), actual.get(0).toString());
  }

  @Test
  void whenUpdateRole_thenSuccess() {

    Role oldRole = new Role();
    oldRole.setName(RoleConstant.ROLE_DTO.getName());

    when(roleRepository.save(any())).thenReturn(oldRole);
    when(roleRepository.findByName(anyString())).thenReturn(Optional.of(oldRole));
    Role newRole = new Role();
    newRole.setName(RoleConstant.NEW_ROLE_DTO.getName());
    RoleDTO actual = roleService.updateRole(RoleConstant.ROLE_UPDATE_DTO);

    assertNotNull(actual);
    assertEquals(oldRole.getName(), actual.getName());
  }
}
