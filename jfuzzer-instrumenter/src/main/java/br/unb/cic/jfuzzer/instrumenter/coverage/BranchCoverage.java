package br.unb.cic.jfuzzer.instrumenter.coverage;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import br.unb.cic.jfuzzer.instrumenter.coverage.BranchCoverage.MethodTransformVisitor;
import br.unb.cic.jfuzzer.util.coverage.CoverageException;
import br.unb.cic.jfuzzer.util.coverage.CoverageUtil;

public class BranchCoverage extends ClassVisitor implements ICoverage {
    static final String INVALID_INSTRUCTION = "Invalid instruction: ";

    String clazz;

    public BranchCoverage(final ClassVisitor classVisitor) {
        super(ASM9, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superClass, String[] interfaces) {
        super.visit(version, access, name, signature, superClass, interfaces);
        this.clazz = name;
        //System.err.println("CLASS=" + name);
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);

        //System.err.println("  - METHOD=" + name + " ... " + signature);        

        CoverageUtil.updateMethod(clazz);
        
//        MethodTransformVisitor methodTransformVisitor = new MethodTransformVisitor(mv, clazz, name);
//        writeCoverageEvent(mv, clazz, name, 0, "METHOD_ENTER");

        return mv == null ? null : new MethodTransformVisitor(mv, clazz, name);
    }

    class MethodTransformVisitor extends MethodVisitor implements ICoverage {
        protected int lastVisitedLine;
        protected String className;
        private String methodName;

        public MethodTransformVisitor(final MethodVisitor mv, String nameOfclass, String methodName) {
            super(ASM9, mv);
            this.className = nameOfclass;
            this.methodName = methodName;
        }

//        @Override
//        public void visitEnd() {
//            writeCoverageEvent(mv, className, methodName, lastVisitedLine, "METHOD_EXIT");
//            super.visitEnd();
//          }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void visitLineNumber(int line, Label start) {
            lastVisitedLine = line;

            CoverageUtil.updateLine(className, line);
            writeCoverageEvent(mv, className, methodName, line, "LINE_" + line);

            super.visitLineNumber(line, start);
        }

        /*
         * Visit a field instruction. The opcode must be one of: - GETSTATIC - PUTSTATIC
         * - GETFIELD - PUTFIELD
         */
        @Override
        public void visitFieldInsn(int opcode, String owner, String field, String descriptor) {
            //System.err.printf("%s.%s - FIELD: %s%n", className, methodName, field);

            writeCoverageEvent(mv, className, methodName, lastVisitedLine, "visitFieldInsn_" + field);

            super.visitFieldInsn(opcode, owner, field, descriptor);
        }

        /*
         * Visit an Int Increment Instruction. This instruction does not change the
         * operand stack.
         * 
         * @param idx index of the local variable
         * 
         * @increment ammount of the increment.
         * 
         * (non-Javadoc)
         * 
         * @see org.objectweb.asm.MethodVisitor#visitIincInsn(int, int)
         * 
         * @see
         * https://docs.oracle.com/javase/specs/jvms/se9/html/jvms-6.html#jvms-6.5.iinc
         */
        @Override
        public void visitIincInsn(int idx, int increment) {
            //System.err.printf("%s.%s - visitIincInsn %n", className, methodName);

            super.visitIincInsn(idx, increment);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitInsn(int opcode) {
            //System.err.printf("%s.%s - visitInsn: %d%n", className, methodName, opcode);

            writeCoverageEvent(mv, className, methodName, lastVisitedLine, "visitInsn_" + opcode);

            super.visitInsn(opcode);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitVarInsn(int opcode, int var) {
            switch (opcode) {
                case Opcodes.ILOAD:
                case Opcodes.LLOAD:
                case Opcodes.FLOAD:
                case Opcodes.DLOAD:
                case Opcodes.ALOAD:
                case Opcodes.ISTORE:
                case Opcodes.LSTORE:
                case Opcodes.FSTORE:
                case Opcodes.DSTORE:
                case Opcodes.ASTORE:
                case Opcodes.RET:
                    //System.err.printf("%s.%s - LOCAL_VARIABLE: %s%n", className, methodName, var);
                    writeCoverageEvent(mv, className, methodName, lastVisitedLine, "LOCAL_VARIABLE_" + var);
                    break;
                default:
                    throw new CoverageException(INVALID_INSTRUCTION + opcode);
            }
            super.visitVarInsn(opcode, var);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitTypeInsn(int opcode, String type) {
            switch (opcode) {
                case Opcodes.NEW:
                case Opcodes.ANEWARRAY:
                case Opcodes.CHECKCAST:
                case Opcodes.INSTANCEOF:
                    //System.err.printf("%s.%s - TYPE: %s%n", className, methodName, type);
                    writeCoverageEvent(mv, className, methodName, lastVisitedLine, "visitTypeInsn_" + type);
                    break;
                default:
                    throw new CoverageException(INVALID_INSTRUCTION + opcode);
            }
            super.visitTypeInsn(opcode, type);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitIntInsn(int opcode, int operand) {
            switch (opcode) {
                case Opcodes.BIPUSH:
                case Opcodes.SIPUSH:
                case Opcodes.NEWARRAY:
                    //System.err.printf("%s.%s - visitIntInsn: %d%n", className, methodName, operand);
                    writeCoverageEvent(mv, className, methodName, lastVisitedLine, "visitIntInsn_" + operand);
                    break;
                default:
                    throw new CoverageException(INVALID_INSTRUCTION + opcode);
            }
            super.visitIntInsn(opcode, operand);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterfaceInvoke) {
            switch (opcode) {
                case Opcodes.INVOKEVIRTUAL:
                case Opcodes.INVOKESPECIAL:
                case Opcodes.INVOKESTATIC:
                case Opcodes.INVOKEINTERFACE:
                    //System.err.printf("%s.%s - visitMethodInsn: %s%n", className, methodName, name + "___" + descriptor + "____" + owner);
//                    writeCoverageEvent(mv, className, methodName, "visitMethodInsn_" + name);
                   // writeCoverageEvent(mv, className, methodName, lastVisitedLine, "visitMethodInsn_" + descriptor);
                    writeCoverageEvent(mv, className, methodName, lastVisitedLine, "visitMethodInsn_" + name + "===" + descriptor + "===" + owner);
                    break;
                default:
                    throw new CoverageException(INVALID_INSTRUCTION + opcode);
            }
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterfaceInvoke);
        }

        /*
         * This is really tough.
         * 
         * The implementation here is based on the Eric Bodden's paper published at SOAP
         * 2012. <i>InvokeDynamic support in Soot</ i>
         */
        @Override
        public void visitInvokeDynamicInsn(String name, String descriptor, Handle bsmh, Object... bootstrapMethodArguments) {
            //System.err.printf("%s.%s - visitInvokeDynamicInsn: %s%n", className, methodName, bsmh);
//            writeCoverageEvent(mv, className, methodName, "visitInvokeDynamicInsn_" + bsmh);
            writeCoverageEvent(mv, className, methodName, lastVisitedLine, "visitInvokeDynamicInsn_" + bsmh);

            super.visitInvokeDynamicInsn(name, descriptor, bsmh, bootstrapMethodArguments);
        }

        @Override
        public void visitLdcInsn(Object value) {
            //System.err.printf("%s.%s - visitLdcInsn: %s%n", className, methodName, value);
//            writeCoverageEvent(mv, className, methodName, "visitLdcInsn_" + value);
            writeCoverageEvent(mv, className, methodName, lastVisitedLine, "visitLdcInsn_" + value);

            super.visitLdcInsn(value);
        }

        @Override
        public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
            //System.err.printf("%s.%s - visitLookupSwitchInsn%n", className, methodName, labels);
            writeCoverageEvent(mv, className, methodName, lastVisitedLine, "visitLookupSwitchInsn_" + labels);

            super.visitLookupSwitchInsn(dflt, keys, labels);
        }

        @Override
        public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
            //System.err.printf("%s.%s - visitTableSwitchInsn%n", className, methodName);
            writeCoverageEvent(mv, className, methodName, lastVisitedLine, "visitTableSwitchInsn_" + labels);

            super.visitTableSwitchInsn(min, max, dflt, labels);
        }

        @Override
        public void visitJumpInsn(int opcode, Label label) {
            CoverageUtil.updateBranch(className, methodName, lastVisitedLine);
            //System.err.printf("%s.%s - visitJumpInsn: %s%n", className, methodName, label);
            if (opcode == Opcodes.GOTO) {
                //System.err.println("GOTO: " + label);
                writeCoverageEvent(mv, className, methodName, lastVisitedLine, "GOTO_" + label);
            } else if (opcode == Opcodes.JSR) {
                throw new CoverageException(INVALID_INSTRUCTION + opcode);
            } else {
                switch (opcode) {
                    case Opcodes.IFEQ:
                    case Opcodes.IFNE:
                    case Opcodes.IFLT:
                    case Opcodes.IFLE:
                    case Opcodes.IFGT:
                    case Opcodes.IFGE:
                    case Opcodes.IF_ICMPEQ:
                    case Opcodes.IF_ICMPNE:
                    case Opcodes.IF_ICMPLT:
                    case Opcodes.IF_ICMPGE:
                    case Opcodes.IF_ICMPGT:
                    case Opcodes.IF_ICMPLE:
                    case Opcodes.IF_ACMPEQ:
                    case Opcodes.IF_ACMPNE:
                    case Opcodes.IFNULL:
                    case Opcodes.IFNONNULL:
                        //System.err.println("IF: " + label);
                        writeCoverageEvent(mv, className, methodName, lastVisitedLine, "IF_" + label);
                        break;
                    default:
                        throw new CoverageException(INVALID_INSTRUCTION + opcode);
                }
            }
            super.visitJumpInsn(opcode, label);
        }

    }
}
