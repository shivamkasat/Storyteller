package com.storyteller.storyteller;

import com.storyteller.storyteller.dao.AppDao;
import com.storyteller.storyteller.entity.Author;
import com.storyteller.storyteller.entity.Category;
import com.storyteller.storyteller.entity.Story;
import com.storyteller.storyteller.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Optional;

@SpringBootApplication
public class StorytellerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorytellerApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AppDao appDao) {
		return runner -> {
			createUser(appDao);
//			findAuthorById(appDao);
//			createStory(appDao);

		};
	}

	@Transactional
	private void createStory(AppDao appDao) {
		Story story = new Story("StoryTitle", "StoryContent", new Date(System.currentTimeMillis()));
		Optional<Author> optionalAuthor = appDao.findAuthorByIdWithStories(3);
		Author author = optionalAuthor.get();
		story.setAuthor(author);
		author.addStory(story);
		Optional<Category> optionalCategory = appDao.findCategoryByIdWithStories(2);
		Category category = optionalCategory.get();
		category.addStory(story);
		story.setCategory(category);
		appDao.saveStory(story);
	}

	private void findAuthorById(AppDao appDao) {
		Optional<Author> optionalAuthor = appDao.findAuthorById(1);
		Author author = optionalAuthor.get();
		System.out.println(author);

	}


	private void createUser(AppDao appDao) {
		User user = new User("shivamkasatt", "password",
				"shivamkasatt@gmail.com", new Date(System.currentTimeMillis()),
				User.Role.Admin, new Date(System.currentTimeMillis()));
		appDao.save(user);
	}
}
