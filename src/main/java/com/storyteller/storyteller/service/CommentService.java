package com.storyteller.storyteller.service;

import com.storyteller.storyteller.dto.CommentDTO;
import com.storyteller.storyteller.dto.CommentRequestDTO;
import com.storyteller.storyteller.entity.Comment;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(CommentRequestDTO commentRequestDTO);

    void deleteById(int commentId);

    List<CommentDTO> getCommentByStoryId(int storyId);

}
