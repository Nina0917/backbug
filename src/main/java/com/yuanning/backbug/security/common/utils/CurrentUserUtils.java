package com.yuanning.backbug.security.common.utils;

import com.yuanning.backbug.entity.User;
import com.yuanning.backbug.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author shuang.kou
 * @description 获取当前请求的用户
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CurrentUserUtils {

    private final AppUserService userService;

    public User getCurrentUser() {
        return userService.find(getCurrentUserName());
    }

    private  String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }
}
