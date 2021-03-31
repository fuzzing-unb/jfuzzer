package br.unb.cic.jfuzzer.instrumenter.coverage;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import br.unb.cic.jfuzzer.util.coverage.CoverageEvent;

public interface ICoverage extends Opcodes {

    static final String OBSERVABLE_CLASS = "br/unb/cic/jfuzzer/util/observer/JFuzzerObservable";
    static final String OBSERVABLE_METHOD_NAME = "setEvent";
    @Deprecated static final String OBSERVABLE_METHOD_PARAMS = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V";
    static final String OBSERVABLE_METHOD_PARAMS_NOVO = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V";

    default void writeCoverageEvent(MethodVisitor mv, String className, String methodName, CoverageEvent event) {
        writeCoverageEvent(mv, className, methodName, event.toString());
    }

    default void writeCoverageEvent(MethodVisitor mv, String className, String methodName, String event) {
        if (canVisitMethod(methodName)) {
            mv.visitLdcInsn(className);
            mv.visitLdcInsn(methodName);
            mv.visitLdcInsn(event);
            mv.visitMethodInsn(INVOKESTATIC, OBSERVABLE_CLASS, OBSERVABLE_METHOD_NAME, OBSERVABLE_METHOD_PARAMS, false);
        }
    }
    
    default void writeCoverageEvent(MethodVisitor mv, String className, String methodName, int line, String event) {
        if (canVisitMethod(methodName)) {
            mv.visitLdcInsn(className);
            mv.visitLdcInsn(methodName);
            //mv.visitLdcInsn(Integer.valueOf(line));
            //TODO tratar como integer
            mv.visitLdcInsn(""+line);
            mv.visitLdcInsn(event);
            mv.visitMethodInsn(INVOKESTATIC, OBSERVABLE_CLASS, OBSERVABLE_METHOD_NAME, OBSERVABLE_METHOD_PARAMS_NOVO, false);
        }
    }

    default boolean canVisitMethod(String methodName) {
        return !methodName.contains("updateEvent")
                && !methodName.contains("showEvents")
                && !methodName.contains("writeCoverageEvent")
                && !methodName.contains("canVisitMethod");
    }

}
