package com.storyteller.storyteller.service;

import com.storyteller.storyteller.dao.CommentRepository;
import com.storyteller.storyteller.dao.StoryRepository;
import com.storyteller.storyteller.dao.UserRepsository;
import com.storyteller.storyteller.dto.CommentDTO;
import com.storyteller.storyteller.dto.CommentRequestDTO;
import com.storyteller.storyteller.entity.Comment;
import com.storyteller.storyteller.entity.Story;
import com.storyteller.storyteller.entity.User;
import com.storyteller.storyteller.mapper.CommentMappper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private UserRepsository userRepsository;
    private StoryRepository storyRepository;
    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                              UserRepsository userRepsository,
                              StoryRepository storyRepository) {
        this.commentRepository = commentRepository;
        this.userRepsository = userRepsository;
        this.storyRepository = storyRepository;
    }


    @Override
    public CommentDTO createComment(CommentRequestDTO commentRequestDTO) {
        int storyId = commentRequestDTO.getStoryId();
        int userId = commentRequestDTO.getUserId();
        String content = commentRequestDTO.getContent();

        Optional<Story> optionalStory = storyRepository.findById(storyId);
        Optional<User> optionalUser = userRepsository.findById(userId);

        Comment comment = Comment.builder().content(content)
                .commentDate(new Date(System.currentTimeMillis()))
                .user(optionalUser.get())
                .story(optionalStory.get())
                .build();
        Comment savedComment = commentRepository.save(comment);
        return CommentMappper.toDTO(savedComment);
    }

    @Override
    public void deleteById(int commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDTO> getCommentByStoryId(int storyId) {
        return commentRepository.findByStoryId(storyId).stream().map(CommentMappper::toDTO).collect(Collectors.toList());
    }
}
