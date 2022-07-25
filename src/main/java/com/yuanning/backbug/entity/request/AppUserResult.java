package com.yuanning.backbug.entity.request;

import com.yuanning.backbug.entity.messageEnum.AppUserRole;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AppUserResult {
    private final Long id;
    private final AppUserRole appUserRole;
    private final String email;
    private final String firstName;
    private final String lastName;

    public AppUserResult(Long id, AppUserRole appUserRole, String email, String firstName, String lastName) {
        this.id = id;
        this.appUserRole = appUserRole;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
