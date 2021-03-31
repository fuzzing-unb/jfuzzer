package br.unb.cic.jfuzzer.instrumenter.coverage;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import br.unb.cic.jfuzzer.util.coverage.CoverageEvent;

public interface ICoverage extends Opcodes {

    static final String OBSERVABLE_CLASS = "br/unb/cic/jfuzzer/util/observer/JFuzzerObservable";
    static final String OBSERVABLE_METHOD_NAME = "setEvent";
    static final String OBSERVABLE_METHOD_PARAMS = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V";

    default void writeCoverageEvent(MethodVisitor mv, String className, String methodName, CoverageEvent event) {
        writeCoverageEvent(mv, className, methodName, event.toString());
    }

    default void writeCoverageEvent(MethodVisitor mv, String className, String methodName, String event) {
        mv.visitLdcInsn(className);
        mv.visitLdcInsn(methodName);
        mv.visitLdcInsn(event);
        mv.visitMethodInsn(INVOKESTATIC, OBSERVABLE_CLASS, OBSERVABLE_METHOD_NAME, OBSERVABLE_METHOD_PARAMS, false);
    }

}
