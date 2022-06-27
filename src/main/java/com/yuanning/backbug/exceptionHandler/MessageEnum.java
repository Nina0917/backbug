package com.yuanning.backbug.exceptionHandler;

public enum MessageEnum {
    SYSTEM_ERROR(1001, "系统异常"),
    NAME_EXCEEDED_CHARRACTER_LIMIT(2000, "姓名超过了限制，最大4个字符!"),
    Email_invalid(501, "Email format is not valid"),
    Email_occupied(501, "Email has already been taken"),
    EMPTY_FIELD(500, "There are empty fields");


    private Integer code;

    private String message;

    MessageEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
