package com.yuanning.backbug.entity.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


/**
 * @author shuang.kou
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean enabled;
}
