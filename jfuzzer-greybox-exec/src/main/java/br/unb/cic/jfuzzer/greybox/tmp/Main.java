package br.unb.cic.jfuzzer.greybox.tmp;

import java.util.List;

//import org.apache.commons.lang3.math.NumberUtils;

import br.unb.cic.jfuzzer.FuzzerConfig;
import br.unb.cic.jfuzzer.api.RunnerResult;
import br.unb.cic.jfuzzer.greybox.GreyBoxFuzzer;
import br.unb.cic.jfuzzer.util.Range;
import br.unb.cic.jfuzzer.util.observer.JFuzzerObservable;

public class Main {

    public static void main(String[] args) {
        System.out.println("Beginning execution ...");

//        System.err.println(NumberUtils.createLong("0x08000000000000000000"));
//        System.err.println(NumberUtils.createLong("0x080000000"));
//        System.err.println(NumberUtils.createLong("0x72321741"));        

        // commons codec
        GreyBoxFuzzer fuzzer = new GreyBoxFuzzer(new Range<>(30, 50), FuzzerConfig.getDefaultRandom(), alphabet());
      CommonsCodecRunner runner = new CommonsCodecRunner();

        //commons lang
//        GreyBoxFuzzer fuzzer = new GreyBoxFuzzer(new Range<>(5, 10), FuzzerConfig.getDefaultRandom(), "x0123456789");
//        CommonsLangNumberUtilsRunner runner = new CommonsLangNumberUtilsRunner();
//
        JFuzzerObservable.addObserver(fuzzer);

        List<RunnerResult<String>> results = fuzzer.run(runner, 5);

        System.out.println("\n ********* RESULTS **********");
        results.forEach(System.out::println);

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        CoverageUtil.printLines();
//        CoverageUtil.printMethods();
//        CoverageUtil.printBranches();
    }

    private static String alphabet() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            sb.append((char) i);
        }
        return sb.toString();
    }
}
