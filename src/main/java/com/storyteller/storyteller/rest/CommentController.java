package com.storyteller.storyteller.rest;

import com.storyteller.storyteller.dto.CommentDTO;
import com.storyteller.storyteller.dto.CommentRequestDTO;
import com.storyteller.storyteller.entity.Comment;
import com.storyteller.storyteller.mapper.CommentMappper;
import com.storyteller.storyteller.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("")
    public ResponseEntity<CommentDTO> createComment(@RequestBody @Valid CommentRequestDTO commentRequestDTO) {
        CommentDTO commentDTO = commentService.createComment(commentRequestDTO);
        return ResponseEntity.ok(commentDTO);
    }

    @GetMapping("")
    public ResponseEntity<List<CommentDTO>> getCommentByStoryId(@RequestParam int storyId) {
        return ResponseEntity.ok(commentService.getCommentByStoryId(storyId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteById(@PathVariable int commentId) {
        commentService.deleteById(commentId);
        return ResponseEntity.noContent().build();
    }

}
