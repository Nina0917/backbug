package com.yuanning.backbug.controller;

import com.yuanning.backbug.entity.AppUser;
import com.yuanning.backbug.entity.RegistrationRequest;
import com.yuanning.backbug.exceptionHandler.Result;
import com.yuanning.backbug.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:8081",allowCredentials = "true")
public class RegistrationController {

    private final RegistrationService registrationService;


    @PostMapping
    public Result<String> register(@RequestBody RegistrationRequest request) {
        //System.out.println("receieved!");
        return registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

}
