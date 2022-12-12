package com.spring.boot.books.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "price")
@Setter
@Getter
public class Price {

  @Id
  @SequenceGenerator(name = "Price_SequenceGenerator", sequenceName = "Price_SequenceGenerator", initialValue = 1, allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Price_SequenceGenerator")
  @Column(name = "id")
  private long id;
  @Column(name = "price")
  private long dollar;

  @OneToOne
  @JoinColumn(name = "id")
  private Category category;
}
