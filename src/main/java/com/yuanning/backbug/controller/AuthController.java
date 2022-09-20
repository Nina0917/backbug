package com.yuanning.backbug.controller;


import com.yuanning.backbug.entity.request.LoginRequest;
import com.yuanning.backbug.exceptionHandler.MessageUtil;
import com.yuanning.backbug.exceptionHandler.Result;
import com.yuanning.backbug.security.common.constants.SecurityConstants;
import com.yuanning.backbug.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
// @RequiredArgsConstructor(onConstructor = @__(@Autowired))
@AllArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    // @ApiOperation("登录")
    public Result<String> login(@RequestBody LoginRequest loginRequest) {
        // 创建token
        String token = authService.createToken(loginRequest);
        // 获取header
        HttpHeaders httpHeaders = new HttpHeaders();
        // 设置header
        httpHeaders.set(SecurityConstants.TOKEN_HEADER, token);
        // 返回结果
        return MessageUtil.success(token);
    }

    @PostMapping("/logout")
    // @ApiOperation("退出")
    public Result logout() {
        return authService.removeToken();
    }
}
