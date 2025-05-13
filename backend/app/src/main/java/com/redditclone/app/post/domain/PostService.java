package com.redditclone.app.post.domain;

import com.redditclone.app.post.application.PostDownloadDTO;
import com.redditclone.app.post.application.PostUploadDTO;

import java.util.List;
import java.util.UUID;

public interface PostService {
    Post uploadPost(PostUploadDTO post);
    PostDownloadDTO downloadPost(UUID id) throws Exception;
    List<Post> getAllPosts();
    List<Post> getPostsBySubreddit(UUID subredditId);
    List<Post> getPostsByUser(UUID userId);
    void deletePost(UUID id);
}
