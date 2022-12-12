package com.spring.boot.books.service;

import com.spring.boot.books.config.ModelMapperConfig;
import com.spring.boot.books.constant.UserConstant;
import com.spring.boot.books.dto.RoleDTO;
import com.spring.boot.books.dto.UpdateRoleDTO;
import com.spring.boot.books.entity.Role;
import com.spring.boot.books.exception.RoleExistException;
import com.spring.boot.books.exception.RoleNotFoundException;
import com.spring.boot.books.repository.RoleRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {

  private final RoleRepository roleRepository;
  private final ModelMapperConfig config;

  public RoleDTO save(RoleDTO role) {
    if (roleRepository.existsByNameLikeIgnoreCase(role.getName())) {
      throw new RoleExistException(
          role.getName().concat(" ").concat(UserConstant.IS_ALREADY_EXIST));
    }
    roleRepository.save(config.modelMapper().map(role, Role.class));
    return role;
  }

  public RoleDTO updateRole(UpdateRoleDTO user) {
    Role result = roleRepository.findByName(user.getName()).orElseThrow(
        () -> new RoleNotFoundException(
            user.getName().concat(" ").concat(UserConstant.ROLE_NOT_EXIST))
    );
    if (!user.getName().equalsIgnoreCase(user.getNewName())
        && roleRepository.existsByNameLikeIgnoreCase(user.getNewName())) {
      throw new RoleExistException(
          user.getNewName().concat(" ").concat(UserConstant.IS_ALREADY_EXIST));
    }
    result.setName(user.getNewName());
    return config.modelMapper().map(roleRepository.save(result), RoleDTO.class);
  }

  public List<RoleDTO> getRoles() {
    List<Role> results = roleRepository.findAll();
    return results.stream().map(result -> config.modelMapper().map(result, RoleDTO.class)).collect(
        Collectors.toList());
  }
}
