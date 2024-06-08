package com.storyteller.storyteller.dto;

import lombok.*;

import java.sql.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
    private int id;

    private String content;

    private Date commentDate;

    private UserDTO userDTO;
}
