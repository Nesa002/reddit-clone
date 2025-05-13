package com.redditclone.app.post.application;

import com.redditclone.app.post.domain.Post;
import com.redditclone.app.post.domain.PostRepository;
import com.redditclone.app.post.domain.PostService;
import com.redditclone.app.shared.document.DocumentFileService;
import com.redditclone.app.subreddit.domain.Subreddit;
import com.redditclone.app.subreddit.domain.SubredditRepository;
import com.redditclone.app.user.domain.User;
import com.redditclone.app.user.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final DocumentFileService documentFileService;

    public PostServiceImpl(PostRepository postRepository, SubredditRepository subredditRepository, UserRepository userRepository, DocumentFileService documentFileService) {
        this.postRepository = postRepository;
        this.subredditRepository = subredditRepository;
        this.userRepository = userRepository;
        this.documentFileService = documentFileService;
    }

    @Override
    public Post uploadPost(PostUploadDTO postDTO) {
        User user = userRepository.findById(postDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Subreddit subreddit = subredditRepository.findById(postDTO.getSubredditId())
                .orElseThrow(() -> new RuntimeException("Subreddit not found"));

        String fileUrl = null;
        try {
            fileUrl = documentFileService.uploadFile(postDTO.getFile(), "posts");
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file", e);
        }

        Post post = postDTO.toEntity(user, subreddit, fileUrl);
        return postRepository.save(post);
    }


    @Override
    public PostDownloadDTO downloadPost(UUID id) throws Exception {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        byte[] fileData = null;
        if (post.getFileUrl() != null) {
            try (InputStream inputStream = documentFileService.downloadFile(post.getFileUrl())) {
                fileData = inputStream.readAllBytes();  // Convert InputStream to byte[]
            } catch (Exception e) {
                throw new RuntimeException("Failed to download file", e);
            }
        }

        PostDownloadDTO postDTO = new PostDownloadDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        postDTO.setType(post.getType());
        postDTO.setUpvotes(post.getUpvotes());
        postDTO.setDownvotes(post.getDownvotes());
        postDTO.setUserId(post.getUser().getId());
        postDTO.setSubredditId(post.getSubreddit().getId());
        postDTO.setFile(fileData);

        return postDTO;
    }


    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> getPostsBySubreddit(UUID subredditId) {
        return postRepository.findBySubredditId(subredditId);
    }

    @Override
    public List<Post> getPostsByUser(UUID userId) {
        return postRepository.findByUserId(userId);
    }

    @Override
    public void deletePost(UUID id) {
        postRepository.deleteById(id);
    }
}
