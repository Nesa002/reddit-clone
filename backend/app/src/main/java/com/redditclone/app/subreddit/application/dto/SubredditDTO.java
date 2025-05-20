package com.redditclone.app.subreddit.application.dto;

import com.redditclone.app.subreddit.domain.Subreddit;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SubredditDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private String bannerImageUrl;
    private String iconImageUrl;

    public Subreddit toEntity() {
        Subreddit subreddit = new Subreddit();
        subreddit.setName(this.name);
        subreddit.setDescription(this.description);
        subreddit.setBannerImageUrl(this.bannerImageUrl);
        subreddit.setIconImageUrl(this.iconImageUrl);
        subreddit.setMemberCount(0);  // Default member count
        return subreddit;
    }
}