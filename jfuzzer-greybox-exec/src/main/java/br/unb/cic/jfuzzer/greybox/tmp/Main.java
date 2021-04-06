package br.unb.cic.jfuzzer.greybox.tmp;

import java.util.List;

import br.unb.cic.jfuzzer.FuzzerConfig;
import br.unb.cic.jfuzzer.api.RunnerResult;
import br.unb.cic.jfuzzer.greybox.GreyBoxFuzzer;
import br.unb.cic.jfuzzer.util.Range;
import br.unb.cic.jfuzzer.util.observer.JFuzzerObservable;

public class Main {

    public static void main(String[] args) {
        System.out.println("Beginning execution ...");

        GreyBoxFuzzer fuzzer = new GreyBoxFuzzer(new Range<>(30, 50), FuzzerConfig.getDefaultRandom(), alphabet());

        CommonsCodecRunner runner = new CommonsCodecRunner();

        JFuzzerObservable.addObserver(fuzzer);

        List<RunnerResult<String>> results = fuzzer.run(runner, 1);

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
