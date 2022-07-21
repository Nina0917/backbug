package com.yuanning.backbug.service;

import com.yuanning.backbug.entity.AppUser;
import com.yuanning.backbug.entity.Friend;
import com.yuanning.backbug.exceptionHandler.MessageEnum;
import com.yuanning.backbug.exceptionHandler.MessageUtil;
import com.yuanning.backbug.exceptionHandler.Result;
import com.yuanning.backbug.repository.AppUserRepository;
import com.yuanning.backbug.repository.FriendRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final AppUserRepository appUserRepository;

    public Result<String> saveFriend(HttpServletRequest request, Long appUserId) {
        // get current user's email
        String email =  (String)request.getSession().getAttribute("email");
        // if user not login in yet
        if (email == "" || email == null) {
            return MessageUtil.error(MessageEnum.User_not_Login.getCode(), MessageEnum.User_not_Login.getMessage());
        }
        // get current user
        AppUser user1 = appUserRepository.findByEmail(email).get();
        AppUser user2 = appUserRepository.findById(appUserId).get();
        AppUser firstuser = user1;
        AppUser seconduser = user2;
        if(user1.getId() > user2.getId()){
            firstuser = user2;
            seconduser = user1;
        }

        Friend friend = new Friend();
        if( !(friendRepository.existsByFirstUserAndSecondUser(firstuser,seconduser)) ){
            friend.setCreatedDate(new Date());
            friend.setFirstUser(firstuser);
            friend.setSecondUser(seconduser);
            friendRepository.save(friend);
            return MessageUtil.success("添加好友成功!");
        } else {
            // two users has already been friend
            return MessageUtil.error(MessageEnum.Friend_already_exists.getCode(), MessageEnum.Friend_already_exists.getMessage());
        }
    }

    public Result<List<AppUser>> getFriends(HttpServletRequest request) {
        // get current user's email
        String email =  (String)request.getSession().getAttribute("email");
        // get current user
        AppUser currentUser = appUserRepository.findByEmail(email).get();

        List<Friend> friendsByFirstUser = friendRepository.findByFirstUser(currentUser);
        List<Friend> friendsBySecondUser = friendRepository.findBySecondUser(currentUser);
        List<AppUser> friendUsers = new ArrayList<>();

        for (Friend friend : friendsByFirstUser) {
            friendUsers.add(appUserRepository.findById(friend.getSecondUser().getId()).get());
        }
        for (Friend friend : friendsBySecondUser) {
            friendUsers.add(appUserRepository.findById(friend.getFirstUser().getId()).get());
        }

        return MessageUtil.success(friendUsers);
    }

    public Result<List<AppUser>> getPotentialFriends(HttpServletRequest request) {
        // if the user has no friends, select 10 friends randomly for him

        // if the user already has friends, select

        // 等b站的程序设计回我
        return MessageUtil.success();

    }
}
