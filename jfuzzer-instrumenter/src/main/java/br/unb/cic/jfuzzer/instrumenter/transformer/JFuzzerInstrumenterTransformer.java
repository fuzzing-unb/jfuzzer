package br.unb.cic.jfuzzer.instrumenter.transformer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.InvocationTargetException;
import java.security.ProtectionDomain;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import br.unb.cic.jfuzzer.instrumenter.coverage.LineCoverage;
import br.unb.cic.jfuzzer.instrumenter.coverage.MethodCoverage;
import br.unb.cic.jfuzzer.instrumenter.coverage.JFuzzerInstrumenterCoverageType;
import br.unb.cic.jfuzzer.instrumenter.coverage.BranchCoverage;

public class JFuzzerInstrumenterTransformer implements ClassFileTransformer {

    @Deprecated private static final String EXCLUDED_PACKAGES = "excludedPackages=";
    private static final String INCLUDED_PACKAGES = "includedpackages=";
    private static final String ARGS_TYPE = "type=";
    @Deprecated private static final List<String> EXCLUDED_PACKAGES_PREFIXES = List.of("br/unb/cic/jfuzzer/util","br/unb/cic/jfuzzer/instrumenter","java","javax","sun","jdk","com/sun","com/ibm","org/xml","apple/awt","com.apple","org.objectweb.asm");
    //private static final List<String> INCLUDED_PACKAGES_PREFIXES = new LinkedList<>(); 
    
    private JFuzzerInstrumenterCoverageType type;
    private String agentArgs;
    private List<String> excludedPackages;
    private List<String> includedPackages;

    public JFuzzerInstrumenterTransformer(String agentArgs) {
        this.agentArgs = agentArgs;
        this.type = JFuzzerInstrumenterCoverageType.CONTROL_FLOW;
        excludedPackages = EXCLUDED_PACKAGES_PREFIXES;
        includedPackages = new LinkedList<>(); 
        parseArgs();
    }

    private void parseArgs() {
        String[] args = agentArgs.split(";");
        for (String argTmp : args) {
            String arg = argTmp.strip().toLowerCase();
            
            if (arg.startsWith(ARGS_TYPE)) {
                String acronym = arg.substring(arg.indexOf("=") + 1);
                Optional<JFuzzerInstrumenterCoverageType> fromAcronym = JFuzzerInstrumenterCoverageType.fromAcronym(acronym);
                if (fromAcronym.isPresent()) {
                    this.type = fromAcronym.get();
                }
            }
            
            if(arg.startsWith(EXCLUDED_PACKAGES)) {
                excludedPackages = new LinkedList<>();
                String[] packages = arg.substring(arg.indexOf("=") + 1).split(",");
                for(String pack: packages) {
                    excludedPackages.add(pack.strip().replaceAll("[\\s.]", "/"));
                }
            }
            
            if(arg.startsWith(INCLUDED_PACKAGES)) {
                System.err.println("INCLUDED_PACKAGES ... "+arg);
                includedPackages = new LinkedList<>();
                String[] packages = arg.substring(arg.indexOf("=") + 1).split(",");
                for(String pack: packages) {
                    includedPackages.add(pack.strip().replaceAll("[\\s.]", "/"));
                }
            }
        }
    }

    @Override
    public byte[] transform(Module module, ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
//        System.err.println("class .... " + className+" ..... type="+type.getLabel());

        if(validPackagePrefix(className)) {
//        if ((!className.startsWith("br/unb/cic/jfuzzer/util")
//                && !className.startsWith("java")
//                && !className.startsWith("javax")
//                && !className.startsWith("sun")
//                && !className.startsWith("jdk"))) {
            
//            excludedPackages.add("java.*");
//            excludedPackages.add("sun.*");
//            excludedPackages.add("javax.*");
//            excludedPackages.add("com.sun.*");
//            excludedPackages.add("com.ibm.*");
//            excludedPackages.add("org.xml.*");
//            excludedPackages.add("org.w3c.*");
//            excludedPackages.add("apple.awt.*");
//            excludedPackages.add("com.apple.*");

            switch (type) {
                case BRANCH:
                    return runAsm(BranchCoverage.class, classfileBuffer);
                case LINE:
                    return runAsm(LineCoverage.class, classfileBuffer);
                case FULL:
                    return runAsm(LineCoverage.class, classfileBuffer);
                default:
                    MethodCoverage coverage = new MethodCoverage();
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

    
    private boolean validPackagePrefix(String className) {
        return includedPackages.stream()
                .anyMatch(className::startsWith);
    }
    
    @Deprecated
    private boolean validPackagePrefixOld(String className) {
        //travata excludedPackages
        return !excludedPackages.stream()
                .anyMatch(className::startsWith);
    }
        
//        excludedPackages.add("java.*");
//        excludedPackages.add("sun.*");
//        excludedPackages.add("javax.*");
//        excludedPackages.add("com.sun.*");
//        excludedPackages.add("com.ibm.*");
//        excludedPackages.add("org.xml.*");
//        excludedPackages.add("org.w3c.*");
//        excludedPackages.add("apple.awt.*");
//        excludedPackages.add("com.apple.*");

    
}
