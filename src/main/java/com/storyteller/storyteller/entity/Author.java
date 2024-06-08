package com.storyteller.storyteller.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Authors")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"user", "id"})
public class Author {
    /*
        MySQL query to create Authors table
        CREATE TABLE Authors (
            AuthorID INT PRIMARY KEY AUTO_INCREMENT,
            UserID INT,
            Bio TEXT,
            ProfilePicture VARCHAR(255),
            FOREIGN KEY (UserID) REFERENCES Users(UserID)
        );
    */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authorid")
    private int id;

    @Column(name = "bio")
    private String bio;

    @Column(name = "profilepicture")
    private String profilePicture;

    @OneToOne
    @JoinColumn(name = "userid")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private List<Story> stories;

    public Author(String bio, String profilePicture) {
        this.bio = bio;
        this.profilePicture = profilePicture;
    }

    public void addStory(Story story) {
        if (this.stories == null) {
            this.stories = new ArrayList<>();
        }
        this.stories.add(story);
    }

}
