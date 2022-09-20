package com.yuanning.backbug.exceptionHandler;

public enum




MessageEnum implements BaseErrorInfoInterface{
    SYSTEM_ERROR("1001", "系统异常"),
    NAME_EXCEEDED_CHARRACTER_LIMIT("2000", "姓名超过了限制，最大4个字符!"),
    Email_invalid("501", "Email format is not valid"),
    Email_occupied("501", "Email has already been taken"),
    Email_sent_Failed("501", "Could not send email to this address"),
    EMPTY_FIELD("500", "There are empty fields"),
    User_Not_Exist("502", "Email does not exist"),
    User_Not_Active("502", "Current Email has not been activated"),
    Password_Not_Correct("502", "Password does not match your account"),
    User_not_Login("503", "User is not lgin in yet."),
    Friend_already_exists("504", "You_have_added_this_user_as_friend"),

    // 数据操作错误定义
    SUCCESS("200", "成功!"),
    BODY_NOT_MATCH("400","请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH("401","请求的数字签名不匹配!"),
    NOT_FOUND("404", "未找到该资源!"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误!"),
    SERVER_BUSY("503","服务器正忙，请稍后再试!");

    /** 错误码 */
    private String resultCode;

    /** 错误描述 */
    private String resultMsg;

    MessageEnum(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    @Override
    public String getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }
}
