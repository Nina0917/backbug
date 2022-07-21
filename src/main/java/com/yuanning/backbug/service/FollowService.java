package com.yuanning.backbug.service;

import com.yuanning.backbug.entity.AppUser;
import com.yuanning.backbug.exceptionHandler.Result;
import com.yuanning.backbug.repository.AppUserRepository;
import com.yuanning.backbug.repository.FollowRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final AppUserRepository appUserRepository;

    public Result<String> follow(HttpServletRequest request, Long followUserId, Boolean isFollow) {
        // 1. get current user
        String email =  (String)request.getSession().getAttribute("email");
        Long userId = appUserRepository.findByEmail(email).get().getId();

        // Determine whether to follow or unfollow
        if (isFollow) {
            // add follow to repository

        }

    }
}
