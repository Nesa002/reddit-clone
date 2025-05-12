package com.redditclone.app.post.domain;

import com.redditclone.app.post.application.PostDTO;

import java.util.List;
import java.util.UUID;

public interface PostService {
    Post createPost(PostDTO post);
    Post getPostById(UUID id);
    List<Post> getAllPosts();
    List<Post> getPostsBySubreddit(UUID subredditId);
    List<Post> getPostsByUser(UUID userId);
    void deletePost(UUID id);
}
