package com.spring.boot.tutorials.controller;

import com.spring.boot.tutorials.dto.TutorialDTO;
import com.spring.boot.tutorials.entity.Tutorial;
import com.spring.boot.tutorials.service.TutorialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class TutorialController {

  @Autowired
  TutorialService tutorialService;

  @GetMapping(path = "/tutorials")
  public ResponseEntity<List<TutorialDTO>> getAllTutorials(
      @RequestParam(required = false) String title) {
    try {
      List<TutorialDTO> result = tutorialService.getAllTutorials(title);
      return result.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
          : new ResponseEntity<>(result, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(summary = "Get all tutorials")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "List all tutorials",
          content = {@Content(mediaType = "application/Json",
              schema = @Schema(implementation = Tutorial.class))}
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Tutorial not found",
          content = @Content
      )
  })

  @GetMapping(path = "/tutorials/{id}")
  public ResponseEntity<TutorialDTO> getTutorialById(@PathVariable("id") long id) {
    Optional<TutorialDTO> tutorialData = tutorialService.getTutorialById(id);
    return tutorialData.map(tutorialDTO -> new ResponseEntity<>(tutorialDTO, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping(path = "/tutorials")
  public ResponseEntity<TutorialDTO> createTutorial(@RequestBody TutorialDTO tutorialDTO) {
    try {
      return new ResponseEntity<>(tutorialService.createTutorial(tutorialDTO), HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping(path = "/tutorials/{id}")
  public ResponseEntity<TutorialDTO> updateTutorial(@PathVariable("id") long id,
      @RequestBody TutorialDTO tutorialDTO) {
    TutorialDTO tutorialDTO1 = tutorialService.updateTutorial(id, tutorialDTO);
    if (tutorialDTO1 != null) {
      return new ResponseEntity<>(tutorialDTO1, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }
  }

  @DeleteMapping(path = "/tutorials/{id}")
  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
    try {
      tutorialService.deleteTutorial(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping(path = "/tutorials")
  public ResponseEntity<HttpStatus> deleteAllTutorials() {
    try {
      tutorialService.deleteAllTutorials();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(path = "/tutorials/public")
  public ResponseEntity<List<TutorialDTO>> findByPublished() {
    try {
      List<TutorialDTO> tutorials = tutorialService.findByPublished();
      return tutorials.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
          : new ResponseEntity<>(tutorials, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }
  }
}
