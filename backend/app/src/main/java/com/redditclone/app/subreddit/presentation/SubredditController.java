package com.redditclone.app.subreddit.presentation;

import com.redditclone.app.subreddit.application.dto.SubredditDTO;
import com.redditclone.app.subreddit.domain.Subreddit;
import com.redditclone.app.subreddit.domain.SubredditService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/subreddits")
@Validated
public class SubredditController {
    private final SubredditService subredditService;

    public SubredditController(SubredditService subredditService) {
        this.subredditService = subredditService;
    }

    @PostMapping
    public ResponseEntity<Subreddit> createSubreddit(@Valid @RequestBody SubredditDTO subredditDTO) {
        Subreddit createdSubreddit = subredditService.createSubreddit(subredditDTO.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubreddit);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subreddit> getSubredditById(@PathVariable UUID id) {
        Subreddit subreddit = subredditService.getSubredditById(id);
        return ResponseEntity.ok(subreddit);
    }

    @GetMapping
    public ResponseEntity<List<Subreddit>> getAllSubreddits() {
        List<Subreddit> subreddits = subredditService.getAllSubreddits();
        return ResponseEntity.ok(subreddits);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubreddit(@PathVariable UUID id) {
        subredditService.deleteSubreddit(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
