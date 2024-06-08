package com.storyteller.storyteller.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Stories")
@Getter
@Setter
@ToString(exclude = {"id", "author", "category", "coverImage"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Story {
    /*
        CREATE TABLE Stories (
        StoryID INT PRIMARY KEY AUTO_INCREMENT,
        Title VARCHAR(100) NOT NULL,
        AuthorID INT NULL,
        CategoryID INT NULL,
        Content TEXT,
        PublishedDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        CoverImage VARCHAR(255),
        FOREIGN KEY (AuthorID) REFERENCES Authors(AuthorID),
        FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID));
    */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storyid")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "publisheddate")
    private Date publishedDate;

    @Column(name = "coverimage")
    private String coverImage;

    @ManyToOne
    @JoinColumn(name = "authorid")
    private Author author;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "categoryid")
    private Category category;

    public Story(String title, String content, Date publishedDate) {
        this.title = title;
        this.content = content;
        this.publishedDate = publishedDate;
    }
}
