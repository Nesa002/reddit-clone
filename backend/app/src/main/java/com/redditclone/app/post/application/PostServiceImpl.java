package com.redditclone.app.post.application;

import com.redditclone.app.post.domain.Post;
import com.redditclone.app.post.domain.PostRepository;
import com.redditclone.app.post.domain.PostService;
import com.redditclone.app.post.domain.PostType;
import com.redditclone.app.shared.document.DocumentFileService;
import com.redditclone.app.subreddit.domain.Subreddit;
import com.redditclone.app.subreddit.domain.SubredditRepository;
import com.redditclone.app.user.application.PostMapper;
import com.redditclone.app.user.domain.User;
import com.redditclone.app.user.domain.UserRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.InputStream;
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
    public PostDownloadDTO downloadPost(UUID id) throws Exception {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        byte[] fileData = downloadFile(post);
        return postMapper.toPostDownloadDTO(post, fileData);
    }

    @Override
    public Page<PostDownloadDTO> getPostsForUser(Pageable pageable, UUID userId) {
        Page<Post> postPage = postRepository.findPostsForUserFollowedSubreddits(userId, pageable);

        return postPage.map(post -> postMapper.toPostDownloadDTO(post, downloadFile(post)));
    }

    @Nullable
    private byte[] downloadFile(Post post) {
        byte[] fileData = null;
        if (post.getFileUrl() != null) {
            try (InputStream inputStream = documentFileService.downloadFile(post.getFileUrl())) {
                fileData = inputStream.readAllBytes();  // Convert InputStream to byte[]
            } catch (Exception e) {
                throw new RuntimeException("Failed to download file", e);
            }
        }
        return fileData;
    }




}
