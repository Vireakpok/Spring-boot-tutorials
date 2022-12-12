package com.spring.boot.books.repository;

import com.spring.boot.books.entity.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

  List<Book> findByPublished(boolean published);
  @Query("SELECT b FROM Book b where lower(b.title) LIKE lower(:title)")
  Optional<Book> findByTitle(String title);
  List<Book> findByTitleContaining(String title);
  List<Book> findByDescription(String description);
}
