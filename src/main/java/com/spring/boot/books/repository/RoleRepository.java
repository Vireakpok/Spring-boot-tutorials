package com.spring.boot.books.repository;

import com.spring.boot.books.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Integer> {

  @Query("SELECT r FROM Role r where lower(r.name) LIKE lower(:roleName)")
  Optional<Role> findByName(String roleName);

  boolean existsByNameLikeIgnoreCase(String name);
}
