package com.redditclone.app.comment.application;

import com.redditclone.app.comment.domain.Comment;
import com.redditclone.app.post.domain.Post;
import com.redditclone.app.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class CommentDTO {

    private UUID id;

    @NotBlank(message = "Content cannot be blank")
    private String content;

    private UUID userId;
    private UUID postId;
    private UUID parentCommentId;

    private int upvotes;
    private int downvotes;

    public Comment toEntity(User user, Post post, Comment parentComment) {
        Comment comment = new Comment();
        comment.setId(this.id);
        comment.setContent(this.content);
        comment.setUser(user);
        comment.setPost(post);
        comment.setParentComment(parentComment);
        comment.setUpvotes(this.upvotes);
        comment.setDownvotes(this.downvotes);
        return comment;
    }
}
