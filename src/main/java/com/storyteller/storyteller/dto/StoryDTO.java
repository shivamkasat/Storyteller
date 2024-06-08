package com.storyteller.storyteller.dto;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoryDTO {
    private int id;
    private String title;
    private String content;
    private Date publishedDate;
    private String coverImageUrl;
    private AuthorDTO author;
    private String category;
}
