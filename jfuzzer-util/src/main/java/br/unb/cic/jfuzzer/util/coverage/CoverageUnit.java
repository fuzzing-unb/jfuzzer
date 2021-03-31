package br.unb.cic.jfuzzer.util.coverage;

import java.util.Objects;

public class CoverageUnit {

    private String className;
    private String methodName;
    private int lineNumber;
    private CoverageEvent event;
    private String msg;

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

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public CoverageEvent getEvent() {
        return event;
    }

    public void setEvent(CoverageEvent event) {
        this.event = event;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, event, lineNumber, methodName, msg);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CoverageUnit other = (CoverageUnit) obj;
        return Objects.equals(className, other.className) && event == other.event && lineNumber == other.lineNumber && Objects.equals(methodName, other.methodName) && Objects.equals(msg, other.msg);
    }

    @Override
    public String toString() {
        return String.format("CoverageUnit [className=%s, methodName=%s, lineNumber=%s, event=%s, msg=%s]", className, methodName, lineNumber, event, msg);
    }

}
