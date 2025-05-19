package com.redditclone.app.post.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    List<Post> findBySubredditId(UUID subredditId);

    List<Post> findByUserId(UUID userId);

    @Query("""
        SELECT p FROM User u
        JOIN u.joinedSubreddits js
        JOIN Post p ON p.subreddit = js
        WHERE u.id = :userId
    """)
    Page<Post> findPostsForUserFollowedSubreddits(@Param("userId") UUID userId, Pageable pageable);

}
