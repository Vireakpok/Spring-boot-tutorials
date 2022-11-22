package com.spring.boot.books.controller;

import com.spring.boot.books.dto.BookDetailDTO;
import com.spring.boot.books.dto.BookDTO;
import com.spring.boot.books.dto.BookPublicDTO;
import com.spring.boot.books.entity.Book;
import com.spring.boot.books.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.Optional;
import javax.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  @GetMapping
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

  @GetMapping(path = "/pagination/sort_by")
  public ResponseEntity<List<BookDTO>> getAllBookPaginationByAsc(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "title") String sortBy,
      @RequestParam(defaultValue = "true") Boolean isASC) {
    List<BookDTO> result = bookService.getBookPaginationSortBy(pageNo,
        pageSize, sortBy, isASC);
    return result.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(result, HttpStatus.OK);
  }

  @GetMapping(path = "/search_by/title")
  public ResponseEntity<List<BookDTO>> getAllBookFilterByName(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(name = "title", required = true) String title) {
    List<BookDTO> bookResult = bookService.getBookFilterByTitle(pageNo, pageSize, title);
    return bookResult.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(bookResult, HttpStatus.OK);
  }

  @GetMapping(path = "/search_by/description")
  public ResponseEntity<List<BookDTO>> getAllBookFilterByDescription(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(name = "description", required = true) String description
  ) {
    List<BookDTO> bookResult = bookService.getBookFilterByContent(pageNo, pageSize,
        description);
    return bookResult.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(bookResult, HttpStatus.OK);
  }

  @GetMapping(path = "/search_by/content")
  public ResponseEntity<List<BookDTO>> getAllBookFilterByContent(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(name = "content", required = true) String content
  ) {
    List<BookDTO> bookResult = bookService.getBookFilterByContent(pageNo, pageSize,
        content);
    return bookResult.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(bookResult, HttpStatus.OK);
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

  @GetMapping(path = "/{id}")
  public ResponseEntity<BookDTO> getBookById(@PathVariable("id") long id) {
    Optional<BookDTO> book = bookService.getBookById(id);
    return book.map(tutorialDTO -> new ResponseEntity<>(tutorialDTO, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
    try {
      Optional<BookDTO> result = bookService.createBook(bookDTO);
      return result.map(dto -> new ResponseEntity<>(dto, HttpStatus.CREATED))
          .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE));
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping(path = "/title")
  public ResponseEntity<BookDetailDTO> updateBook(@RequestParam("oldTitle") String oldTitle,
      @RequestParam("newTitle") String newTitle) {
    BookDetailDTO bookDTOUpdate = bookService.updateBook(oldTitle, newTitle);
    boolean bookDTONotEmpty = StringUtils.isNotEmpty(bookDTOUpdate.toString());
    return bookDTONotEmpty ? new ResponseEntity<>(bookDTOUpdate, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
  }

  @PutMapping(path = "/category")
  public ResponseEntity<BookDetailDTO> updateBookCategory(
      @RequestParam("title") String title, @RequestParam("category") String name) {
    BookDetailDTO result = bookService.addBookCategory(title, name);
    return StringUtils.isEmpty(result.getTitle()) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(result, HttpStatus.OK);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") long id) {
    try {
      bookService.deleteBook(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping
  public ResponseEntity<HttpStatus> deleteAllBooks() {
    try {
      bookService.deleteAllBooks();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(path = "/public")
  public ResponseEntity<List<BookPublicDTO>> findByPublished() {
    try {
      List<BookPublicDTO> books = bookService.findByPublished();
      return books.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
          : new ResponseEntity<>(books, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(path = "/category")
  public ResponseEntity<List<BookDetailDTO>> findByCategory(
      @PathParam("Category") String category) {
    List<BookDetailDTO> bookCategory = bookService.getBookCategory(category);
    return bookCategory.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(bookCategory, HttpStatus.OK);
  }

  @GetMapping(path = "/price_greater_or_equal")
  public ResponseEntity<List<BookDetailDTO>> findByPrice(@PathParam("Price") long price) {
    List<BookDetailDTO> result = bookService.getBookByPrice(price);
    return result.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(result, HttpStatus.OK);
  }

}
