package com.redditclone.app.subreddit.domain;

import java.util.List;
import java.util.UUID;

public interface SubredditService {
    Subreddit createSubreddit(Subreddit subreddit);
    Subreddit getSubredditById(UUID id);
    List<Subreddit> getAllSubreddits();
    void deleteSubreddit(UUID id);
}
