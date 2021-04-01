package br.unb.cic.jfuzzer.util.observer;

import java.io.Serializable;
import java.util.Objects;

@Deprecated
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;

    private String className;
    private String methodName;
    private String line;
    private String msg;

    public Event() {
    }

    public Event(String className, String methodName, String msg) {
        this(className, methodName, 0+"", msg);
    }

    public Event(String className, String methodName, String line, String msg) {
        this.className = className;
        this.methodName = methodName;
        this.line = line;
        this.msg = msg;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getLine() {
        return line;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, line, methodName, msg);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Event other = (Event) obj;
        return Objects.equals(className, other.className) && line == other.line && Objects.equals(methodName, other.methodName) && Objects.equals(msg, other.msg);
    }

    @Override
    public String toString() {
        return String.format("Event [className=%s, methodName=%s, line=%s, msg=%s]", className, methodName, line, msg);
    }

}
