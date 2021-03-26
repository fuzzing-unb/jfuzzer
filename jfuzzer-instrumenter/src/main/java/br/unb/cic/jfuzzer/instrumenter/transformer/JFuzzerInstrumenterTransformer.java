package br.unb.cic.jfuzzer.instrumenter.transformer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.InvocationTargetException;
import java.security.ProtectionDomain;
import java.util.Optional;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import br.unb.cic.jfuzzer.instrumenter.coverage.BranchCoverage;
import br.unb.cic.jfuzzer.instrumenter.coverage.ControlFlowCoverage;
import br.unb.cic.jfuzzer.instrumenter.coverage.JFuzzerInstrumenterCoverageType;

public class JFuzzerInstrumenterTransformer implements ClassFileTransformer {

    private static final String ARGS_TYPE = "type=";
    private JFuzzerInstrumenterCoverageType type;
    private String agentArgs;

    public JFuzzerInstrumenterTransformer(String agentArgs) {
        this.agentArgs = agentArgs;
        this.type = JFuzzerInstrumenterCoverageType.CONTROL_FLOW;
        parseArgs();
    }

    private void parseArgs() {
        String[] args = agentArgs.split(";");
        for (String argTmp : args) {
            String arg = argTmp.strip().toLowerCase();
            if (arg.startsWith(ARGS_TYPE)) {
                System.err.println(ARGS_TYPE + arg);
                String acronym = arg.substring(arg.indexOf("=") + 1);
                System.err.println("acronym=" + acronym);
                Optional<JFuzzerInstrumenterCoverageType> fromAcronym = JFuzzerInstrumenterCoverageType.fromAcronym(acronym);
                if (fromAcronym.isPresent()) {
                    this.type = fromAcronym.get();
                    System.err.println("********** type=" + type);
                }
            }
        }
    }

    @Override
    public byte[] transform(Module module, ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
//        System.err.println("class .... " + className+" ..... type="+type.getLabel());

        if ((!className.startsWith("br/unb/cic/jfuzzer/util")
                && !className.startsWith("java")
                && !className.startsWith("javax")
                && !className.startsWith("sun")
                && !className.startsWith("jdk"))) {

            switch (type) {
                case BRANCH:
                    return runAsm(BranchCoverage.class, classfileBuffer);
                case LINE:
                    return runAsm(BranchCoverage.class, classfileBuffer);
                case FULL:
                    return runAsm(BranchCoverage.class, classfileBuffer);
                default:
                    ControlFlowCoverage coverage = new ControlFlowCoverage();
                    coverage.init();
                    return coverage.transform(module, loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
            }
        }

        return classfileBuffer;
    }

    private byte[] runAsm(Class<? extends ClassVisitor> visitorClass, byte[] classfileBuffer) {
        ClassReader cr = new ClassReader(classfileBuffer);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        ClassVisitor visitor;
        try {
            visitor = visitorClass.getConstructor(ClassVisitor.class).newInstance(cw);
            cr.accept(visitor, 0);
            return cw.toByteArray();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return classfileBuffer;
    }

}
