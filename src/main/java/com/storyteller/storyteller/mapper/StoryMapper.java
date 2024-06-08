package com.storyteller.storyteller.mapper;

import com.storyteller.storyteller.dto.AuthorDTO;
import com.storyteller.storyteller.dto.StoryDTO;
import com.storyteller.storyteller.entity.Author;
import com.storyteller.storyteller.entity.Category;
import com.storyteller.storyteller.entity.Story;
import com.storyteller.storyteller.utils.ImageUtils;

public class StoryMapper {
    public static StoryDTO toDTO(Story story) {
        Author author = story.getAuthor();
        AuthorDTO authorDTO = new AuthorDTO();
        if (author != null) {
            authorDTO = AuthorMapper.toDTO(author);
        }

        Category category = story.getCategory();
        String categoryName = "";
        if (category != null) {
            categoryName = category.getCategoryName();
        }
        StoryDTO storyDTO = StoryDTO.builder()
                .id(story.getId())
                .title(story.getTitle())
                .content(story.getContent())
                .publishedDate(story.getPublishedDate())
                .coverImageUrl("/stories/cover/" + story.getId())
                .author(authorDTO)
                .category(categoryName)
                .build();
        return storyDTO;
    }
}
