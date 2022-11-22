package com.spring.boot.books.controller;

import com.spring.boot.books.dto.CategoryDTO;
import com.spring.boot.books.dto.CategoryDetailDTO;
import com.spring.boot.books.service.CategoryService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @PostMapping
  public ResponseEntity<CategoryDetailDTO> saveCategory(
      @RequestBody CategoryDetailDTO categoryDetailDTO) {
    try {
      Optional<CategoryDetailDTO> result = categoryService.saveCategory(categoryDetailDTO);
      return result.map(detailDTO -> new ResponseEntity<>(detailDTO, HttpStatus.OK))
          .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE));
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping
  public ResponseEntity<List<CategoryDetailDTO>> getCategories() {
    List<CategoryDetailDTO> result = categoryService.getAllCategory();
    return result.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(result, HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<CategoryDTO> updateCategory(@RequestParam String oldCategory,
      @RequestParam String newCategory) {
    Optional<CategoryDTO> result = categoryService.updateCategory(oldCategory, newCategory);
    return result.map(categoryDTO -> new ResponseEntity<>(categoryDTO, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE));
  }

  @PutMapping(path = "price")
  public ResponseEntity<CategoryDetailDTO> updatePrice(@RequestParam String title,
      @RequestParam long newPrice) {
    Optional<CategoryDetailDTO> result = categoryService.updatePrice(title, newPrice);
    return result.map(categoryDetailDTO -> new ResponseEntity<>(categoryDetailDTO, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}
