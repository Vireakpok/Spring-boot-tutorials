package com.spring.boot.tutorials.service;

import com.spring.boot.tutorials.config.ModelMapperConfig;
import com.spring.boot.tutorials.dto.TutorialDTO;
import com.spring.boot.tutorials.entity.Tutorial;
import com.spring.boot.tutorials.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TutorialService {

  @Autowired
  private TutorialRepository tutorialRepository;
  @Autowired
  ModelMapperConfig config;

  public List<TutorialDTO> getAllTutorials(String title) {
    List<TutorialDTO> results = new ArrayList<TutorialDTO>();
    if (title == null) {
      results = tutorialRepository.findAll().stream()
          .map(result -> config.modelMapper().map(result, TutorialDTO.class))
          .collect(Collectors.toList());
    } else {
      results = tutorialRepository.findByTitleContaining(title).stream()
          .map(result -> config.modelMapper().map(result, TutorialDTO.class))
          .collect(Collectors.toList());
    }
    return results;
  }

  public Optional<TutorialDTO> getTutorialById(Long id) {
    return tutorialRepository.findById(id).stream().findFirst()
        .map(result -> config.modelMapper().map(result, TutorialDTO.class));
  }

  public TutorialDTO createTutorial(TutorialDTO tutorial) {
    tutorialRepository.save(config.modelMapper().map(tutorial, Tutorial.class));
    return tutorial;
  }

  public TutorialDTO updateTutorial(long id, TutorialDTO tutorialDTO) {
    Optional<Tutorial> foundTutorial = tutorialRepository.findById(id);
    if (foundTutorial.isPresent()) {
      Tutorial source = foundTutorial.get();
      config.modelMapper().map(tutorialDTO, source);
      source.setId(id);
      return config.modelMapper().map(tutorialRepository.save(source), TutorialDTO.class);
    }
    return null;
  }

  public void deleteTutorial(long id) {
    tutorialRepository.deleteById(id);
  }

  public void deleteAllTutorials() {
    tutorialRepository.deleteAll();
  }

  public List<TutorialDTO> findByPublished() {
    return tutorialRepository.findByPublished(true).stream()
        .map(result -> config.modelMapper().map(result, TutorialDTO.class))
        .collect(Collectors.toList());
  }
}
