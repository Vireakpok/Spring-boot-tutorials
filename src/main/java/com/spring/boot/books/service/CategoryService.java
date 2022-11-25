package com.spring.boot.books.service;

import com.spring.boot.books.config.ModelMapperConfig;
import com.spring.boot.books.constant.BookConstant;
import com.spring.boot.books.dto.CategoryDTO;
import com.spring.boot.books.dto.CategoryDetailDTO;
import com.spring.boot.books.entity.Category;
import com.spring.boot.books.entity.Price;
import com.spring.boot.books.exception.BookNoContentException;
import com.spring.boot.books.exception.BookNotFoundException;
import com.spring.boot.books.exception.CategoryConflictException;
import com.spring.boot.books.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final ModelMapperConfig config;

  public CategoryDetailDTO saveCategory(CategoryDetailDTO categoryDTO) {
    Category category = config.modelMapper().map(categoryDTO, Category.class);
    Optional<Category> title = categoryRepository.findByTitle(category.getTitle());
    if (title.isPresent()) {
      throw new CategoryConflictException(BookConstant.CATEGORY_ALREADY_EXIST);
    }
    Price price = config.modelMapper().map(categoryDTO.getPrice(), Price.class);
    category.addPrice(price);
    Category save = categoryRepository.save(category);
    return config.modelMapper().map(save, CategoryDetailDTO.class);
  }

  public List<CategoryDetailDTO> getAllCategory() {
    List<Category> categories = categoryRepository.findAll();
    if (categories.isEmpty()) {
      throw new BookNoContentException(BookConstant.NO_CATEGORY_AVAILABLE);
    }
    return categories.stream()
        .map(result -> config.modelMapper().map(result, CategoryDetailDTO.class))
        .collect(Collectors.toList());
  }

  public Optional<CategoryDTO> updateCategory(String oldCategory, String newCategory) {
    Optional<Category> category = categoryRepository.findByTitle(oldCategory);
    if (category.isEmpty()) {
      throw new BookNotFoundException(
          BookConstant.CATEGORY_NOT_EXIST.concat(" ").concat(oldCategory));
    }
    Category save = category.get();
    save.setTitle(newCategory);
    return Optional.of(
        config.modelMapper().map(categoryRepository.save(save), CategoryDTO.class));
  }

  public Optional<CategoryDetailDTO> updatePrice(String title, long newPrice) {
    Optional<Category> category = categoryRepository.findByTitle(title);
    if (category.isEmpty()) {
      throw new BookNotFoundException(BookConstant.CATEGORY_NOT_EXIST.concat(" ").concat(title));

    }
    Category source = category.get();
    source.getPrice().setDollar(newPrice);
    return Optional.of(
        config.modelMapper().map(categoryRepository.save(source), CategoryDetailDTO.class));
  }
}
