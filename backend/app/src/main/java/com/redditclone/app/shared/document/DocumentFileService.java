package com.redditclone.app.shared.document;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URI;
import java.util.UUID;

@Service
public class DocumentFileService {

    private final MinioClient minioClient;
    private final String minioUrl;

    public DocumentFileService(MinioClient minioClient, @Value("${minio.url}") String minioUrl) {
        this.minioClient = minioClient;
        this.minioUrl = minioUrl;
    }

    public String uploadFile(MultipartFile file, String bucketName) {
        if (file == null || file.isEmpty() || file.getOriginalFilename() == null) {
            return null;
        }

        String displayFileName = file.getOriginalFilename().replaceAll("[^a-zA-Z0-9.\\-_]", "_");
        String fileName = UUID.randomUUID() + "-" + displayFileName;

        try (InputStream fileStream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(fileStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            return minioUrl + "/" + bucketName + "/" + fileName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }




    public InputStream downloadFile(String fileUrl) throws Exception {
        URI uri = new URI(fileUrl);
        String path = uri.getPath();

        String[] parts = path.replaceFirst("^/+", "").split("/", 2);

        if (parts.length < 2) {
            throw new RuntimeException("Invalid file URL format: " + fileUrl);
        }

        String bucketName = parts[0];
        String objectName = parts[1];

        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to download file", e);
        }
    }

    public void deleteFile(String bucketName, String objectName) throws Exception {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build()
        );
    }
}
