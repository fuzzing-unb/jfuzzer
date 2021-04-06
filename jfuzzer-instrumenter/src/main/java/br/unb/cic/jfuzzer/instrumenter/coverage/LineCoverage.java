package br.unb.cic.jfuzzer.instrumenter.coverage;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import br.unb.cic.jfuzzer.util.coverage.CoverageUtil;

public class LineCoverage extends ClassVisitor implements ICoverage {
    String classname;

    public LineCoverage(final ClassVisitor codevisitor) {
        super(ASM9, codevisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superClass, String[] interfaces) {
        super.visit(version, access, name, signature, superClass, interfaces);
        this.classname = name;
        System.err.println("CLASS=" + name);
    }

    /**
     * Visits a method of the class. This method <i>must</i> return a new
     * {@link MethodVisitor} instance (or {@literal null}) each time it is called,
     * i.e., it should not return a previously returned visitor.
     *
     * @param access     the method's access flags (see {@link Opcodes}). This
     *                   parameter also indicates if the method is synthetic and/or
     *                   deprecated.
     * @param name       the method's name.
     * @param descriptor the method's descriptor (see {@link Type}).
     * @param signature  the method's signature. May be {@literal null} if the
     *                   method parameters, return type and exceptions do not use
     *                   generic types.
     * @param exceptions the internal names of the method's exception classes (see
     *                   {@link Type#getInternalName()}). May be {@literal null}.
     * @return an object to visit the byte code of the method, or {@literal null} if
     *         this class visitor is not interested in visiting the code of this
     *         method.
     */
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);

        System.err.println("  - METHOD=" + name + " ... " + signature);
        CoverageUtil.updateMethod(classname, name);

        return mv == null ? null : new MethodTransformVisitor(mv, classname, name);
    }

    class MethodTransformVisitor extends MethodVisitor implements Opcodes {
        protected int lastVisitedLine;
        protected String className;
        private String methodName;

        public MethodTransformVisitor(final MethodVisitor mv, String nameOfclass, String methodName) {
            super(ASM9, mv);
            this.className = nameOfclass;
            this.methodName = methodName;
        }

        @Override
        public void visitLineNumber(int line, Label start) {
            if (!methodName.contains("updateEvent")
                    && !methodName.contains("showEvents")
                    && !methodName.contains("writeCoverageEvent")
                    && 0 != line) {
                lastVisitedLine = line;

                //mv.visitLdcInsn(className + ":" + line + "\n");

                CoverageUtil.updateLine(className, line);
                writeCoverageEvent(mv, className, methodName, line, "LINE_" + line);
            }

            super.visitLineNumber(line, start);
        }
    }
}
