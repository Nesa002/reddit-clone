package com.redditclone.app.post.application;

import com.redditclone.app.post.application.dto.PostPreviewDTO;
import com.redditclone.app.post.domain.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostPreviewDTO toPostDownloadDTO(Post post, byte[] fileData) {
        PostPreviewDTO postDTO = new PostPreviewDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        postDTO.setType(post.getType());
        postDTO.setUpvotes(post.getUpvotes());
        postDTO.setDownvotes(post.getDownvotes());
        postDTO.setUsername(post.getUser().getUsername());
        postDTO.setSubredditName(post.getSubreddit().getName());
        postDTO.setFile(fileData);
        postDTO.setCreatedAt(post.getCreatedAt());
        return postDTO;
    }
}

