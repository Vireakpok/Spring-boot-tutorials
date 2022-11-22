package com.spring.boot.books.service;

import com.spring.boot.books.config.ModelMapperConfig;
import com.spring.boot.books.dto.BookDetailDTO;
import com.spring.boot.books.dto.BookDTO;
import com.spring.boot.books.dto.BookPublicDTO;
import com.spring.boot.books.entity.Book;
import com.spring.boot.books.entity.Category;
import com.spring.boot.books.repository.BookPaginationAndSortRepository;
import com.spring.boot.books.repository.BookRepository;
import com.spring.boot.books.repository.CategoryRepository;
import com.spring.boot.books.service.specification.BookSpecification;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;
  private final ModelMapperConfig config;
  private final BookPaginationAndSortRepository bookPaginationAndSortRepository;
  private final CategoryRepository categoryRepository;

  public List<BookDTO> getAllBooks(String title) {
    List<BookDTO> results;
    boolean isEmptyTitle = StringUtils.isEmpty(title) || StringUtils.isBlank(title);
    if (isEmptyTitle) {
      results = bookRepository.findAll().stream()
          .map(result -> config.modelMapper().map(result, BookDTO.class))
          .collect(Collectors.toList());
    } else {
      results = bookRepository.findByTitleContaining(title).stream()
          .map(result -> config.modelMapper().map(result, BookDTO.class))
          .collect(Collectors.toList());
    }
    return results;
  }

  public List<BookDTO> getBookPaginationSortBy(int pageNo, int pageSize, String sortName,
      boolean isASC) {
    Sort sort = isASC ? Sort.by(sortName).ascending() : Sort.by(sortName).descending();
    Page<Book> bookPagination = getBookPagination(pageNo, pageSize, sort);
    if (bookPagination.hasContent()) {
      List<Book> bookResults = bookPagination.getContent();
      return bookResults.stream().map(result -> config.modelMapper().map(result, BookDTO.class))
          .collect(Collectors.toList());
    }
    return new ArrayList<>();
  }

  public List<BookDTO> getBookFilterByTitle(int pageNo, int pageSize, String title) {
    PageRequest pagination = PageRequest.of(pageNo, pageSize);
    Page<Book> booksPerPage = bookPaginationAndSortRepository.findByTitle(title,
        pagination);
    if (booksPerPage.hasContent()) {
      return booksPerPage.stream()
          .map(result -> config.modelMapper().map(result, BookDTO.class))
          .collect(Collectors.toList());
    }
    return new ArrayList<>();
  }

  public List<BookDTO> getBookFilterByDescription(int pageNo, int pageSize, String description) {
    PageRequest pagination = PageRequest.of(pageNo, pageSize);
    Page<Book> bookPerPage = bookPaginationAndSortRepository.findByDescription(description,
        pagination);
    if (bookPerPage.hasContent()) {
      return bookPerPage.stream().map(result -> config.modelMapper().map(result, BookDTO.class))
          .collect(
              Collectors.toList());
    }
    return new ArrayList<>();
  }

  public List<BookDTO> getBookFilterByContent(int pageNo, int pageSize, String content) {
    PageRequest pagination = PageRequest.of(pageNo, pageSize);
    Page<Book> bookPerPage = bookPaginationAndSortRepository.searchByContent(content.toLowerCase(),
        pagination);
    if (bookPerPage.hasContent()) {
      return bookPerPage.getContent().stream()
          .map(result -> config.modelMapper().map(result, BookDTO.class)).collect(
              Collectors.toList());
    }
    return new ArrayList<>();
  }

  public Page<Book> getBookPagination(int pageNo, int pageSize, Sort sortBy) {
    PageRequest pagination = PageRequest.of(pageNo, pageSize, sortBy);
    return bookPaginationAndSortRepository.findAll(pagination);
  }

  public Optional<BookDTO> getBookById(Long id) {
    return bookRepository.findById(id).stream().findFirst()
        .map(result -> config.modelMapper().map(result, BookDTO.class));
  }

  public Optional<BookDTO> createBook(BookDTO bookDTO) {
    Book book = config.modelMapper().map(bookDTO, Book.class);
    return Optional.of(config.modelMapper().map(bookRepository.save(book), BookDTO.class));
  }

  public BookDetailDTO updateBook(String oldTitle, String newTitle) {
    Optional<Book> book = bookRepository.findByTitle(oldTitle);
    if (book.isPresent()) {
      Book source = book.get();
      source.setTitle(newTitle);
      return config.modelMapper().map(bookRepository.save(source), BookDetailDTO.class);
    }
    return null;
  }

  public BookDetailDTO addBookCategory(String title, String name) {
    Optional<Book> book = bookRepository.findByTitle(title);
    Optional<Category> category = categoryRepository.findByTitle(name);
    if (book.isPresent() && category.isPresent()) {
      Book result = book.get();
      result.setCategory(category.get());
      bookRepository.save(result);
      return config.modelMapper().map(result, BookDetailDTO.class);
    }
    return null;
  }

  public void deleteBook(long id) {
    bookRepository.deleteById(id);
  }

  public void deleteAllBooks() {
    bookRepository.deleteAll();
  }

  public List<BookPublicDTO> findByPublished() {
    return bookRepository.findByPublished(true).stream()
        .map(result -> config.modelMapper().map(result, BookPublicDTO.class))
        .collect(Collectors.toList());
  }

  public List<BookDetailDTO> getBookCategory(String title) {
    Optional<Category> category = categoryRepository.findByTitle(title);
    if (category.isPresent()) {
      List<Book> results = bookPaginationAndSortRepository.findAll(
          BookSpecification.getBookDetail(category.get().getId()));
      return results.stream().map(result -> config.modelMapper().map(result, BookDetailDTO.class))
          .collect(
              Collectors.toList());
    }
    return new ArrayList<>();
  }

  public List<BookDetailDTO> getBookByPrice(long price) {
    List<Book> results = bookPaginationAndSortRepository.findAll(
        BookSpecification.getBookPrice(price));
    if (results.isEmpty()) {
      return new ArrayList<>();
    }
    return results.stream().map(result -> config.modelMapper().map(result, BookDetailDTO.class))
        .collect(
            Collectors.toList());
  }
}
