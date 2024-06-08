package com.storyteller.storyteller.mapper;

import com.storyteller.storyteller.dto.CommentDTO;
import com.storyteller.storyteller.dto.UserDTO;
import com.storyteller.storyteller.entity.Comment;
import com.storyteller.storyteller.entity.User;

public class CommentMappper {
    public static CommentDTO toDTO(Comment comment) {
        User user = comment.getUser();
        UserDTO userDTO = new UserDTO();
        if (user != null) {
            userDTO = UserMapper.toDTO(user);
        }
        CommentDTO commentDTO = CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .commentDate(comment.getCommentDate())
                .userDTO(userDTO)
                .build();

        return commentDTO;
    }
}
