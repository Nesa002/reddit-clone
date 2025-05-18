package com.redditclone.app.post.application;

import com.redditclone.app.post.domain.PostType;
import lombok.Data;

import java.io.InputStream;
import java.util.UUID;

@Data
public class PostDownloadDTO {
    private UUID id;
    private String title;
    private String content;
    private PostType type;
    private byte[] file;
    private int upvotes;
    private int downvotes;
    private UUID userId;
    private UUID subredditId;
}
