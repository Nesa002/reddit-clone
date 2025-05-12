package com.redditclone.app.post.application;

import com.redditclone.app.post.domain.Post;
import com.redditclone.app.post.domain.PostRepository;
import com.redditclone.app.post.domain.PostService;
import com.redditclone.app.subreddit.domain.Subreddit;
import com.redditclone.app.subreddit.domain.SubredditRepository;
import com.redditclone.app.user.domain.User;
import com.redditclone.app.user.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, SubredditRepository subredditRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.subredditRepository = subredditRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Post createPost(PostDTO postDTO) {
        User user = userRepository.findById(postDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Subreddit subreddit = subredditRepository.findById(postDTO.getSubredditId())
                .orElseThrow(() -> new RuntimeException("Subreddit not found"));

        Post post = postDTO.toEntity(user, subreddit);
        return postRepository.save(post);
    }


    @Override
    public Post getPostById(UUID id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> getPostsBySubreddit(UUID subredditId) {
        return postRepository.findBySubredditId(subredditId);
    }

    @Override
    public List<Post> getPostsByUser(UUID userId) {
        return postRepository.findByUserId(userId);
    }

    @Override
    public void deletePost(UUID id) {
        postRepository.deleteById(id);
    }
}
