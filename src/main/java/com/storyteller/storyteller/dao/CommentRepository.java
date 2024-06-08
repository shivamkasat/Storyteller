package com.storyteller.storyteller.dao;

import com.storyteller.storyteller.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByStoryId(int storyId);
}
