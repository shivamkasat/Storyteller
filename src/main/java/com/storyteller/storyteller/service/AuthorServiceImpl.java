package com.storyteller.storyteller.service;

import com.storyteller.storyteller.dao.AuthorRespository;
import com.storyteller.storyteller.dao.UserRepsository;
import com.storyteller.storyteller.dto.AuthorDTO;
import com.storyteller.storyteller.dto.AuthorRequestDTO;
import com.storyteller.storyteller.dto.StoryDTO;
import com.storyteller.storyteller.entity.Author;
import com.storyteller.storyteller.entity.Story;
import com.storyteller.storyteller.entity.User;
import com.storyteller.storyteller.exception.AuthorNotFound;
import com.storyteller.storyteller.exception.UserNotFoundException;
import com.storyteller.storyteller.mapper.AuthorMapper;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService{

    private AuthorRespository authorRespository;
    private UserRepsository userRepsository;

    private ImageService imageService;

    @Value("${minio.bucketName.profile}")
    private String profileBucketName;

    @Autowired
    public AuthorServiceImpl(AuthorRespository authorRespository, UserRepsository userRepsository, ImageService imageService) {
        this.authorRespository = authorRespository;
        this.userRepsository = userRepsository;
        this.imageService = imageService;
    }

    @Override
    public AuthorDTO createAuthor(AuthorRequestDTO authorRequestDTO) throws UserNotFoundException {
        // TODO: if a user is already associated as author do not let it associate again with another author
        Optional<User> optionalUser = userRepsository.findById(authorRequestDTO.getUserId());
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with id " + authorRequestDTO.getUserId() + " does not exist");
        }
        User user = optionalUser.get();
        String fileExtention = "";
        String originalFileName = authorRequestDTO.getProfilePicture().getOriginalFilename();
        if (originalFileName != null) {
            fileExtention = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        String fileName = user.getUsername() + fileExtention;
        imageService.saveImageToMinIO(authorRequestDTO.getProfilePicture(), fileName, profileBucketName);
        Author author = new Author(authorRequestDTO.getBio(), fileName);
        author.setUser(user);
        Author savedAuthor = authorRespository.save(author);
        return AuthorMapper.toDTO(savedAuthor);
    }

    @Override
    public AuthorDTO findById(int id) throws AuthorNotFound {
        Optional<Author> optionalAuthor = authorRespository.findById(id);
        if (optionalAuthor.isEmpty()) {
            throw new AuthorNotFound("Author with id " + id +" not found");
        }
        return AuthorMapper.toDTO(optionalAuthor.get());
    }

    @Override
    public List<AuthorDTO> findAll() {
        return authorRespository.findAll().stream().map(AuthorMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        // we will delete author entity and keep make the respective user role as reader
        Optional<Author> optionalAuthor = authorRespository.findById(id);
        if (optionalAuthor.isPresent()) {
            User user = optionalAuthor.get().getUser();
            user.setRole(User.Role.Reader);
            List<Story> stories = optionalAuthor.get().getStories();
            for (Story story : stories) {
                story.setAuthor(null);
            }
        }

        authorRespository.deleteById(id);
    }

    @Override
    public List<StoryDTO> findAllStoriesByAuthorId(int authorId) {
        Optional<Author> optionalAuthor = authorRespository.findById(authorId);
        if (optionalAuthor.isEmpty()) {
            throw new AuthorNotFound("Author with id " + authorId + " not found");
        }
        Author author = optionalAuthor.get();
        return author.getStories().stream().map(StoryMapper::toDTO).collect(Collectors.toList());
    }


    @Override
    public ImageContent getProfilePicture(int authorId) {
        Optional<Author> optionalAuthor = authorRespository.findById(authorId);
        if (optionalAuthor.isEmpty()) {
            throw new AuthorNotFound("Author with id " + authorId + " not found");
        }
        Author author = optionalAuthor.get();
        String fileName = author.getProfilePicture();
        try {
            InputStream imageFromMinIO = imageService.getImageFromMinIO(fileName, profileBucketName);
            byte[] byteImage = IOUtils.toByteArray(imageFromMinIO);
            return ImageContent.builder()
                    .resource(byteImage)
                    .mediaType(imageService.getMediaTypeForFileName(fileName))
                    .build();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (MinioException e) {
            throw new RuntimeException(e);
        }
    }
}
