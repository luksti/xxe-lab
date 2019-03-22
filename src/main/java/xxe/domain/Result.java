package xxe.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {

    @ApiModelProperty(value = /* "was request processed without errors?" */ "Vastus kas sõnum võeti vastu ilma vigadeta.", required = true)
    private boolean ok;

    @ApiModelProperty(value = /* "error message (if any)" */ "Viga kirjeldav sõnum, kui seda on.")
    private String message;

    @ApiModelProperty(value = /* "main response data (if any)" */ "Teenuse poolt tagastatav info, kui seda on.")
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
