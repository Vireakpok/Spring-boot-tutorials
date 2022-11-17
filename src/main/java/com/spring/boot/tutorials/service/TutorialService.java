package com.spring.boot.tutorials.service;

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

    public List<TutorialDTO> getAllTutorials(String title) {
        List<TutorialDTO> results = new ArrayList<TutorialDTO>();
        if (title == null) {
            results = tutorialRepository.findAll().stream().map(this::convertTutorialToTutorialDTO).collect(Collectors.toList());
        } else {
            results =  tutorialRepository.findByTitleContaining(title).stream().map(this::convertTutorialToTutorialDTO).collect(Collectors.toList());
        }
        return results;
    }

    public Optional<TutorialDTO> getTutorialById(Long id) {
        return tutorialRepository.findById(id).stream().findFirst().map(this::convertTutorialToTutorialDTO);
    }

    public TutorialDTO createTutorial(Tutorial tutorial) {
        Tutorial save = tutorialRepository.save(tutorial);
        return convertTutorialToTutorialDTO(save);
    }

    public TutorialDTO updateTutorial(long id, Tutorial tutorial) {
        Optional<Tutorial> findTutorial = tutorialRepository.findById(id);
        if (findTutorial.isPresent()) {
            Tutorial save = findTutorial.get();
            save.setTitle(tutorial.getTitle());
            save.setDescription(tutorial.getDescription());
            save.setPublished(tutorial.isPublished());
            tutorialRepository.save(save);
        }
        return convertTutorialToTutorialDTO(tutorial);
    }

    public void deleteTutorial(long id) {
        tutorialRepository.deleteById(id);
    }

    public void deleteAllTutorials() {
        tutorialRepository.deleteAll();
    }

    public List<TutorialDTO> findByPublished() {
        return tutorialRepository.findByPublished(true).stream().map(this::convertTutorialToTutorialDTO).collect(Collectors.toList());
    }

    public TutorialDTO convertTutorialToTutorialDTO(Tutorial tutorial) {
        TutorialDTO tutorialDTO = new TutorialDTO();
        tutorialDTO.setTitle(tutorial.getTitle());
        tutorialDTO.setDescription(tutorial.getDescription());
        tutorialDTO.setPublished(tutorial.isPublished());
        return tutorialDTO;
    }
}
