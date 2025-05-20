package com.redditclone.app.comment.application;

import com.redditclone.app.comment.application.dto.CommentDTO;
import com.redditclone.app.comment.domain.Comment;
import com.redditclone.app.comment.domain.CommentRepository;
import com.redditclone.app.comment.domain.CommentService;
import com.redditclone.app.post.domain.Post;
import com.redditclone.app.post.domain.PostRepository;
import com.redditclone.app.user.domain.User;
import com.redditclone.app.user.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public Comment createComment(CommentDTO commentDTO) {
        User user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(commentDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment parentComment = null;
        if (commentDTO.getParentCommentId() != null) {
            parentComment = getCommentById(commentDTO.getParentCommentId());
        }

        Comment comment = commentDTO.toEntity(user, post, parentComment);
        return commentRepository.save(comment);
    }

    @Override
    public Comment getCommentById(UUID id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> getCommentsByPost(UUID postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    public List<Comment> getCommentsByUser(UUID userId) {
        return commentRepository.findByUserId(userId);
    }

    @Override
    public void deleteComment(UUID id) {
        commentRepository.deleteById(id);
    }
}
