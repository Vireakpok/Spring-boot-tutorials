package com.spring.boot.books.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryDetailDTO extends CategoryDTO {

  private PriceDTO price;
}
