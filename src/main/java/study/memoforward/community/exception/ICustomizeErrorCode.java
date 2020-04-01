package study.memoforward.community.exception;

// 这个接口只会被一个枚举类实现，不知道意义何在，想不出来它还有其他的用法
public interface ICustomizeErrorCode {
    String getMessage();
    int getErrorCode();
}
