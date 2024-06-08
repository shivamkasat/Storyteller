CREATE DATABASE IF NOT EXISTS storyteller;
USE storyteller;

CREATE TABLE Users (
    UserID INT PRIMARY KEY AUTO_INCREMENT,
    Username VARCHAR(50) NOT NULL,
    PasswordHash VARCHAR(255) NOT NULL,
    Email VARCHAR(100) NOT NULL,
    DateOfBirth DATE,
    Role ENUM('Admin', 'Author', 'Reader') NOT NULL,
    DateJoined TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Authors (
    AuthorID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    Bio TEXT,
    ProfilePicture VARCHAR(255),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

CREATE TABLE Categories (
    CategoryID INT PRIMARY KEY AUTO_INCREMENT,
    CategoryName VARCHAR(50) NOT NULL
);

CREATE TABLE Stories (
    StoryID INT PRIMARY KEY AUTO_INCREMENT,
    Title VARCHAR(100) NOT NULL,
    AuthorID INT,
    CategoryID INT,
    Content TEXT,
    PublishedDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CoverImage VARCHAR(255),
    FOREIGN KEY (AuthorID) REFERENCES Authors(AuthorID),
    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID)
);

CREATE TABLE Ratings (
    RatingID INT PRIMARY KEY AUTO_INCREMENT,
    StoryID INT,
    UserID INT,
    RatingValue INT CHECK (RatingValue >= 1 AND RatingValue <= 5),
    RatingDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (StoryID) REFERENCES Stories(StoryID),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

CREATE TABLE Comments (
    CommentID INT PRIMARY KEY AUTO_INCREMENT,
    StoryID INT,
    UserID INT,
    Content TEXT,
    CommentDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (StoryID) REFERENCES Stories(StoryID),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

CREATE TABLE Favorites (
    FavoriteID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    StoryID INT,
    FavoriteDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (StoryID) REFERENCES Stories(StoryID)
);
