package com.redditclone.app.shared.document;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
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

    public String uploadFile(InputStream inputStream, String fileName, String bucketName, String contentType) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(inputStream, -1, 10485760)
                            .contentType(contentType)
                            .build()
            );
            return minioUrl + "/" + bucketName + "/" + fileName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file from stream", e);
        }
    }

    public String generateAndUploadVideoThumbnail(MultipartFile videoFile) {
        try {
            File tempVideoFile = File.createTempFile("video", ".mp4");
            videoFile.transferTo(tempVideoFile);

            File thumbnailFile = File.createTempFile("thumb", ".jpg");

            FFmpeg ffmpeg = new FFmpeg("ffmpeg");
            FFprobe ffprobe = new FFprobe("ffprobe");

            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(tempVideoFile.getAbsolutePath())
                    // Seek to 1 second in the video
                    .addExtraArgs("-ss", "00:00:01")
                    // Capture only 1 frame
                    .addOutput(thumbnailFile.getAbsolutePath())
                    .setFrames(1)
                    .setFormat("image2")
                    .done();

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
            executor.createJob(builder).run();

            try (InputStream thumbInputStream = new FileInputStream(thumbnailFile)) {
                String thumbFileName = UUID.randomUUID() + ".jpg";
                return uploadFile(
                        thumbInputStream,
                        thumbFileName,
                        "thumbnails",
                        "image/jpeg"
                );
            } finally {
                tempVideoFile.delete();
                thumbnailFile.delete();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate and upload thumbnail", e);
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
