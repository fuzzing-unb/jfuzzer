package br.unb.cic.jfuzzer.api;

import java.io.Serializable;
import java.util.Objects;

public class RunnerResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private T input;
    private RunnerStatus status;
    private Integer code;
    private String message;

    public RunnerResult(T input, RunnerStatus status) {
        this.input = input;
        this.status = status;
    }

    public RunnerResult(T input, RunnerStatus status, Integer code, String message) {
        this.input = input;
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public T getInput() {
        return input;
    }

    public RunnerStatus getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, input, message, status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RunnerResult other = (RunnerResult) obj;
        return Objects.equals(code, other.code) && Objects.equals(input, other.input) && Objects.equals(message, other.message) && status == other.status;
    }

    @Override
    public String toString() {
        return String.format("RunnerResult [status=%s, code=%s, message=%s, input=%s]", status, code, message, input);
    }

}
