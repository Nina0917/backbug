package com.yuanning.backbug.controller;

import com.yuanning.backbug.BaseTest;
import com.yuanning.backbug.entity.request.LoginRequest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends BaseTest {
    @Test
    public void should_login_success_and_get_token_in_header() throws Exception {
        LoginRequest loginRequest = new LoginRequest("admin@123.com", "root", true);
        this.mockMvc.perform(post("/api/auth/login")
                .content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
