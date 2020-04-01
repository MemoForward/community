package study.memoforward.community.exception;

public class CustomizeException extends RuntimeException{

    private String message;
    private int errorcode;

    public CustomizeException(int errorcode, String message) {
        this.message = message;
        this.errorcode = errorcode;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public CustomizeException(ICustomizeErrorCode errorCode){
        this.message = errorCode.getMessage();
        this.errorcode = errorCode.getErrorCode();
    }

    @Override
    public String getMessage() {
        return message;
    }


}
