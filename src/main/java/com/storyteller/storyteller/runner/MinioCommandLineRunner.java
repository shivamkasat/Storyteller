package com.storyteller.storyteller.runner;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MinioCommandLineRunner implements CommandLineRunner {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucketName.profile}")
    private String profileBucketName;

    @Value("${minio.bucketName.cover}")
    private String coverBucketName;

    @Override
    public void run(String... args) throws Exception {
        try {
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(profileBucketName).build());
            if (isExist) {
                System.out.println("Bucket already exists.");
            } else {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(profileBucketName).build());
                System.out.println("Bucket created successfully.");
            }

            isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(coverBucketName).build());
            if (isExist) {
                System.out.println("Bucket already exists.");
            } else {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(coverBucketName).build());
                System.out.println("Bucket created successfully.");
            }

        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        }
    }
}
