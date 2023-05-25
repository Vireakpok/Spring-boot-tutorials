package com.spring.boot.books.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

  @Id
  @GeneratedValue(
      strategy = GenerationType.AUTO
  )
  private Integer id;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false, unique = true)
  private String email;
  @Column(nullable = false)
  private String password;
  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(
      name = "user_role",
      uniqueConstraints = @UniqueConstraint(columnNames = {"USER_ID", "ROLE_ID"}),
      joinColumns = {@JoinColumn(name = "USER_ID",
          referencedColumnName = "ID")},
      inverseJoinColumns = {@JoinColumn(name = "ROLE_ID",
          referencedColumnName = "ID")})
  private List<Role> roles;
}
