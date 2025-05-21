package com.redditclone.app.post.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
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
        ORDER BY p.createdAt DESC
    """)
    Page<Post> findPostsForUserFeedNew(@Param("userId") UUID userId, Pageable pageable);

    @Query("""
        SELECT p FROM User u
        JOIN u.joinedSubreddits js
        JOIN Post p ON p.subreddit = js
        WHERE u.id = :userId
          AND p.createdAt >= :cutoffDate
        ORDER BY p.upvotes DESC
    """)
    Page<Post> findPostsForUserFeedTop(@Param("userId") UUID userId, @Param("cutoffDate") Instant cutoffDate, Pageable pageable);

    @Query("""
        SELECT p FROM User u
        JOIN u.joinedSubreddits js
        JOIN Post p ON p.subreddit = js
        WHERE u.id = :userId
        ORDER BY p.upvotes DESC
    """)
    Page<Post> findPostsForUserFeedTopAllTime(@Param("userId") UUID userId, Pageable pageable);


}
