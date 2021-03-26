package br.unb.cic.jfuzzer.instrumenter.coverage;

import java.util.Iterator;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class BranchCoverage extends ClassVisitor implements Opcodes {
    String classname;

    public BranchCoverage(final ClassVisitor codevisitor) {
        super(ASM5, codevisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superClass, String[] interfaces) {
        super.visit(version, access, name, signature, superClass, interfaces);
        this.classname = name;
        System.err.println("CLASS="+name);
    }

//    @Override
//    public void visitEnd() {
//        Iterator it = cn.methods.iterator();
//
//        while (it.hasNext()) {
//            visitMethod((MethodNode) it.next());
//        }
//
//    }

    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);        

        
        
        System.err.println("  - METHOD=" + name + " ... " + signature);
        
        // TODO implement
        return mv == null ? null : new MethodTransformVisitor(mv, classname, name);
        // return null;
    }

    class MethodTransformVisitor extends MethodVisitor implements Opcodes {
        protected int lastVisitedLine;
        protected String className;
        private String methodName;

        public MethodTransformVisitor(final MethodVisitor mv, String nameOfclass, String methodName) {
            super(ASM5, mv);
            this.className = nameOfclass;
            this.methodName = methodName;
        }

        @Override
        public void visitLineNumber(int line, Label start) {
            if (!methodName.contains("updateEvent") 
                    && !methodName.contains("showEvents")
                    && 0 != line) {
                lastVisitedLine = line;

                mv.visitLdcInsn(className);
                mv.visitLdcInsn(methodName);
                mv.visitLdcInsn("LINE_" + line);
                // mv.visitLdcInsn(new Integer(line));
                // mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
                // "(I)Ljava/lang/Integer;", false);
                mv.visitMethodInsn(INVOKESTATIC, "br/unb/cic/jfuzzer/util/observer/JFuzzerObservable", "setEvent", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", false);

                
                
//                 mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//                mv.visitLdcInsn(className + " : " + line);
//                 mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
            }

            super.visitLineNumber(line, start);
        }

        @Override
        public void visitEnd() {
            super.visitEnd();
        }
    }
}
