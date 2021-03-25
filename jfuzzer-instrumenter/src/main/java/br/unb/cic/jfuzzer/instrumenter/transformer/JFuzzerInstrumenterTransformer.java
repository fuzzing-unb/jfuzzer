package br.unb.cic.jfuzzer.instrumenter.transformer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Optional;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import br.unb.cic.jfuzzer.instrumenter.coverage.BranchCoverage;
import br.unb.cic.jfuzzer.instrumenter.coverage.ControlFlowCoverage;
import br.unb.cic.jfuzzer.instrumenter.coverage.JFuzzerInstrumenterCoverageType;

public class JFuzzerInstrumenterTransformer implements ClassFileTransformer {

    private JFuzzerInstrumenterCoverageType type;
    private String agentArgs;

    public JFuzzerInstrumenterTransformer(String agentArgs) {
        this.agentArgs = agentArgs;
        this.type = JFuzzerInstrumenterCoverageType.CONTROL_FLOW;
        parseArgs();
    }

    private void parseArgs() {
        // TODO
//        String[] args = agentArgs.split(";");
//        for (String argTmp : args) {
//            String arg = argTmp.strip().toLowerCase();
//            if (arg.startsWith("type=")) {
//                System.err.println("type=" + arg);
//                String acronym = arg.substring(arg.indexOf("="));
//                System.err.println("acronym=" + acronym);
//                Optional<JFuzzerInstrumenterCoverageType> fromAcronym = JFuzzerInstrumenterCoverageType.fromAcronym(acronym);
//                if (fromAcronym.isPresent()) {
//                    type = fromAcronym.get();
//                }
//            }
//        }
    }

    @Override
    public byte[] transform(Module module, ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        switch (type) {
            case BRANCH:
                ClassReader cr = new ClassReader(classfileBuffer);
                ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
                BranchCoverage ca = new BranchCoverage(cw);
                cr.accept(ca, 0);
                return cw.toByteArray();
            case FULL:
                return classfileBuffer;
            default:
                ControlFlowCoverage coverage = new ControlFlowCoverage();
                coverage.init();
                return coverage.transform(module, loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
        }

    }

}
