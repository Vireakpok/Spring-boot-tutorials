package com.spring.boot.books.service;


import com.spring.boot.books.config.ModelMapperConfig;
import com.spring.boot.books.dto.BookDTO;
import com.spring.boot.books.entity.Book;
import com.spring.boot.books.repository.BookRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  @Autowired
  private BookRepository bookRepository;
  @Autowired
  ModelMapperConfig config;

  public List<BookDTO> getAllBooks(String title) {
    List<BookDTO> results = new ArrayList<BookDTO>();
    if (title == null) {
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

  public Optional<BookDTO> getBookById(Long id) {
    return bookRepository.findById(id).stream().findFirst()
        .map(result -> config.modelMapper().map(result, BookDTO.class));
  }

  public BookDTO createBook(BookDTO book) {
    bookRepository.save(config.modelMapper().map(book, Book.class));
    return book;
  }

  public BookDTO updateBook(long id, BookDTO tutorialDTO) {
    Optional<Book> foundTutorial = bookRepository.findById(id);
    if (foundTutorial.isPresent()) {
      Book source = foundTutorial.get();
      config.modelMapper().map(tutorialDTO, source);
      source.setId(id);
      return config.modelMapper().map(bookRepository.save(source), BookDTO.class);
    }
    return null;
  }

  public void deleteBook(long id) {
    bookRepository.deleteById(id);
  }

  public void deleteAllBooks() {
    bookRepository.deleteAll();
  }

  public List<BookDTO> findByPublished() {
    return bookRepository.findByPublished(true).stream()
        .map(result -> config.modelMapper().map(result, BookDTO.class))
        .collect(Collectors.toList());
  }
}
