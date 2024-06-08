package com.storyteller.storyteller.service;

import com.storyteller.storyteller.dao.AuthorRespository;
import com.storyteller.storyteller.dao.CategoryRepository;
import com.storyteller.storyteller.dao.StoryRepository;
import com.storyteller.storyteller.dto.StoryDTO;
import com.storyteller.storyteller.dto.StoryRequestDTO;
import com.storyteller.storyteller.entity.Author;
import com.storyteller.storyteller.entity.Category;
import com.storyteller.storyteller.entity.Story;
import com.storyteller.storyteller.exception.AuthorNotFound;
import com.storyteller.storyteller.exception.CategoryNotFound;
import com.storyteller.storyteller.exception.StoryNotFound;
import com.storyteller.storyteller.mapper.StoryMapper;
import com.storyteller.storyteller.utils.ImageContent;
import com.storyteller.storyteller.utils.ImageService;
import com.storyteller.storyteller.utils.ImageUtils;
import io.minio.errors.MinioException;
import jakarta.transaction.Transactional;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoryServiceImpl implements StoryService{

    private StoryRepository storyRepository;

    private AuthorRespository authorRespository;

    private CategoryRepository categoryRepository;

    private ImageService imageService;

    @Value("${minio.bucketName.cover}")
    private String coverBucketName;

    @Autowired
    public StoryServiceImpl(StoryRepository storyRepository, CategoryRepository categoryRepository,
                            AuthorRespository authorRespository, ImageService imageService) {
        this.storyRepository = storyRepository;
        this.categoryRepository = categoryRepository;
        this.authorRespository = authorRespository;
        this.imageService = imageService;
    }

    @Override
    public List<StoryDTO> findAll() {
         List<Story> stories = storyRepository.findAll();
         return stories.stream().map(StoryMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public StoryDTO findById(int id) {
        Optional<Story> optionalStory = storyRepository.findById(id);
        if (optionalStory.isEmpty()) {
            throw new StoryNotFound("Story with id " + id + " not found");
        }
        Story story = optionalStory.get();
        return StoryMapper.toDTO(story);
    }

    @Override
    @Transactional
    public StoryDTO createStory(StoryRequestDTO storyRequestDTO) {
        String title = storyRequestDTO.getTitle();
        String content = storyRequestDTO.getContent();
        int authorId = storyRequestDTO.getAuthorId();
        int categoryId = storyRequestDTO.getCategoryId();
        MultipartFile coverImage = storyRequestDTO.getCoverImage();
        String fileExtension = "";
        String coverImageName = coverImage.getOriginalFilename();
        if (coverImageName != null) {
            fileExtension = coverImageName.substring(coverImageName.lastIndexOf("."));
        }
        String fileName = String.join("_", title.split(" ")) + fileExtension;
//        String filePath = ImageUtils.saveImage(coverImage, fileName);
        imageService.saveImageToMinIO(coverImage, fileName, coverBucketName);
        Story story = Story.builder().title(title)
                .content(content)
                .coverImage(fileName)
                .publishedDate(new Date(System.currentTimeMillis()))
                .build();

        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        Optional<Author> optionalAuthor = authorRespository.findById(authorId);
        if (optionalCategory.isEmpty()) {
            throw new CategoryNotFound("Category with id: " + categoryId + " not found");
        }

        if (optionalAuthor.isEmpty()) {
            throw new AuthorNotFound("Author with id: " + authorId + " not found");
        }
        Category category = optionalCategory.get();
        Author author = optionalAuthor.get();
        story.setCategory(category);
        story.setAuthor(optionalAuthor.get());
        category.addStory(story);
        author.addStory(story);

        Story savedStory = storyRepository.save(story);
        return StoryMapper.toDTO(story);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Optional<Story> optionalStory = storyRepository.findById(id);
        if (optionalStory.isEmpty()) {
            throw new StoryNotFound("Story with id " + id + " not found");
        }
        Story story = optionalStory.get();
        Author author = story.getAuthor();
        List<Story> authorStories = author.getStories();
        authorStories.remove(story);
        Category category = story.getCategory();
        List<Story> categoryStories = category.getStories();
        categoryStories.remove(story);
        storyRepository.deleteById(id);
    }

    @Override
    @Transactional
    public StoryDTO update(StoryRequestDTO storyRequestDTO, int storyId) {
        int authorId = storyRequestDTO.getAuthorId();
        int categoryId = storyRequestDTO.getCategoryId();
        String title = storyRequestDTO.getTitle();
        String content = storyRequestDTO.getContent();
        Optional<Story> optionalStory = storyRepository.findById(storyId);
        Optional<Author> optionalAuthor = authorRespository.findById(authorId);
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        Story story = optionalStory.get();
        Author author = optionalAuthor.get();
        Category category = optionalCategory.get();
        story.setTitle(title);
        story.setAuthor(author);
        story.setCategory(category);
        story.setContent(content);

        MultipartFile coverImage = storyRequestDTO.getCoverImage();
        String fileExtension = "";
        String coverImageName = coverImage.getOriginalFilename();
        if (coverImageName != null) {
            fileExtension = coverImageName.substring(coverImageName.lastIndexOf("."));
        }
        String fileName = String.join("_", title.split(" ")) + fileExtension;

//        String filePath = ImageUtils.saveImage(coverImage, fileName);
        imageService.saveImageToMinIO(coverImage, fileName, coverBucketName);
        story.setCoverImage(fileName);
        Story savedStory = storyRepository.save(story);
        return StoryMapper.toDTO(savedStory);
    }

    @Override
    public ImageContent getCoverImage(int storyId) {
        Optional<Story> optionalStory = storyRepository.findById(storyId);
        if (optionalStory.isEmpty()) {
            throw new StoryNotFound("Story with id " + storyId + " not found");
        }

        Story story = optionalStory.get();
        String fileName =  story.getCoverImage();
        try {
            InputStream imageFromMinIO = imageService.getImageFromMinIO(fileName, coverBucketName);
            byte[] byteImage = IOUtils.toByteArray(imageFromMinIO);
            return ImageContent.builder()
                    .resource(byteImage)
                    .mediaType(imageService.getMediaTypeForFileName(fileName))
                    .build();

        } catch (IOException | MinioException e) {
            throw new RuntimeException(e);
        }
    }
}
