package com.yuanning.backbug.service;

import com.yuanning.backbug.entity.User;
import com.yuanning.backbug.entity.Follow;
import com.yuanning.backbug.exceptionHandler.BaseException;
import com.yuanning.backbug.exceptionHandler.MessageEnum;
import com.yuanning.backbug.exceptionHandler.MessageUtil;
import com.yuanning.backbug.exceptionHandler.Result;
import com.yuanning.backbug.repository.AppUserRepository;
import com.yuanning.backbug.repository.FollowRepository;
import com.yuanning.backbug.security.common.utils.CurrentUserUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final AppUserRepository appUserRepository;
    private final StringRedisTemplate stringRedisTemplate;
    private final CurrentUserUtils currentUserUtils;

    public Result<String> follow(Long followUserId, Boolean isFollow) {
        // 1. get current user
        User followUser = null;
        String key = null;
        User user = currentUserUtils.getCurrentUser();

        followUser = appUserRepository.findById(followUserId).get();
        if (followUser == null) {
            throw new BaseException(MessageEnum.User_Not_Exist);
        }

        key = "follows:" + user.getId();


        // Determine whether to follow or unfollow
        if (isFollow) {
            // follow request, add follow to repository
            Follow f = new Follow();
            f.setUser(user);
            f.setFollowUser(followUser);
            f.setCreatedTime(LocalDateTime.now());
            Follow result = followRepository.save(f);
            // if save data to repository successfully, we add it to redis
            if  (!(result == null)) {
                // sadd follows:userId followUserId
                stringRedisTemplate.opsForSet().add(key,followUserId.toString());
            }

        } else {
            // unfollow request, delete follow from repository
            // query the follow according to user and followUser

            Long deleteId = followRepository.findByUserAndFollowUser(user, followUser).get().getId();
            // delete the record from database
            followRepository.deleteById(deleteId);
            // delete the record from redis
            stringRedisTemplate.opsForSet().remove(key,followUserId.toString());

        }

        return MessageUtil.success();
    }

    public Result<List<User>> getFollowing(int page) {

        // get current user
        User user = currentUserUtils.getCurrentUser();

        // query from the repository, get all following users
        // every page contains 5 rows
        Pageable pageable = PageRequest.of(page,5);
        List<Follow> followingUsers = followRepository.findAllByUser(user, pageable);
        // current user is not following anyone
        List<User> result = new ArrayList<>();
        if (followingUsers.size() == 0) {
            return MessageUtil.success(result);
        } else {
            for (Follow f : followingUsers) {
                result.add(f.getFollowUser());
            }
            return MessageUtil.success(result);
        }
    }

    public Result<User> getFollowingAccEmail(String email) {
        Optional<User> byEmail = appUserRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            User searchResult = byEmail.get();
            return MessageUtil.success(searchResult);
        } else {
            return MessageUtil.error(MessageEnum.User_Not_Exist.getResultCode(), MessageEnum.User_Not_Exist.getResultMsg());
        }
    }

    public Result<Integer> isFollow(Long followUserId) {
        // get current user
        User user = currentUserUtils.getCurrentUser();
        // get followUser
        User followUser = appUserRepository.findById(followUserId).get();

        // query in the follow table to see if the record exists
        Optional<Follow> result = followRepository.findByUserAndFollowUser(user, followUser);

        if (result.isPresent()) {
            return MessageUtil.success(1);
        } else {
            return MessageUtil.success(0);
        }
    }

    public Result<List<User>> recomFollows() {
        // get current user
        User user = currentUserUtils.getCurrentUser();
        // get current user's all following users' ids
        Long appUserId = user.getId();
        List<Long> followUserIds = followRepository.findFollowUserIdsByUserId(appUserId);
        for (Long id : followUserIds) {
            stringRedisTemplate.opsForSet().differenceAndStore("follows:"+id.toString(),"follows:"+appUserId, "recomdFollows:"+appUserId);
        }
        Set<String> recomdUserIds = stringRedisTemplate.opsForSet().distinctRandomMembers("recomdFollows:" + appUserId, 10);
        List<User> userList = new ArrayList<>();
        for (String sid : recomdUserIds) {
            userList.add(appUserRepository.findById(Long.parseLong(sid)).get());
        }
        return MessageUtil.success(userList);
    }
}
