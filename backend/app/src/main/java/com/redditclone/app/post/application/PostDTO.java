package com.redditclone.app.post.application;

import com.redditclone.app.post.domain.Post;
import com.redditclone.app.post.domain.PostType;
import com.redditclone.app.subreddit.domain.Subreddit;
import com.redditclone.app.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class PostDTO {

    private UUID id;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    private String content;

    @NotNull(message = "Post type cannot be null")
    private PostType type;

    private int upvotes;
    private int downvotes;

    @NotNull(message = "User ID cannot be null")
    private UUID userId;

    @NotNull(message = "Subreddit ID cannot be null")
    private UUID subredditId;

    public Post toEntity(User user, Subreddit subreddit) {
        Post post = new Post();
        post.setId(this.id);
        post.setTitle(this.title);
        post.setContent(this.content);
        post.setType(this.type);
        post.setUpvotes(this.upvotes);
        post.setDownvotes(this.downvotes);
        post.setUser(user);
        post.setSubreddit(subreddit);
        return post;
    }

}
