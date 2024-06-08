package com.storyteller.storyteller.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name="Ratings")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"id", "story", "user"})
@AllArgsConstructor
@Builder
public class Rating {

    /*CREATE TABLE Ratings (
        RatingID INT PRIMARY KEY AUTO_INCREMENT,
        StoryID INT,
        UserID INT,
        RatingValue INT CHECK (RatingValue >= 1 AND RatingValue <= 5),
        RatingDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (StoryID) REFERENCES Stories(StoryID),
        FOREIGN KEY (UserID) REFERENCES Users(UserID)
    );*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ratingid")
    private int id;

    @Column(name = "ratingvalue")
    private int ratingValue;

    @Column(name = "ratingdate")
    private Date ratingDate;

    @ManyToOne
    @JoinColumn(name = "storyid")
    private Story story;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;
}
