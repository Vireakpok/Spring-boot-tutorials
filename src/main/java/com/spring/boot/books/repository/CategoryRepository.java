package com.spring.boot.books.repository;

import com.spring.boot.books.entity.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
  @Query("SELECT c FROM Category c where lower(c.title) LIKE lower(:title)")
  Optional<Category> findByTitle(@Param("title") String title);
}
