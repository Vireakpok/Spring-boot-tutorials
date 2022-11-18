package com.spring.boot.tutorials.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "tutorials")
@Table(name = "tutorials")
@Setter
@Getter
public class Tutorial {

  @Id
  @SequenceGenerator(
      name = "tutorial_generator",
      sequenceName = "tutorial_sequence",
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "tutorial_sequence"
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

  public Tutorial() {

  }

  public Tutorial(String title, String description, boolean published) {
    this.title = title;
    this.description = description;
    this.published = published;

  }

  @Override
  public String toString() {
    return "Tutorial [id=" + id + ", title=" + title + ", desc=" + description + ", published="
        + published + "]";
  }
}
