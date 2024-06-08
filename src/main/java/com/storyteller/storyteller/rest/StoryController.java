package com.storyteller.storyteller.rest;

import com.storyteller.storyteller.dto.StoryDTO;
import com.storyteller.storyteller.dto.StoryRequestDTO;
import com.storyteller.storyteller.entity.Story;
import com.storyteller.storyteller.exception.AuthorNotFound;
import com.storyteller.storyteller.exception.CategoryNotFound;
import com.storyteller.storyteller.service.StoryService;
import com.storyteller.storyteller.utils.ImageContent;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stories")
public class StoryController {

    private StoryService storyService;

    @Autowired
    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping("")
    public ResponseEntity<List<StoryDTO>> getAllStories() {
        return ResponseEntity.ok(storyService.findAll());
    }

    @GetMapping("/{storyId}")
    public ResponseEntity<StoryDTO> getStoryById(@PathVariable int storyId) {
        StoryDTO storyDTO = storyService.findById(storyId);
        return ResponseEntity.ok(storyDTO);
    }

    @PostMapping("")
    public ResponseEntity<StoryDTO> createStory(@ModelAttribute @Valid StoryRequestDTO storyRequestDTO) {
        try {
            StoryDTO story = storyService.createStory(storyRequestDTO);
            return ResponseEntity.ok(story);
        } catch (RuntimeException ex) {
            return  ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{storyId}")
    public ResponseEntity<StoryDTO> updateStory(@PathVariable int storyId, @ModelAttribute @Valid StoryRequestDTO storyRequestDTO) {
        StoryDTO story = storyService.update(storyRequestDTO, storyId);
        return ResponseEntity.ok(story);
    }

    @DeleteMapping("/{storyId}")
    public ResponseEntity<Void> deleteStory(@PathVariable int storyId) {
        storyService.deleteById(storyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cover/{storyId}")
    public ResponseEntity<byte[]> getCoverImage(@PathVariable int storyId) {
        ImageContent imageContent = storyService.getCoverImage(storyId);
        return ResponseEntity.ok().contentType(imageContent.getMediaType()).body(imageContent.getResource());
    }

}
