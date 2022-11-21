package com.spring.boot.books.repository;

import com.spring.boot.books.entity.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

  List<Book> findByPublished(boolean published);

  List<Book> findByTitleContaining(String title);
}
