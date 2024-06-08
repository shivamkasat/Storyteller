package com.storyteller.storyteller.service;

import com.storyteller.storyteller.dto.StoryDTO;
import com.storyteller.storyteller.dto.StoryRequestDTO;
import com.storyteller.storyteller.entity.Story;
import com.storyteller.storyteller.exception.AuthorNotFound;
import com.storyteller.storyteller.exception.CategoryNotFound;
import com.storyteller.storyteller.utils.ImageContent;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface StoryService {

    List<StoryDTO> findAll();

    StoryDTO findById(int id);

    StoryDTO createStory(StoryRequestDTO storyRequestDTO);

    void deleteById(int id);

    StoryDTO update(StoryRequestDTO storyRequestDTO, int storyId);

    ImageContent getCoverImage(int storyId);
}
