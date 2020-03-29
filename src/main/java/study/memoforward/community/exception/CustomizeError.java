package study.memoforward.community.exception;

public enum CustomizeError implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND("问题不见了，换个问题试试!"),
    USER_NOT_LOGIN("用户未登录！"),
    URL_NOT_FOUND(" 没有你找的资源，网址不正确或资源已过期!"),
    LOGIN_FAILED("登录失败！"),
    USER_NO_PERMISSION("用户无权限");

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    CustomizeError(String message) {
        this.message = message;
    }
}
