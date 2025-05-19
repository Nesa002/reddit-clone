package com.redditclone.app.post.domain;

import com.redditclone.app.post.application.PostDownloadDTO;
import com.redditclone.app.post.application.PostUploadDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PostService {
    Post uploadPost(PostUploadDTO post);
    PostDownloadDTO downloadPost(UUID id) throws Exception;
    Page<PostDownloadDTO> getPostsForUser(Pageable pageable, UUID userId);
}
