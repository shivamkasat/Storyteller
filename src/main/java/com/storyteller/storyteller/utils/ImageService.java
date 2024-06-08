package com.storyteller.storyteller.utils;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.GetObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;


import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class ImageService {

    @Autowired
    private MinioClient minioClient;


    public String saveImageToMinIO(MultipartFile image, String fileName, String bucketName) throws RuntimeException {
        try (InputStream is = image.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(is, is.available(), -1)
                            .contentType(image.getContentType())
                            .build()
            );
        } catch (Exception ex) {
            throw new RuntimeException("Failed to update image to storage " + ex.getMessage());
        }
        return fileName; // This would be the key for retrieving the image from MinIO
    }

    public InputStream getImageFromMinIO(String filename, String bucketName) throws IOException, MinioException {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            .build()
            );
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public MediaType getMediaTypeForFileName(String fileName) {
        String extension = getFileExtension(fileName);
        if (extension != null) {
            switch (extension.toLowerCase()) {
                case "png":
                    return MediaType.IMAGE_PNG;
                case "jpg":
                case "jpeg":
                    return MediaType.IMAGE_JPEG;
                case "gif":
                    return MediaType.IMAGE_GIF;
                // Add more cases for other file types if needed
            }
        }
        return MediaType.APPLICATION_OCTET_STREAM;
    }

    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex >= 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        return null;
    }
}
