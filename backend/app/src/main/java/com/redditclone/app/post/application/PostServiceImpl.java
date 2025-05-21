package com.redditclone.app.post.application;

import com.redditclone.app.post.application.dto.PostDetailsDTO;
import com.redditclone.app.post.application.dto.PostPreviewDTO;
import com.redditclone.app.post.application.dto.PostUploadDTO;
import com.redditclone.app.post.domain.*;
import com.redditclone.app.shared.document.DocumentFileService;
import com.redditclone.app.subreddit.domain.Subreddit;
import com.redditclone.app.subreddit.domain.SubredditRepository;
import com.redditclone.app.user.domain.User;
import com.redditclone.app.user.domain.UserRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final DocumentFileService documentFileService;

    private final PostMapper postMapper;

    public PostServiceImpl(PostRepository postRepository, SubredditRepository subredditRepository, UserRepository userRepository, DocumentFileService documentFileService, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.subredditRepository = subredditRepository;
        this.userRepository = userRepository;
        this.documentFileService = documentFileService;
        this.postMapper = postMapper;
    }

    @Override
    public Post uploadPost(PostUploadDTO postDTO) {
        User user = userRepository.findById(postDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Subreddit subreddit = subredditRepository.findById(postDTO.getSubredditId())
                .orElseThrow(() -> new RuntimeException("Subreddit not found"));

        String fileUrl = null;
        String thumbnailUrl = null;
        try {
            fileUrl = documentFileService.uploadFile(postDTO.getFile(), "posts");
            if (postDTO.getType() == PostType.VIDEO) {
                thumbnailUrl = documentFileService.generateAndUploadVideoThumbnail(postDTO.getFile());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file", e);
        }

        Post post = postDTO.toEntity(user, subreddit, fileUrl, thumbnailUrl);
        return postRepository.save(post);
    }


    @Override
    public PostDetailsDTO downloadPost(UUID id) throws Exception {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        byte[] fileData = downloadFile(post.getFileUrl());
        return postMapper.toPostDetailsDTO(post, fileData);
    }

    @Override
    public Page<PostPreviewDTO> getPostsForUser(Pageable pageable, UUID userId, FilterType filterType) {
        Page<Post> postPage = filterPosts(pageable, userId, filterType);

        return postPage.map(post -> {
            String urlToDownload;

            if (post.getType() == PostType.VIDEO) {
                urlToDownload = post.getThumbnailUrl();
            } else {
                urlToDownload = post.getFileUrl();
            }

            return postMapper.toPostPreviewDTO(post, downloadFile(urlToDownload));
        });
    }

    private Page<Post> filterPosts(Pageable pageable, UUID userId, FilterType filterType) {
        switch (filterType) {
            case NEW -> {
                return postRepository.findPostsForUserFeedNew(userId, pageable);
            }
            case TOP_TODAY -> {
                Instant cutoff = LocalDateTime.now()
                        .minusDays(1)
                        .atZone(ZoneId.systemDefault())
                        .toInstant();
                return postRepository.findPostsForUserFeedTop(userId, cutoff, pageable);
            }
            case TOP_WEEK -> {
                Instant cutoff = LocalDateTime.now()
                        .minusWeeks(1)
                        .atZone(ZoneId.systemDefault())
                        .toInstant();
                return postRepository.findPostsForUserFeedTop(userId, cutoff, pageable);
            }
            case TOP_MONTH -> {
                Instant cutoff = LocalDateTime.now()
                        .minusMonths(1)
                        .atZone(ZoneId.systemDefault())
                        .toInstant();
                return postRepository.findPostsForUserFeedTop(userId, cutoff, pageable);
            }
            case TOP_YEAR -> {
                Instant cutoff = LocalDateTime.now()
                        .minusYears(1)
                        .atZone(ZoneId.systemDefault())
                        .toInstant();
                return postRepository.findPostsForUserFeedTop(userId, cutoff, pageable);
            }
            case TOP_ALL_TIME -> {
                return postRepository.findPostsForUserFeedTopAllTime(userId, pageable);
            }
            default -> throw new IllegalArgumentException("Unsupported filter type: " + filterType);
        }
    }


    @Nullable
    private byte[] downloadFile(String fileUrl) {
        byte[] fileData = null;
        if (fileUrl != null) {
            try (InputStream inputStream = documentFileService.downloadFile(fileUrl)) {
                fileData = inputStream.readAllBytes();  // Convert InputStream to byte[]
            } catch (Exception e) {
                throw new RuntimeException("Failed to download file", e);
            }
        }
        return fileData;
    }




}
