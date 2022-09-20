package com.yuanning.backbug.entity.request;

import com.yuanning.backbug.entity.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    public User toUser() {
        User user = new User();
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setEmail(email);
        user.setPassword(password);
        user.setEnabled(true);
        return user;
    }
}
