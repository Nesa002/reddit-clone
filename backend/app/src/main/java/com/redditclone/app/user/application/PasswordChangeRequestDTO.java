package com.redditclone.app.user.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeRequestDTO {
    private UUID id;
    private String password;
}
