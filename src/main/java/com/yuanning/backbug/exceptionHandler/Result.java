package com.yuanning.backbug.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Result<T> {
    // 0 is Success
    private String code; // 状态码

    // "SUCCESS"
    private String message; // 状态描述信息

    private T data; // 定义为范型

    public Result() {

    }

}
