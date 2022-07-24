package com.yuanning.backbug.controller;

import com.yuanning.backbug.entity.AppUser;
import com.yuanning.backbug.exceptionHandler.Result;
import com.yuanning.backbug.service.FollowService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/follow")
@AllArgsConstructor
@CrossOrigin
public class FollowController {
    private final FollowService followService;

    @PostMapping("follow")
    public Result<String> follow(HttpServletRequest request, @RequestParam Long followUserId, @RequestParam Boolean isFollow) {
        return followService.follow(request,followUserId,isFollow);
    }

    /**
     * Get AppUsers that the current user is following
     * @param request
     * @return
     */
    @GetMapping("getAll")
    public Result<List<AppUser>> getFollowing(@RequestParam int page, HttpServletRequest request) {
        return followService.getFollowing(page, request);
    }

    @GetMapping("search")
    public Result<AppUser> getFollowingAccEmail(@RequestParam String email) {
        return followService.getFollowingAccEmail(email);
    }

    /**
     * 0 represents no, 1 represents yes
     * @param followUserId
     * @return
     */
    @GetMapping("isFollow")
    public Result<Integer> isFollow(HttpServletRequest request, @RequestParam Long followUserId) {
        return followService.isFollow(request, followUserId);
    }

    @GetMapping("recomFollows")
    public Result<AppUser> recomFollows(HttpServletRequest request) {
        return followService.recomFollows(request);
    }

}
