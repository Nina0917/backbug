package com.yuanning.backbug.controller;

import com.yuanning.backbug.exceptionHandler.Result;
import com.yuanning.backbug.service.FollowService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "api/v1/follow")
@AllArgsConstructor
@CrossOrigin
public class FollowController {
    private final FollowService followService;

    @PostMapping("/{id}/{isFollow}")
    public Result<String> follow(HttpServletRequest request, @PathVariable("id") Long followUserId, @PathVariable("isFollow") Boolean isFollow) {
        return followService.follow(request,followUserId,isFollow);
    }
}
