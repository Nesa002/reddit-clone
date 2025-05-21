package com.redditclone.app.post.application;

import com.redditclone.app.post.application.dto.PostDetailsDTO;
import com.redditclone.app.post.application.dto.PostPreviewDTO;
import com.redditclone.app.post.domain.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostPreviewDTO toPostPreviewDTO(Post post, byte[] fileData) {
        PostPreviewDTO postDTO = new PostPreviewDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        postDTO.setType(post.getType());
        postDTO.setUpvotes(post.getUpvotes());
        postDTO.setDownvotes(post.getDownvotes());
        postDTO.setAuthorUsername(post.getUser().getUsername());
        postDTO.setSubredditName(post.getSubreddit().getName());
        postDTO.setFile(fileData);
        postDTO.setCreatedAt(post.getCreatedAt());
        return postDTO;
    }

    public PostDetailsDTO toPostDetailsDTO(Post post, byte[] fileData) {
        PostDetailsDTO postDTO = new PostDetailsDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        postDTO.setType(post.getType());
        postDTO.setUpvotes(post.getUpvotes());
        postDTO.setDownvotes(post.getDownvotes());
        postDTO.setAuthorUsername(post.getUser().getUsername());
        postDTO.setAuthorId(post.getUser().getId());
        postDTO.setSubredditName(post.getSubreddit().getName());
        postDTO.setSubredditId(post.getSubreddit().getId());
        postDTO.setFile(fileData);
        postDTO.setCreatedAt(post.getCreatedAt());
        return postDTO;
    }
}

