package br.unb.cic.jfuzzer.util.observer;

import java.io.Serializable;
import java.util.Objects;

public class Event implements Serializable {
    private static final long serialVersionUID = 1L;

    private String className;
    private String methodName;
    private String msg;

    public Event() {
    }

    public Event(String className, String methodName, String msg) {
        this.className = className;
        this.methodName = methodName;
        this.msg = msg;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, methodName, msg);
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
        return Objects.equals(className, other.className) && Objects.equals(methodName, other.methodName) && Objects.equals(msg, other.msg);
    }

    @Override
    public String toString() {
        return String.format("Event [className=%s, methodName=%s, msg=%s]", className, methodName, msg);
    }

}
