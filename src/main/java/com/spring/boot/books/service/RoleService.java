package com.spring.boot.books.service;

import com.spring.boot.books.config.ModelMapperConfig;
import com.spring.boot.books.constant.UserConstant;
import com.spring.boot.books.dto.RoleDTO;
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

  public RoleDTO updateRole(String oldName, String newName) {
    Role result = roleRepository.findByName(oldName).orElseThrow(
        () -> new RoleNotFoundException(
            oldName.concat(" ").concat(UserConstant.ROLE_NOT_EXIST))
    );
    if (!oldName.equalsIgnoreCase(newName) && roleRepository.existsByNameLikeIgnoreCase(newName)) {
      throw new RoleExistException(
          newName.concat(" ").concat(UserConstant.IS_ALREADY_EXIST));
    }
    result.setName(newName);
    return config.modelMapper().map(roleRepository.save(result), RoleDTO.class);
  }

  public List<RoleDTO> getRoles() {
    List<Role> results = roleRepository.findAll();
    return results.stream().map(result -> config.modelMapper().map(result, RoleDTO.class)).collect(
        Collectors.toList());
  }
}
