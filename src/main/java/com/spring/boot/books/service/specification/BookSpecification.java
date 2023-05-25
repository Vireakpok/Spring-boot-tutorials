package com.spring.boot.books.service.specification;

import com.spring.boot.books.entity.Book;
import com.spring.boot.books.entity.Book_;
import com.spring.boot.books.entity.Category;
import com.spring.boot.books.entity.Category_;
import com.spring.boot.books.entity.Price;
import com.spring.boot.books.entity.Price_;
import javax.persistence.criteria.Join;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookSpecification {

  public static Specification<Book> getBookDetail(long categoryId) {
    return (book, cq, cb) -> {
      Join<Book, Category> bookCategory = book.join(Book_.CATEGORY);
      Join<Category, Price> categoryPrice = bookCategory.join(Category_.PRICE);
      return cb.equal(categoryPrice.get(Category_.ID), categoryId);
    };
  }

  public static Specification<Book> getBookPrice(long price) {
    return (book, cq, cb) -> {
      Join<Book, Category> bookCategory = book.join(Book_.CATEGORY);
      Join<Category, Price> categoryPrice = bookCategory.join(Category_.PRICE);
      return cb.or(cb.gt(categoryPrice.get(Price_.DOLLAR), price),
          cb.equal(categoryPrice.get(Price_.DOLLAR), price));
    };
  }
}
