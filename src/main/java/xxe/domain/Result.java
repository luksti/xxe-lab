package xxe.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {

    private boolean ok;

    private String message;

    private T data;

    private Result() {}

    private Result(boolean ok, T data) {
        this.ok = ok;
        this.data = data;
    }

    private Result(boolean ok, T data, String message) {
        this.ok = ok;
        this.data = data;
        this.message = message;
    }

    public static Result ok() {
        return new Result<>(true, null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(true, data);
    }

    public static Result nok(String message) {
        return new Result<>(false, null, message);
    }

    public boolean isOk() {
        return ok;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return "Result{" +
                "ok=" + ok +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
