package com.yuanning.backbug.entity.messageEnum;

import lombok.Getter;

/**
 * @author shuang.kou
 */

@Getter
public enum RoleType {
    // 初始化用户
    USER("USER", "normal user"),
    TEMP_USER("TEMP_USER", "temporary user"),
    // 可以管理project
    MANAGER("MANAGER", "manager"),
    // getAll，和修改权限
    ADMIN("ADMIN", "admin");
    private final String name;
    private final String description;

    RoleType(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
