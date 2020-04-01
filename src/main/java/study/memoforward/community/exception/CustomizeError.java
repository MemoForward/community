package study.memoforward.community.exception;

public enum CustomizeError implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2000,"你操作的问题消失了，小朋友你是否有很多问号？"),
    URL_NOT_FOUND(2001," 没有你找的资源，网址不正确或资源已过期!"),
    COMMENT_NOT_GOOD(2002,"评论出现问题，系统正在排查。"),
    USER_NOT_LOGIN(3000,"用户未登录"),
    LOGIN_FAILED(3001,"网络错误，登录失败！"),
    USER_NO_PERMISSION(3002,"用户无权限"),
    HIT_NO_TARGET(3003,"未选中任何问题和评论进行回复"),
    COMMENT_TYPE_NOT_EXIST(3004,"评论类型错误或不存在"),
    COMMENT_NOT_EXIST(3005,"你回复的评论已被删除"),
    USER_RELOGIN_IN(3006,"用户未登录，点击确认登录"),
    COMMENT_CANNOT_BE_NULL(3007, "评论不能为空");

    private final String message;
    private final int errorCode;

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    CustomizeError(int errorCode, String message) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
