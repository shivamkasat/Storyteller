package com.storyteller.storyteller.rest;

import com.storyteller.storyteller.dao.AuthorRespository;
import com.storyteller.storyteller.dto.AuthorDTO;
import com.storyteller.storyteller.dto.AuthorRequestDTO;
import com.storyteller.storyteller.dto.StoryDTO;
import com.storyteller.storyteller.entity.Author;
import com.storyteller.storyteller.entity.Story;
import com.storyteller.storyteller.entity.User;
import com.storyteller.storyteller.service.AuthorService;
import com.storyteller.storyteller.service.UserService;
import com.storyteller.storyteller.utils.ImageContent;
import com.storyteller.storyteller.utils.ImageUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private AuthorService authorService;
    private UserService userService;

    @Autowired
    public AuthorController(AuthorService authorService, UserService userService) {
        this.authorService = authorService;
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors = authorService.findAll();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorDTO> findById(@PathVariable int authorId) {
       AuthorDTO authorDTO = authorService.findById(authorId);
       return ResponseEntity.ok(authorDTO);
    }

    @PostMapping("")
    public ResponseEntity<AuthorDTO> createAuthor(@ModelAttribute @Valid AuthorRequestDTO authorRequestDTO) {
        AuthorDTO authorDTO = authorService.createAuthor(authorRequestDTO);
        return ResponseEntity.ok(authorDTO);
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable int authorId) {
        authorService.deleteById(authorId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/picture/{authorId}")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable int authorId) {
        ImageContent imageContent = authorService.getProfilePicture(authorId);
        return ResponseEntity.ok().contentType(imageContent.getMediaType()).body(imageContent.getResource());
    }

    @GetMapping("/{authorId}/stories")
    public ResponseEntity<List<StoryDTO>> getAllStoriesByAuthor(@PathVariable int authorId) {
        List<StoryDTO> authorStories = authorService.findAllStoriesByAuthorId(authorId);
        if (!authorStories.isEmpty()) {
            return ResponseEntity.ok(authorStories);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}


