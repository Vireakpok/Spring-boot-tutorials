package com.spring.boot.books.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookConstant {

  public static final String BOOK_CAN_NOT_UPDATE_WITH_TITLE = "Book can not be updated with title";
  public static final String BOOK_CAN_NOT_UPDATE_WITH_CATEGORY_TITLE = "Book can not be updated with category title";
  public static final String BOOK_TITLE_NOT_FOUND = "Book title not found";
  public static final String BOOK_HAVE_NO_PRICE_WITH = "Search have no price with";
  public static final String BOOK_NOT_FOUND_WITH_ID = "Book not found with id";
  public static final String BOOK_NOT_FOUND_WITH_CONTENT = "Book not found with content";
  public static final String BOOK_NOT_FOUND_WITH_DESCRIPTION = "Book not found with description";
  public static final String CAN_NOT_SORT_BY_TITLE = "Can not sort by title";
  public static final String NO_CATEGORY_AVAILABLE = "No category available";
  public static final String CATEGORY_ALREADY_EXIST = "Category already exists";
  public static final String CATEGORY_NOT_EXIST = "Category not exist";
}
