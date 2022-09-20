package com.yuanning.backbug.controller;

import com.yuanning.backbug.entity.User;
import com.yuanning.backbug.exceptionHandler.Result;
import com.yuanning.backbug.service.FollowService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/follows")
@AllArgsConstructor
@CrossOrigin
public class FollowController {
    private final FollowService followService;

    /**
     * add or delete follow
     * @param followUserId: The id of the user that the current user wants to follow
     * @param isFollow: true represents the action of follow, false represents unfollow
     * @return
     */
    @PostMapping("add-follow")
    public Result follow(@RequestParam Long followUserId, @RequestParam Boolean isFollow) {
        return followService.follow(followUserId,isFollow);
    }

    /**
     * Get appUsers that the current user is following
     * @param page: page number
     * @return
     */
    @GetMapping("get-all")
    public Result<List<User>> getFollowing(@RequestParam(defaultValue = "0") int page) {
        return followService.getFollowing(page);
    }

    /**
     * search user by email
     * @param email: the email of the user that we want to search
     * @return: success means the user exists, error means the user does not exist
     */
    @GetMapping("get-acc-email")
    public Result<User> getFollowingAccEmail(@RequestParam String email) {
        return followService.getFollowingAccEmail(email);
    }

    /**
     * if the current user is following another user
     * 0 represents no, 1 represents yes
     * @param followUserId
     * @return
     */
    @GetMapping("isFollow")
    public Result<Integer> isFollow(@RequestParam Long followUserId) {
        return followService.isFollow(followUserId);
    }

    /**
     * recommend people the current user may know
     * @return
     */
    @GetMapping("recomFollows")
    public Result<List<User>> recomFollows() {
        return followService.recomFollows();
    }

}
