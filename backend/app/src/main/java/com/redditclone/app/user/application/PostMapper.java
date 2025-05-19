package com.redditclone.app.user.application;

import com.redditclone.app.post.application.PostDownloadDTO;
import com.redditclone.app.post.domain.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostDownloadDTO toPostDownloadDTO(Post post, byte[] fileData) {
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
}

