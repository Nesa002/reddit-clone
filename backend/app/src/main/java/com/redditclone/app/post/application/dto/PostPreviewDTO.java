package com.redditclone.app.post.application.dto;

import com.redditclone.app.post.domain.PostType;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class PostPreviewDTO {
    private UUID id;
    private String title;
    private String content;
    private PostType type;
    private byte[] file;
    private int upvotes;
    private int downvotes;
    private String username;
    private String subredditName;
    private Instant createdAt;
}
