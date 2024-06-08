package com.storyteller.storyteller.service;

import com.storyteller.storyteller.dto.AuthorDTO;
import com.storyteller.storyteller.dto.AuthorRequestDTO;
import com.storyteller.storyteller.dto.StoryDTO;
import com.storyteller.storyteller.entity.Author;
import com.storyteller.storyteller.entity.Story;
import com.storyteller.storyteller.utils.ImageContent;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    AuthorDTO createAuthor(AuthorRequestDTO authorRequestDTO);
    AuthorDTO findById(int id);

    List<AuthorDTO> findAll();

    void deleteById(int id);

    List<StoryDTO> findAllStoriesByAuthorId(int authorId);

    ImageContent getProfilePicture(int authorId);
}
