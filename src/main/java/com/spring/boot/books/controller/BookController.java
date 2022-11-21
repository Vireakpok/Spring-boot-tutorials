package com.spring.boot.books.controller;

import com.spring.boot.books.dto.BookDTO;
import com.spring.boot.books.entity.Book;
import com.spring.boot.books.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class BookController {

  @Autowired
  private BookService bookService;

  @GetMapping(path = "/books")
  public ResponseEntity<List<BookDTO>> getAllBooks(
      @RequestParam(required = false) String title) {
    try {
      List<BookDTO> result = bookService.getAllBooks(title);
      return result.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
          : new ResponseEntity<>(result, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(summary = "Get all book")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "List all books",
          content = {@Content(mediaType = "application/Json",
              schema = @Schema(implementation = Book.class))}
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Book not found",
          content = @Content
      )
  })

  @GetMapping(path = "/books/{id}")
  public ResponseEntity<BookDTO> getBookById(@PathVariable("id") long id) {
    Optional<BookDTO> book = bookService.getBookById(id);
    return book.map(tutorialDTO -> new ResponseEntity<>(tutorialDTO, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping(path = "/books")
  public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
    try {
      return new ResponseEntity<>(bookService.createBook(bookDTO), HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping(path = "/books/{id}")
  public ResponseEntity<BookDTO> updateBook(@PathVariable("id") long id,
      @RequestBody BookDTO bookDTO) {
    BookDTO bookDTOUpdate = bookService.updateBook(id, bookDTO);
    if (bookDTOUpdate != null) {
      return new ResponseEntity<>(bookDTOUpdate, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }
  }

  @DeleteMapping(path = "/books/{id}")
  public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") long id) {
    try {
      bookService.deleteBook(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping(path = "/books")
  public ResponseEntity<HttpStatus> deleteAllBooks() {
    try {
      bookService.deleteAllBooks();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(path = "/books/public")
  public ResponseEntity<List<BookDTO>> findByPublished() {
    try {
      List<BookDTO> books = bookService.findByPublished();
      return books.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
          : new ResponseEntity<>(books, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
