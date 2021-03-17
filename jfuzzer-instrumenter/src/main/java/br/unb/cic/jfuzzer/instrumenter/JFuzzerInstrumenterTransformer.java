package br.unb.cic.jfuzzer.instrumenter;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.logging.Level;
import java.util.logging.Logger;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.runtime.Desc;
import javassist.scopedpool.ScopedClassPoolFactoryImpl;
import javassist.scopedpool.ScopedClassPoolRepositoryImpl;

/**
 * Class transformer which intercepts the method call, used to emit the debug
 * information.
 */
public class JFuzzerInstrumenterTransformer implements ClassFileTransformer {

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

    @Override
    public byte[] transform(Module module, ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        byte[] byteCode = classfileBuffer;
        // If you wanted to intercept all the classs then you can remove this
        // conditional check.
//        if (className.equals("Example")) {
        if(className.startsWith("br") && !className.contains("observer") && !className.contains("InstrumenterClient")) {
//        if(!className.startsWith("java") && !className.contains("observer") && !className.contains("InstrumenterClient")) {
            log.info("Transforming the class " + className);
            try {
                ClassPool classPool = scopedClassPoolFactory.create(loader, rootPool, ScopedClassPoolRepositoryImpl.getInstance());
                CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));                               
                
                CtMethod[] methods = ctClass.getDeclaredMethods();
//                CtMethod[] methods = ctClass.getMethods();               
                
//                log.info("CLASSE " + ctClass.getName()+" ... "+ctClass.isFrozen());
////                ctClass.defrost();
                                               
                for (CtMethod method : methods) {                   
                    
                    log.info("Transforming the method " + method.getLongName());
                    String tmp = "br.unb.cic.jfuzzer.util.observer.JFuzzerObservable.setEvent(\"%s\", \"%s\", \"%s\");";
                    method.insertBefore(String.format(tmp, ctClass.getName(), method.getLongName(), "ENTER"));
                    
                    method.insertBefore(String.format(tmp, ctClass.getName(), method.getLongName(), "EXIT"));
//                    String msg = String.format(METHOD_ENTER, method.getLongName());
//                    method.insertBefore(String.format("br.unb.cic.jfuzzer.instrumenter.JFuzzerInstrumenterLogger.log(\"%s\");", msg));
//
//                    String msgAfter = String.format(METHOD_EXIT, method.getLongName());
//                    method.insertAfter(String.format("br.unb.cic.jfuzzer.instrumenter.JFuzzerInstrumenterLogger.log(\"%s\");", msgAfter));
                }

                byteCode = ctClass.toBytecode();
                ctClass.detach();
            } catch (Throwable ex) {
                log.log(Level.SEVERE, "Error in transforming the class: " + className, ex);
            }
        }
        return byteCode;
    }

    /**
     * An agent provides an implementation of this interface method in order to
     * transform class files. Transforms the given class file and returns a new
     * replacement class file. We check our config with classes and intercept only
     * when the Corresponding Class Name, Method Name, Method Signature matches.
     *
     * @param loader              The defining loader of the class to be
     *                            transformed, may be {@code null} if the bootstrap
     *                            loader.
     * @param className           The name of the class in the internal form of
     *                            fully qualified class.
     * @param classBeingRedefined If this is triggered by a redefine or re
     *                            transform, the class being redefined.
     * @param protectionDomain    The protection domain of the class being defined
     *                            or redefined.
     * @param classfileBuffer     The input byte buffer in class file format - Have
     *                            to be instrumented.
     * @return The transformed byte code.
     * @throws IllegalClassFormatException The IllegalClassFormat Exception.
     */
//    //@Override
//    public byte[] transform(Module module, ClassLoader loader, String className, Class<?> classBeingRedefined,
//                            ProtectionDomain protectionDomain, byte[] classfileBuffer)
//            throws IllegalClassFormatException {
//
//        byte[] byteCode = classfileBuffer;
//        // If you wanted to intercept all the classs then you can remove this conditional check.
//        if (className.equals("Example")) {
//            log.info("Transforming the class " + className);
//            try {
//                ClassPool classPool = scopedClassPoolFactory.create(loader, rootPool,
//                        ScopedClassPoolRepositoryImpl.getInstance());
//                CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
//                CtMethod[] methods = ctClass.getDeclaredMethods();
//
//                for (CtMethod method : methods) {
//                    if (method.getName().equals("main")){
//                        method.insertAfter("System.out.println(\"Logging using Agent\");");
//                    }
//                }
//                byteCode = ctClass.toBytecode();
//                ctClass.detach();
//            } catch (Throwable ex) {
//                log.log(Level.SEVERE, "Error in transforming the class: " + className, ex);
//            }
//        }
//        return byteCode;
//    }

}
