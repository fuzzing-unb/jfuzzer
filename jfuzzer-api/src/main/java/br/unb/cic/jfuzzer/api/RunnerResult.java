package br.unb.cic.jfuzzer.api;

import java.io.Serializable;
import java.util.Objects;

public class RunnerResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private T input;
    private RunnerStatus status;

    public RunnerResult(T input, RunnerStatus status) {
        this.input = input;
        this.status = status;
    }

    public T getInput() {
        return input;
    }

    public RunnerStatus getStatus() {
        return status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(input, status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RunnerResult<?> other = (RunnerResult<?>) obj;
        return Objects.equals(input, other.input) && status == other.status;
    }

    @Override
    public String toString() {
        return String.format("[status=%s, input=%s]", status, input);
    }

}
