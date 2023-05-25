package com.spring.boot.books.service;

import com.spring.boot.books.config.ModelMapperConfig;
import com.spring.boot.books.constant.BookConstant;
import com.spring.boot.books.dto.BookDTO;
import com.spring.boot.books.dto.BookDetailDTO;
import com.spring.boot.books.dto.BookPublicDTO;
import com.spring.boot.books.entity.Book;
import com.spring.boot.books.entity.Category;
import com.spring.boot.books.exception.BookNoContentException;
import com.spring.boot.books.exception.BookNotFoundException;
import com.spring.boot.books.repository.BookPaginationAndSortRepository;
import com.spring.boot.books.repository.BookRepository;
import com.spring.boot.books.repository.CategoryRepository;
import com.spring.boot.books.service.specification.BookSpecification;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
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
    throw new BookNoContentException(BookConstant.CAN_NOT_SORT_BY_TITLE);
  }

  public List<BookDTO> getBookFilterByTitle(int pageNo, int pageSize, String title) {
    PageRequest pagination = PageRequest.of(pageNo, pageSize);
    Page<Book> booksPerPage = bookPaginationAndSortRepository.findByTitle(title,
        pagination);
    if (booksPerPage.isEmpty()) {
      throw new BookNotFoundException(BookConstant.BOOK_TITLE_NOT_FOUND.concat(" ").concat(title));
    }
    return booksPerPage.stream()
        .map(result -> config.modelMapper().map(result, BookDTO.class))
        .collect(Collectors.toList());
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
    throw new BookNotFoundException(
        BookConstant.BOOK_NOT_FOUND_WITH_DESCRIPTION.concat(" ").concat(description));
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
    throw new BookNotFoundException(
        BookConstant.BOOK_NOT_FOUND_WITH_CONTENT.concat(" ").concat(content));
  }

  public Page<Book> getBookPagination(int pageNo, int pageSize, Sort sortBy) {
    PageRequest pagination = PageRequest.of(pageNo, pageSize, sortBy);
    return bookPaginationAndSortRepository.findAll(pagination);
  }

  public BookDTO getBookById(Long id) {
    Optional<Book> results = bookRepository.findById(id);
    if (results.isEmpty()) {
      throw new BookNotFoundException(
          BookConstant.BOOK_NOT_FOUND_WITH_ID.concat(" ").concat(id.toString()));
    }
    return config.modelMapper().map(results.get(), BookDTO.class);
  }

  public BookDTO createBook(BookDTO bookDTO) {
    Book book = config.modelMapper().map(bookDTO, Book.class);
    return config.modelMapper().map(bookRepository.save(book), BookDTO.class);
  }

  public BookDetailDTO updateBook(String oldTitle, String newTitle) {
    Optional<Book> book = bookRepository.findByTitle(oldTitle);
    if (book.isEmpty()) {
      throw new BookNotFoundException(
          BookConstant.BOOK_CAN_NOT_UPDATE_WITH_TITLE.concat(" ").concat(oldTitle));
    }
    Book source = book.get();
    source.setTitle(newTitle);
    return config.modelMapper().map(bookRepository.save(source), BookDetailDTO.class);
  }

  public BookDetailDTO updateBookCategory(String title, String categoryTitle) {
    Optional<Book> book = bookRepository.findByTitle(title);
    if (book.isEmpty()) {
      throw new BookNotFoundException(
          BookConstant.BOOK_CAN_NOT_UPDATE_WITH_TITLE.concat(" ").concat(title));
    }
    Optional<Category> category = categoryRepository.findByTitle(categoryTitle);
    if (category.isEmpty()) {
      throw new BookNotFoundException(
          BookConstant.BOOK_CAN_NOT_UPDATE_WITH_CATEGORY_TITLE.concat(" ").concat(categoryTitle));
    }
    Book result = book.get();
    result.setCategory(category.get());
    bookRepository.save(result);
    return config.modelMapper().map(result, BookDetailDTO.class);
  }

  public void deleteBook(long id) {
    bookRepository.deleteById(id);
  }

  public void deleteAllBooks() {
    bookRepository.deleteAll();
  }

  public List<BookPublicDTO> findByPublished() {
    List<Book> results = bookRepository.findByPublished(true);
    if (results.isEmpty()) {
      return new ArrayList<>();
    }
    return results.stream()
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
    throw new BookNotFoundException(
        BookConstant.BOOK_CAN_NOT_UPDATE_WITH_CATEGORY_TITLE.concat(" ").concat(title));
  }

  public List<BookDetailDTO> getBookByPrice(long price) {
    List<Book> results = bookPaginationAndSortRepository.findAll(
        BookSpecification.getBookPrice(price));
    if (results.isEmpty()) {
      throw new BookNotFoundException(
          BookConstant.BOOK_HAVE_NO_PRICE_WITH.concat(" ").concat(String.valueOf(price)));
    }
    return results.stream().map(result -> config.modelMapper().map(result, BookDetailDTO.class))
        .collect(
            Collectors.toList());
  }
}
