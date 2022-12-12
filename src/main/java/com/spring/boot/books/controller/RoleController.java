package com.spring.boot.books.controller;

import com.spring.boot.books.dto.RoleDTO;
import com.spring.boot.books.dto.UpdateRoleDTO;
import com.spring.boot.books.service.RoleService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/role")
@AllArgsConstructor
@Validated
public class RoleController {

  private final RoleService roleService;

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<RoleDTO> save(@RequestBody RoleDTO role) {
    return ResponseEntity.ok(roleService.save(role));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping
  public ResponseEntity<RoleDTO> updateRole(@RequestBody UpdateRoleDTO user) {
    return ResponseEntity.ok(roleService.updateRole(user));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  public ResponseEntity<List<RoleDTO>> getROles() {
    return ResponseEntity.ok(roleService.getRoles());
  }
}
