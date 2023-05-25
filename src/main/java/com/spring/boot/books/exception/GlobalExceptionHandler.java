package com.spring.boot.books.exception;

import java.time.LocalDate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(BookNoContentException.class)
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public final ResponseEntity<Object> bookNoContentHandle(BookNoContentException ex) {
    log.error(ex.getMessage(), ex);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @ExceptionHandler(BookNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public final ResponseEntity<Object> bookNotFoundHandler(
      BookNotFoundException ex,
      WebRequest request) {
    BookExceptionResponse response = new BookExceptionResponse();
    response.setDate(LocalDate.now());
    response.setMessage(
        ex.getMessage()
            .concat(" ")
            .concat(request.getLocale().toString()));
    log.error(ex.getMessage(), ex);
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(CategoryConflictException.class)
  public final ResponseEntity<Object> categoryConflictHandler(CategoryConflictException ex,
      WebRequest request) {
    BookExceptionResponse response = new BookExceptionResponse();
    response.setDate(LocalDate.now());
    response.setMessage(
        ex.getMessage()
            .concat(" ")
            .concat(request.getLocale().toString()));
    log.error(ex.getMessage(), ex);
    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
  }

  @Override
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    BookExceptionResponse response = new BookExceptionResponse();
    response.setDate(LocalDate.now());
    response.setMessage(
        ex.getMessage()
            .concat(" ")
            .concat(request.getLocale().toString()));
    log.error(ex.getMessage(), ex);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handle(Exception ex,
      HttpServletRequest request, HttpServletResponse response) {
    BookExceptionResponse bookExceptionResponse = new BookExceptionResponse();
    bookExceptionResponse.setDate(LocalDate.now());
    bookExceptionResponse.setMessage(
        ex.getMessage()
            .concat(" ")
            .concat(request.getLocale().toString()));
    log.error(ex.getMessage(), ex);
    return new ResponseEntity<>(bookExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(RoleNotFoundException.class)
  public final ResponseEntity<Object> roleNotFoundHandler(RoleNotFoundException ex) {
    log.error(ex.getMessage(), ex);
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(RoleExistException.class)
  public final ResponseEntity<Object> roleExistHandler(RoleExistException ex) {
    log.error(ex.getMessage(), ex);
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(EmailExistException.class)
  public final ResponseEntity<Object> emailExceptionHandler(EmailExistException ex) {
    log.error(ex.getMessage(), ex);
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(JwtIOException.class)
  public ResponseEntity<Object> jwtIOHandler(JwtIOException ex) {
    log.error(ex.getMessage(), ex);
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
