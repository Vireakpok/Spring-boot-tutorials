package com.spring.boot.books.repository;

import com.spring.boot.books.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookPaginationAndSortRepository extends PagingAndSortingRepository<Book, Long>,
    JpaSpecificationExecutor<Book> {

  @Query("SELECT b FROM Book b where lower(b.title) LIKE lower(:title)")
  Page<Book> findByTitle(@Param("title") String title, Pageable pageable);
  @Query("SELECT b FROM Book b where lower(b.description) LIKE lower(:description)")
  Page<Book> findByDescription(@Param("description") String description, Pageable pageable);
  @Query("SELECT b FROM Book b where lower(b.title) LIKE %:content% OR lower(b.description) LIKE %:content%")
  Page<Book> searchByContent(@Param("content") String content, Pageable pageable);
}
