package com.storyteller.storyteller.rest;

import com.storyteller.storyteller.dto.RatingDTO;
import com.storyteller.storyteller.dto.RatingRequestDTO;
import com.storyteller.storyteller.entity.Rating;
import com.storyteller.storyteller.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("")
    public ResponseEntity<RatingDTO> createRating(@RequestBody @Valid RatingRequestDTO ratingRequestDTO) {
        RatingDTO rating = ratingService.createRating(ratingRequestDTO);
        return ResponseEntity.ok(rating);
    }

    @GetMapping("")
    public ResponseEntity<List<RatingDTO>> findRatingWithStoryId(@RequestParam int storyId) {
        List<RatingDTO> ratings = ratingService.findRatingsWithStoryId(storyId);
        return ResponseEntity.ok(ratings);
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<Void> deleteById(@PathVariable int ratingId) {
        ratingService.deleteById(ratingId);
        return ResponseEntity.noContent().build();
    }
}
