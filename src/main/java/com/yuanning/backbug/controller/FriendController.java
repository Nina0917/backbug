package com.yuanning.backbug.controller;

import com.yuanning.backbug.entity.AppUser;
import com.yuanning.backbug.exceptionHandler.Result;
import com.yuanning.backbug.service.FriendService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/follow")
@AllArgsConstructor
@CrossOrigin
public class FriendController {
    private final FriendService friendService;

    @PostMapping("/{id}/{isFollow}")
    public Result<String> addFriend(HttpServletRequest request, @RequestParam Long appUserId) {
        return friendService.saveFriend(request,appUserId);
    }

    @GetMapping("getAll")
    public Result<List<AppUser>> getFriends(HttpServletRequest request) {
        return friendService.getFriends(request);
    }

    @GetMapping("listPotentialFriends")
    public Result<List<AppUser>> getPotentialFriends(HttpServletRequest request) {
        return friendService.getPotentialFriends(request);
    }
}
