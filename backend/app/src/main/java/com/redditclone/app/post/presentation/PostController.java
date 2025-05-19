package com.redditclone.app.post.presentation;

import com.redditclone.app.post.application.PostDownloadDTO;
import com.redditclone.app.post.application.PostUploadDTO;
import com.redditclone.app.post.domain.Post;
import com.redditclone.app.post.domain.PostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/posts")
@Validated
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Post> uploadPost(@Valid @ModelAttribute PostUploadDTO postDTO) {
        Post createdPost = postService.uploadPost(postDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDownloadDTO> downloadPost(@PathVariable UUID id) throws Exception {
        PostDownloadDTO post = postService.downloadPost(id);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/feed/{userId}")
    public ResponseEntity<Page<PostDownloadDTO>> getPostsForUser(
            Pageable pageable,
            @PathVariable UUID userId) {
        Page<PostDownloadDTO> posts = postService.getPostsForUser(pageable, userId);
        return ResponseEntity.ok(posts);
    }
}
