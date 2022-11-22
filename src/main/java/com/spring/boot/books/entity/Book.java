package com.spring.boot.books.entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "Book")
@Table(name = "books")
@Setter
@Getter
@NoArgsConstructor
public class Book {

  @Id
  @SequenceGenerator(
      name = "book_generator",
      sequenceName = "book_sequence",
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "book_sequence"
  )
  @Column(
      name = "id",
      nullable = false,
      updatable = false
  )
  private long id;
  @Column(
      name = "title",
      nullable = false,
      columnDefinition = "TEXT"
  )
  private String title;
  @Column(
      name = "description",
      columnDefinition = "TEXT"
  )
  private String description;
  @Column(
      name = "published",
      columnDefinition = "BOOLEAN"
  )
  boolean published;

  @ManyToOne
  @JoinColumn(name = "categoryId", referencedColumnName = "id")
  private Category category;

  @Override
  public String toString() {
    return "Book [id=" + id + ", title=" + title + ", desc=" + description + ", published="
        + published + "]";
  }
}
