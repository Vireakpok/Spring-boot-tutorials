package com.spring.boot.books.repository;

import com.spring.boot.books.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {

  @Query("SELECT s FROM User s where lower(s.email) LIKE lower(:email)")
  Optional<User> findByEmail(String email);
}
