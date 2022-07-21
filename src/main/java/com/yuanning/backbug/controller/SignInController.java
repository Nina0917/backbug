package com.yuanning.backbug.controller;

import com.yuanning.backbug.entity.AppUser;
import com.yuanning.backbug.exceptionHandler.MessageUtil;
import com.yuanning.backbug.exceptionHandler.Result;
import com.yuanning.backbug.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "api/v1/signIn")
@AllArgsConstructor
//@CrossOrigin(origins = "http://localhost:8081",allowCredentials = "true")
@CrossOrigin
public class SignInController {
    private final AppUserService appUserService;

    @PostMapping()
    public Result<String> signIn(HttpServletRequest request, @RequestBody AppUser appUser) {
        // System.out.println("success!");
        Result<String> result = appUserService.signIn(appUser);
        if (result.getCode() == 0){
            //获取session
            HttpSession session = request.getSession();
            session.setAttribute("email",appUser.getEmail());
            session.setAttribute("userName",appUser.getLastName());
        }
        return result;
    }
}
