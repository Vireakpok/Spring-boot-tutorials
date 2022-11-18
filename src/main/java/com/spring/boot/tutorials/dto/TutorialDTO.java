package com.spring.boot.tutorials.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TutorialDTO {

  private String title;
  private String description;
  private boolean published;
}
