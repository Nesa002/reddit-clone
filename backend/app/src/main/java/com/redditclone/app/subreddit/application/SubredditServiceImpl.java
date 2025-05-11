package com.redditclone.app.subreddit.application;

import com.redditclone.app.subreddit.domain.Subreddit;
import com.redditclone.app.subreddit.domain.SubredditRepository;
import com.redditclone.app.subreddit.domain.SubredditService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SubredditServiceImpl implements SubredditService {
    private final SubredditRepository subredditRepository;

    public SubredditServiceImpl(SubredditRepository subredditRepository) {
        this.subredditRepository = subredditRepository;
    }

    @Override
    public Subreddit createSubreddit(Subreddit subreddit) {
        return subredditRepository.save(subreddit);
    }

    @Override
    public Subreddit getSubredditById(UUID id) {
        return subredditRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subreddit not found"));
    }

    @Override
    public List<Subreddit> getAllSubreddits() {
        return subredditRepository.findAll();
    }

    @Override
    public void deleteSubreddit(UUID id) {
        subredditRepository.deleteById(id);
    }
}
