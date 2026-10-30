package cn.fly.smartshopping.common;

public class Result <T>{
    private Integer code;//表示业务状态码。

    private String message;

    private T data;

    public Result(){}

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data);
    }

    public static <T> Result<T> error(String message) { 
        return new Result<>(500, message, null);
    }
//401：用户没有登录。
    public static <T> Result<T> unauthorized(String message) {
        return new Result<>(401, message, null);
    }


 // 403：用户已经登录，但是没有执行该操作的权限。

public static <T> Result<T> forbidden(String message) {
    return new Result<>(403, message, null);
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
}
