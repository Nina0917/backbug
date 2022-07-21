package com.yuanning.backbug.exceptionHandler;

public enum MessageEnum {
    SYSTEM_ERROR(1001, "系统异常"),
    NAME_EXCEEDED_CHARRACTER_LIMIT(2000, "姓名超过了限制，最大4个字符!"),
    Email_invalid(501, "Email format is not valid"),
    Email_occupied(501, "Email has already been taken"),
    Email_sent_Failed(501, "Could not send email to this address"),
    EMPTY_FIELD(500, "There are empty fields"),
    User_Not_Exist(502, "Email does not exist"),
    User_Not_Active(502, "Current Email has not been activated"),
    Password_Not_Correct(502, "Password does not match your account"),

    User_not_Login(503, "User is not lgin in yet."),

    Friend_already_exists(504, "You_have_added_this_user_as_friend");


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
