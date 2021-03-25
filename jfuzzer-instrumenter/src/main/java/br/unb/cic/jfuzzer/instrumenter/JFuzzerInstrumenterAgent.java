package br.unb.cic.jfuzzer.instrumenter;

import java.lang.instrument.Instrumentation;
import java.util.logging.Logger;

import br.unb.cic.jfuzzer.instrumenter.transformer.JFuzzerInstrumenterTransformer;

public class JFuzzerInstrumenterAgent {
    private static final Logger log = Logger.getLogger(JFuzzerInstrumenterAgent.class.getName());

    /**
     * As soon as the JVM initializes, This method will be called. Configs for
     * intercepting will be read and added to Transformer so that Transformer will
     * intercept when the corresponding Java Class and Method is loaded.
     *
     * @param agentArgs       The list of agent arguments
     * @param instrumentation The instrumentation object
     * @throws InstantiationException While an instantiation of object cause an
     *                                error.
     */
    public static void premain(String agentArgs, Instrumentation instrumentation) throws InstantiationException {
        log.info("Starting Java Agent......");
        JFuzzerInstrumenterTransformer interceptingClassTransformer = new JFuzzerInstrumenterTransformer(agentArgs);
        instrumentation.addTransformer(interceptingClassTransformer);
    }
    
}
