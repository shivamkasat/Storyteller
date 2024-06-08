package com.storyteller.storyteller.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Categories")
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"id"})
public class Category {
    /*
        CREATE TABLE Categories (
        CategoryID INT PRIMARY KEY AUTO_INCREMENT,
        CategoryName VARCHAR(50) NOT NULL);
    */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="categoryid")
    private int id;

    @Column(name = "categoryname")
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<Story> stories;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public void addStory(Story story) {
        if (this.stories == null) {
            this.stories = new ArrayList<>();
        }
        this.stories.add(story);
    }
}
