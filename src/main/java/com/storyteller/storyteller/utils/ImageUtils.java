package com.storyteller.storyteller.utils;

import com.storyteller.storyteller.entity.User;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImageUtils {

    private static MinioClient minioClient;

    // TODO: save cover image and profile image in seperate directories
    private static final String authorImagesPath = "/Users/shkasat/Downloads/storyteller/src/main/resources/static";
    public static String saveImage(MultipartFile image, String filename) throws RuntimeException {
        try {
            Path uploadPath = Paths.get(authorImagesPath);
            Path targetLocation = uploadPath.resolve(filename);
            Files.copy(image.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Return the relative path to the image
            return targetLocation.toString();
        } catch (IOException ex) {
            throw new RuntimeException("Could not save image file: " + image.getOriginalFilename(), ex);
        }
    }

    public static MediaType getMediaTypeForFileName(Path path) {
        String mimeType;
        try {
            mimeType = Files.probeContentType(path);
        } catch (IOException e) {
            throw new RuntimeException("Unable to determine mime type of the file", e);
        }
        if (mimeType == null) {
            // Fallback to a default mime type if not determinable
            mimeType = "application/octet-stream";
        }
        return MediaType.parseMediaType(mimeType);
    }

    public static void saveImageToMino(MultipartFile image, String fileName, String bucketName)  {
        try (InputStream is = image.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs
                    .builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(is, is.available(), -1)
                    .contentType(image.getContentType())
                    .build());
        } catch (Exception ex) {
            throw new  RuntimeException("failed to save image to storage" + ex.getMessage());
        }
    }
}
