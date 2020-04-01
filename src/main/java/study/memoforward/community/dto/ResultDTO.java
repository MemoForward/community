package study.memoforward.community.dto;

import study.memoforward.community.exception.CustomizeError;

public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;

    public static ResultDTO errorOf(CustomizeError error) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(error.getMessage());
        resultDTO.setCode(error.getErrorCode());
        return resultDTO;
    }

    public ResultDTO() {
    }

    public ResultDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResultDTO success(){
        return new ResultDTO(200, "请求成功");
    }

    public static <T> ResultDTO success(T data) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        resultDTO.setData(data);
        return resultDTO;

    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultDTO{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
