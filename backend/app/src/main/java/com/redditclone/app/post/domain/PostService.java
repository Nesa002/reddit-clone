package com.redditclone.app.post.domain;

import com.redditclone.app.post.application.dto.PostDetailsDTO;
import com.redditclone.app.post.application.dto.PostPreviewDTO;
import com.redditclone.app.post.application.dto.PostUploadDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PostService {
    Post uploadPost(PostUploadDTO post);
    PostDetailsDTO downloadPost(UUID id) throws Exception;
    Page<PostPreviewDTO> getPostsForUser(Pageable pageable, UUID userId, FilterType filterType);
}
