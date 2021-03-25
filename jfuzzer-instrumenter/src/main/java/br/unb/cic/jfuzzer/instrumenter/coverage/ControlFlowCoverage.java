package br.unb.cic.jfuzzer.instrumenter.coverage;

import java.io.ByteArrayInputStream;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.unb.cic.jfuzzer.instrumenter.transformer.JFuzzerInstrumenterTransformer;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.runtime.Desc;
import javassist.scopedpool.ScopedClassPoolFactoryImpl;
import javassist.scopedpool.ScopedClassPoolRepositoryImpl;

public class ControlFlowCoverage {
    private static final String METHOD_ENTER = "Entering method: %s";
    private static final String METHOD_EXIT = "Exiting method: %s";

    /**
     * We use JUL as this is an java agent which should not depend on any other
     * framework than java.
     */
    private static final Logger log = Logger.getLogger(JFuzzerInstrumenterTransformer.class.getName());
    private ScopedClassPoolFactoryImpl scopedClassPoolFactory = new ScopedClassPoolFactoryImpl();

    private ClassPool rootPool;

    public void init() {
        // Sets the useContextClassLoader =true to get any class type to be correctly
        // resolved with correct OSGI module
        Desc.useContextClassLoader = true;
        rootPool = ClassPool.getDefault();
    }

    public byte[] transform(Module module, ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        byte[] byteCode = classfileBuffer;
        // If you wanted to intercept all the classs then you can remove this
        // conditional check.
//        if (className.equals("Example")) {
//        if(className.startsWith("br") && !className.contains("observer") && !className.contains("InstrumenterClient")) {

        if ((className.startsWith("br") || className.startsWith("org/apache/commons/codec")) && !className.contains("observer") && !className.contains("InstrumenterClient") && !className.contains("CommonsCodecRunner")) {

//        if (  !className.startsWith("java/util") 
//                && !className.startsWith("sun/") && !className.contains("observer") 
//                && !className.contains("InstrumenterClient") && !className.contains("CommonsCodecRunner") && !className.contains("WeakPairMap")) {

            log.info("Transforming the class " + className);
            try {
                ClassPool classPool = scopedClassPoolFactory.create(loader, rootPool, ScopedClassPoolRepositoryImpl.getInstance());
                CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));

                CtMethod[] methods = ctClass.getDeclaredMethods();
//                CtMethod[] methods = ctClass.getMethods();               

//                log.info("CLASSE " + ctClass.getName()+" ... "+ctClass.isFrozen());
////                ctClass.defrost();

                for (CtMethod method : methods) {
                    if (canModify(method)) {
                        log.fine("Transforming the method " + method.getLongName());

                        String tmp = "br.unb.cic.jfuzzer.util.observer.JFuzzerObservable.setEvent(\"%s\", \"%s\", \"%s\");";
                        method.insertBefore(String.format(tmp, ctClass.getName(), method.getLongName(), "ENTER"));
                        method.insertAfter(String.format(tmp, ctClass.getName(), method.getLongName(), "EXIT"));

//                    String msg = String.format(METHOD_ENTER, method.getLongName());
//                    method.insertBefore(String.format("br.unb.cic.jfuzzer.instrumenter.JFuzzerInstrumenterLogger.log(\"%s\");", msg));
//
//                    String msgAfter = String.format(METHOD_EXIT, method.getLongName());
//                    method.insertAfter(String.format("br.unb.cic.jfuzzer.instrumenter.JFuzzerInstrumenterLogger.log(\"%s\");", msgAfter));

                    }
                }

                byteCode = ctClass.toBytecode();
                ctClass.detach();
            } catch (Throwable ex) {
                log.log(Level.SEVERE, "Error in transforming the class: " + className, ex);
            }
        }
        return byteCode;
    }

    public static boolean canModify(CtMethod method) {
        return !(Modifier.isInterface(method.getModifiers())
                || Modifier.isNative(method.getModifiers())
                || Modifier.isAbstract(method.getModifiers())
                || Modifier.isVolatile(method.getModifiers())
                || Modifier.isStrict(method.getModifiers()));
    }
}
