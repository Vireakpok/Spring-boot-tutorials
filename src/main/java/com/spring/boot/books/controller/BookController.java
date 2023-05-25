package com.spring.boot.books.controller;

import com.spring.boot.books.dto.BookDetailDTO;
import com.spring.boot.books.dto.BookDTO;
import com.spring.boot.books.dto.BookPublicDTO;
import com.spring.boot.books.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  @GetMapping
  public ResponseEntity<List<BookDTO>> getAllBooks(
      @RequestParam(required = false) String title) {
    List<BookDTO> result = bookService.getAllBooks(title);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @GetMapping(path = "/pagination/sort_by")
  public ResponseEntity<List<BookDTO>> getAllBookPaginationByAsc(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "title") String sortBy,
      @RequestParam(defaultValue = "true") Boolean isASC) {
    List<BookDTO> result = bookService.getBookPaginationSortBy(pageNo,
        pageSize, sortBy, isASC);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @GetMapping(path = "/search_by/title")
  public ResponseEntity<List<BookDTO>> getAllBookFilterByName(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @Size(max = 25, min = 5) @RequestParam(name = "title") String title) {
    List<BookDTO> bookResult = bookService.getBookFilterByTitle(pageNo, pageSize, title);
    return new ResponseEntity<>(bookResult, HttpStatus.OK);
  }

  @GetMapping(path = "/search_by/description")
  public ResponseEntity<List<BookDTO>> getAllBookFilterByDescription(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(name = "description") String description
  ) {
    List<BookDTO> bookResult = bookService.getBookFilterByDescription(pageNo, pageSize,
        description);
    return new ResponseEntity<>(bookResult, HttpStatus.OK);
  }

  @GetMapping(path = "/search_by/content")
  public ResponseEntity<List<BookDTO>> getAllBookFilterByContent(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(name = "content", required = true) String content
  ) {
    List<BookDTO> bookResult = bookService.getBookFilterByContent(pageNo, pageSize,
        content);
    return new ResponseEntity<>(bookResult, HttpStatus.OK);
  }

  @Operation(summary = "Get all book")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "List all books",
          content = {@Content(mediaType = "application/Json",
              schema = @Schema(implementation = BookDTO.class))}
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Book not found",
          content = @Content
      )
  })

  @GetMapping(path = "/{id}")
  public ResponseEntity<BookDTO> getBookById(@PathVariable("id") long id) {
    return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<BookDTO> createBook(@Validated @RequestBody BookDTO bookDTO) {
    BookDTO result = bookService.createBook(bookDTO);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @PutMapping(path = "/title")
  public ResponseEntity<BookDetailDTO> updateBook(
      @Size(max = 25, min = 5) @NotBlank @RequestParam("oldTitle") String oldTitle,
      @Size(max = 25, min = 5) @NotBlank @RequestParam("newTitle") String newTitle) {
    BookDetailDTO bookDTOUpdate = bookService.updateBook(oldTitle, newTitle);
    return new ResponseEntity<>(bookDTOUpdate, HttpStatus.OK);
  }

  @PutMapping(path = "/category")
  public ResponseEntity<BookDetailDTO> updateBookCategory(
      @Size(max = 25, min = 5) @NotBlank @RequestParam("title") String title,
      @Size(max = 25, min = 5) @NotBlank @RequestParam("categoryTitle") String categoryTitle) {
    BookDetailDTO result = bookService.updateBookCategory(title, categoryTitle);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<HttpStatus> deleteBook(@Validated @PathVariable("id") long id) {
    bookService.deleteBook(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping
  public ResponseEntity<HttpStatus> deleteAllBooks() {
    bookService.deleteAllBooks();
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping(path = "/public")
  public ResponseEntity<List<BookPublicDTO>> findByPublished() {
    List<BookPublicDTO> books = bookService.findByPublished();
    return books.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(books, HttpStatus.OK);
  }

  @GetMapping(path = "/category")
  public ResponseEntity<List<BookDetailDTO>> findByCategory(
      @PathParam("Category") String category) {
    List<BookDetailDTO> bookCategory = bookService.getBookCategory(category);
    return new ResponseEntity<>(bookCategory, HttpStatus.OK);
  }

  @GetMapping(path = "/price_greater_or_equal")
  public ResponseEntity<List<BookDetailDTO>> findByPrice(@PathParam("Price") long price) {
    List<BookDetailDTO> result = bookService.getBookByPrice(price);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

}
