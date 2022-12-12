package com.spring.boot.books.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "category")
@Setter
@Getter
public class Category {

  @Id
  @SequenceGenerator(name = "Category_SequenceGenerator", sequenceName = "Category_SequenceGenerator", initialValue = 1, allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Category_SequenceGenerator")
  @Column(name = "id")
  private long id;
  @Column(name = "title", unique = true, updatable = true)
  private String title;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
  private List<Book> books;
  @OneToOne(mappedBy = "category", cascade = CascadeType.ALL)
  private Price price;

  public void addPrice(Price price) {
    this.price = price;
    price.setCategory(this);
  }
}
