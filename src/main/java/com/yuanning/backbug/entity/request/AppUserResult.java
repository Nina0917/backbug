package com.yuanning.backbug.entity.request;

import com.yuanning.backbug.entity.messageEnum.RoleType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AppUserResult {
    private final Long id;
    private final String email;
    private final String firstName;
    private final String lastName;

    public AppUserResult(Long id, String email, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
