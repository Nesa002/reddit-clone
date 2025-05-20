package com.redditclone.app.comment.domain;

import com.redditclone.app.comment.application.dto.CommentDTO;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    Comment createComment(CommentDTO comment);
    Comment getCommentById(UUID id);
    List<Comment> getAllComments();
    List<Comment> getCommentsByPost(UUID postId);
    List<Comment> getCommentsByUser(UUID userId);
    void deleteComment(UUID id);
}
