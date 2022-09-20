package com.yuanning.backbug.controller;

import com.yuanning.backbug.entity.User;
import com.yuanning.backbug.entity.request.AppUserResult;
import com.yuanning.backbug.entity.request.RegistrationRequest;
import com.yuanning.backbug.entity.request.UserUpdateRequest;



import com.yuanning.backbug.exceptionHandler.Result;
import com.yuanning.backbug.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author shuang.kou
 */
@RestController
// @RequiredArgsConstructor(onConstructor = @__(@Autowired))
@AllArgsConstructor
@RequestMapping("api/users")
@CrossOrigin
public class UserController {

    private final AppUserService userService;

    @PostMapping("/sign-up")
    public Result signUp(@RequestBody RegistrationRequest registrationRequest) {

        return userService.signUpUser(registrationRequest);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    // ResponseEntity represents the whole HTTP response: status code, headers, and body.
    // As a result, we can use it to fully configure the HTTP response.
    public Result<List<AppUserResult>> getAllUser(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum) {

        return userService.getAll(pageNum);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result update(@RequestBody UserUpdateRequest userUpdateRequest) {

        return userService.update(userUpdateRequest);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result deleteUserByUserName(@RequestParam("username") String email) {

        return userService.delete(email);
    }
}
