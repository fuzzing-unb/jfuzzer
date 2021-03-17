package br.unb.cic.jfuzzer.greybox.tmp;

import java.util.List;

import br.unb.cic.jfuzzer.FuzzerConfig;
import br.unb.cic.jfuzzer.api.RunnerResult;
import br.unb.cic.jfuzzer.fuzzer.StringFuzzer;
import br.unb.cic.jfuzzer.util.Range;
import br.unb.cic.jfuzzer.util.observer.JFuzzerObservable;

public class Main {
    public static void main(String[] args) {
        System.out.println("Beginning execution ...");        
        
        StringFuzzer fuzzer = new StringFuzzer(new Range<>(30, 50), FuzzerConfig.getDefaultRandom());

        CommonsCodecRunner runner = new CommonsCodecRunner();
        
        JFuzzerObservable.addObserver(new InstrumenterClient());
        JFuzzerObservable.addObserver(runner);
        
        List<RunnerResult<String>> results = fuzzer.run(runner, 10);

        System.out.println("\n ********* RESULTS **********");
        results.forEach(System.out::println);
    }
}
