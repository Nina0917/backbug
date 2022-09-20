package com.yuanning.backbug.service;

import com.yuanning.backbug.entity.User;
import com.yuanning.backbug.entity.request.LoginRequest;
import com.yuanning.backbug.exceptionHandler.MessageUtil;
import com.yuanning.backbug.exceptionHandler.Result;
import com.yuanning.backbug.security.common.utils.CurrentUserUtils;

import com.yuanning.backbug.security.common.utils.JwtTokenUtils;
import com.yuanning.backbug.security.entity.JwtUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shuang.kou
 **/
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService {
    private final AppUserService appUserService;
    private final StringRedisTemplate stringRedisTemplate;
    private final CurrentUserUtils currentUserUtils;

    public String createToken(LoginRequest loginRequest) {
        // 根据username/email找到user
        User user = appUserService.find(loginRequest.getEmail());
        // 验证密码
        if (!appUserService.check(loginRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("The user name or password is not correct.");
        }
        JwtUser jwtUser = new JwtUser(user);
        // 如果user被disable了
        // 默认jwtUser的enable是true
        if (!jwtUser.isEnabled()) {
            throw new BadCredentialsException("User is forbidden to login");
        }
        // 将 GrantedAuthority List变为String
        List<String> authorities = jwtUser.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        //创建token
        String token = JwtTokenUtils.createToken(user.getEmail(), user.getId().toString(), authorities, loginRequest.getRememberMe());
        // redis里保存user id和token，用set
        stringRedisTemplate.opsForValue().set(user.getId().toString(), token);
        System.out.println(token);
        return token;
    }

    public Result removeToken() {
        stringRedisTemplate.delete(currentUserUtils.getCurrentUser().getId().toString());
        return MessageUtil.success();
    }
}
