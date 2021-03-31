package br.unb.cic.jfuzzer.instrumenter.coverage;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import br.unb.cic.jfuzzer.util.coverage.CoverageUtil;

public class BranchCoverage extends ClassVisitor implements Opcodes {
    String classname;

    public BranchCoverage(final ClassVisitor codevisitor) {
        super(ASM5, codevisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superClass, String[] interfaces) {
        super.visit(version, access, name, signature, superClass, interfaces);
        this.classname = name;
        System.err.println("CLASS=" + name);
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
        
        CoverageUtil.updateMethod(classname);

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

        /*
         * Visit a field instruction. The opcode must be one of: - GETSTATIC - PUTSTATIC
         * - GETFIELD - PUTFIELD
         */
        @Override
        public void visitFieldInsn(int opcode, String owner, String field, String descriptor) {
            System.err.printf("%s.%s - FIELD: %s%n", className, methodName, field);
//            switch (opcode) {
//                case Opcodes.GETSTATIC:
//                case Opcodes.PUTSTATIC:
//                case Opcodes.GETFIELD:
//                case Opcodes.PUTFIELD:
//                    System.err.println("FIELD: " + field);
//                    break;
//                default:
//                    // throw RuntimeExceptionFactory.illegalArgument(vf.string("invalid instruction
//                    // " + opcode), null, null);
//                    throw new RuntimeException("invalid instruction " + opcode);
//            }
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
//            System.err.println("visitIincInsn");
            System.err.printf("%s.%s - visitIincInsn %n", className, methodName);

            super.visitIincInsn(idx, increment);
        }

        @Override
        public void visitInsn(int opcode) {
            System.err.printf("%s.%s - visitInsn: %d%n", className, methodName, opcode);
            
            super.visitInsn(opcode);
        }

        /*
         * Visit a local variable instructions.
         * 
         * (non-Javadoc)
         * 
         * @see org.objectweb.asm.MethodVisitor#visitVarInsn(int, int)
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
//                    System.err.println("LOCAL_VARIABLE: " + var);
                    System.err.printf("%s.%s - LOCAL_VARIABLE: %s%n", className, methodName, var);
                    break;
                default:
//                throw RuntimeExceptionFactory.illegalArgument(vf.string("invalid instruction " + opcode), null, null);
                    throw new RuntimeException("invalid instruction " + opcode);
            }
            super.visitVarInsn(opcode, var);
        }

        /*
         * Visit a type instruction.
         * 
         * (non-Javadoc)
         * 
         * @see org.objectweb.asm.MethodVisitor#visitTypeInsn(int, java.lang.String)
         */
        @Override
        public void visitTypeInsn(int opcode, String type) {            
            switch (opcode) {
                case Opcodes.NEW:
//                newInstanceIns(objectConstructor(type.replace("/", ".")));
//                break;
                case Opcodes.ANEWARRAY:
//                aNewArrayIns(type(type));
//                break;
                case Opcodes.CHECKCAST:
//                simpleCastIns(type(type));
//                break;
                case Opcodes.INSTANCEOF:
//                instanceOfIns(type(type));
//                    System.err.println("************ TYPE: " + type);
                    System.err.printf("%s.%s - TYPE: %s%n", className, methodName, type);
                    break;
                default:
//                throw RuntimeExceptionFactory.illegalArgument(vf.string("invalid instruction " + opcode), null, null);
                    throw new RuntimeException("invalid instruction " + opcode);
            }
            super.visitTypeInsn(opcode, type);
        }

        @Override
        public void visitIntInsn(int opcode, int operand) {
            switch (opcode) {
                case Opcodes.BIPUSH:
//                pushConstantValue(type("I"), Immediate.iValue(Value.intValue(operand)));
//                break;
                case Opcodes.SIPUSH:
//                pushConstantValue(type("I"), Immediate.iValue(Value.intValue(operand)));
//                break;
                case Opcodes.NEWARRAY:
//                createNewArrayIns(operand);
//                    System.err.println("visitIntInsn: " + operand);
                    System.err.printf("%s.%s - visitIntInsn: %d%n", className, methodName, operand);
                    break;
                default:
//                throw RuntimeExceptionFactory.illegalArgument(vf.string("invalid instruction " + opcode), null, null);
                    throw new RuntimeException("invalid instruction " + opcode);
            }
            super.visitIntInsn(opcode, operand);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String descriptor,
                boolean isInterfaceInvoke) {
            switch (opcode) {
                case Opcodes.INVOKEVIRTUAL:
                case Opcodes.INVOKESPECIAL:
                case Opcodes.INVOKESTATIC:
                case Opcodes.INVOKEINTERFACE:
//                    System.err.println("visitMethodInsn: " + name);
                    System.err.printf("%s.%s - visitIntInsn: %s%n", className, methodName, name);
                    break;
                default:
//                throw RuntimeExceptionFactory.illegalArgument(vf.string("invalid instruction " + opcode), null, null);
                    throw new RuntimeException("invalid instruction " + opcode);
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
        public void visitInvokeDynamicInsn(String name, String descriptor, Handle bsmh,
                Object... bootstrapMethodArguments) {

//            System.err.println("visitInvokeDynamicInsn: " + bsmh);
            System.err.printf("%s.%s - visitInvokeDynamicInsn: %s%n", className, methodName, bsmh);
            super.visitInvokeDynamicInsn(name, descriptor, bsmh, bootstrapMethodArguments);
        }

        @Override
        public void visitLdcInsn(Object value) {
//            for (Environment env : stack.peek().environments()) {
//                env.operands.push(new Operand(toJimpleTypedValue(value)));
//            }

//            System.err.println("visitLdcInsn: " + value);
            System.err.printf("%s.%s - visitLdcInsn: %s%n", className, methodName, value);
            super.visitLdcInsn(value);
        }

        @Override
        public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
//            System.err.println("visitLookupSwitchInsn");
            System.err.printf("%s.%s - visitLookupSwitchInsn%n", className, methodName, labels);
//            for (Environment env : stack.peek().environments()) {
//                Immediate key = env.operands.pop().immediate;
//
//                List<CaseStmt> caseStmts = new ArrayList<>();
//
//                for (int i = 0; i < keys.length; i++) {
//                    caseStmts.add(CaseStmt.caseOption(keys[i], labels[i].toString()));
//                }
//
//                if (dflt != null) {
//                    caseStmts.add(CaseStmt.defaultOption(dflt.toString()));
//                }
//                env.instructions.add(Statement.lookupSwitch(key, caseStmts));
//            }
            super.visitLookupSwitchInsn(dflt, keys, labels);
        }

        @Override
        public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
            System.err.println("visitTableSwitchInsn: " + dflt);
            System.err.printf("%s.%s - visitTableSwitchInsn%n", className, methodName);
//            List<CaseStmt> caseStmts = new ArrayList<>();
//            for (Label label : labels) {
//                caseStmts.add(CaseStmt.caseOption(label.getOffset(), label.toString()));
//            }
//            if (dflt != null) {
//                caseStmts.add(CaseStmt.defaultOption(dflt.toString()));
//            }
//            for (Environment env : stack.peek().environments()) {
//                Immediate key = env.operands.pop().immediate;
//                env.instructions.add(Statement.tableSwitch(key, min, max, caseStmts));
//            }
            super.visitTableSwitchInsn(min, max, dflt, labels);
        }

        @Override
        public void visitJumpInsn(int opcode, Label label) {
            System.err.printf("%s.%s - visitJumpInsn: %s%n", className, methodName, label);
            if (opcode == Opcodes.GOTO) {
//                notifyGotoStmt(Statement.gotoStmt(label.toString()), label.toString()); // TODO: investigate this decision here.
                System.err.println("GOTO");
            } else if (opcode == Opcodes.JSR) {
//                throw RuntimeExceptionFactory.illegalArgument(vf.string("unsupported instruction JSR" + opcode), null,
//                        null);
                throw new RuntimeException("invalid instruction " + opcode);
            } else {
                switch (opcode) {
                    case Opcodes.IFEQ:
//                        exp = Expression.cmpeq(first, second);
//                        break;
                    case Opcodes.IFNE:
//                        exp = Expression.cmpne(first, second);
//                        break;
                    case Opcodes.IFLT:
//                        exp = Expression.cmplt(first, second);
//                        break;
                    case Opcodes.IFLE:
//                        exp = Expression.cmple(first, second);
//                        break;
                    case Opcodes.IFGT:
//                        exp = Expression.cmpgt(first, second);
//                        break;
                    case Opcodes.IFGE:
//                        exp = Expression.cmpge(first, second);
//                        break;
                    case Opcodes.IF_ICMPEQ:
//                        second = env.operands.pop().immediate;
//                        exp = Expression.cmpeq(second, first);
//                        break;
                    case Opcodes.IF_ICMPNE:
//                        second = env.operands.pop().immediate;
//                        exp = Expression.cmpne(second, first);
//                        break;
                    case Opcodes.IF_ICMPLT:
//                        second = env.operands.pop().immediate;
//                        exp = Expression.cmplt(second, first);
//                        break;
                    case Opcodes.IF_ICMPGE:
//                        second = env.operands.pop().immediate;
//                        exp = Expression.cmpge(second, first);
//                        break;
                    case Opcodes.IF_ICMPGT:
//                        second = env.operands.pop().immediate;
//                        exp = Expression.cmpgt(second, first);
//                        break;
                    case Opcodes.IF_ICMPLE:
//                        second = env.operands.pop().immediate;
//                        exp = Expression.cmple(second, first);
//                        break;
                    case Opcodes.IF_ACMPEQ:
//                        second = env.operands.pop().immediate;
//                        exp = Expression.cmpeq(second, first);
//                        break;
                    case Opcodes.IF_ACMPNE:
//                        second = env.operands.pop().immediate;
//                        exp = Expression.cmpne(second, first);
//                        break;
                    case Opcodes.IFNULL:
//                        exp = Expression.isNull(first);
//                        break;
                    case Opcodes.IFNONNULL:
//                        exp = Expression.isNotNull(first);
                        System.err.println("IF: " + label);
                        break;
                    default:
//                        throw RuntimeExceptionFactory.illegalArgument(vf.string("invalid instruction " + opcode), null,
//                                null);
                        throw new RuntimeException("invalid instruction " + opcode);
                }

            }
//            referencedLabels.add(label.toString());
            super.visitJumpInsn(opcode, label);
        }

    }
}
