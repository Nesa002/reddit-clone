package com.redditclone.app.post.application;

import com.redditclone.app.post.domain.Post;
import com.redditclone.app.post.domain.PostType;
import com.redditclone.app.subreddit.domain.Subreddit;
import com.redditclone.app.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Data
public class PostUploadDTO {

    private UUID id;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    private String content;

    @NotNull(message = "Post type cannot be null")
    private PostType type;

    private MultipartFile file;

    private int upvotes;

    private int downvotes;

    @NotNull(message = "User ID cannot be null")
    private UUID userId;

    @NotNull(message = "Subreddit ID cannot be null")
    private UUID subredditId;

    public Post toEntity(User user, Subreddit subreddit, String fileUrl, String thumbnailUrl) {
        Post post = new Post();
        post.setId(this.id);
        post.setTitle(this.title);
        post.setContent(this.content);
        post.setType(this.type);
        post.setFileUrl(fileUrl);
        post.setThumbnailUrl(thumbnailUrl);
        post.setUpvotes(this.upvotes);
        post.setDownvotes(this.downvotes);
        post.setUser(user);
        post.setSubreddit(subreddit);
        return post;
    }

}
