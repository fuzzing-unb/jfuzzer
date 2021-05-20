package br.unb.cic.jfuzzer.greybox.tmp;

import br.unb.cic.jfuzzer.api.RunnerResult;
import br.unb.cic.jfuzzer.greybox.GreyBoxFuzzer;
import br.unb.cic.jfuzzer.greybox.PowerSchedule;
import br.unb.cic.jfuzzer.util.observer.JFuzzerObservable;
import java.util.ArrayList;
import java.util.List;

public class RunFuzzer {
    
    public List<RunnerResult<String>> improveCoverageGB(List<String> seeds, PowerSchedule schedule, CommonsCodecRunner runner, int trials, int newTrials){
        
        GreyBoxFuzzer fuzzer = new GreyBoxFuzzer(seeds, schedule, 0);
        JFuzzerObservable.addObserver(fuzzer);
        List<RunnerResult<String>> results = fuzzer.run(runner, trials);
        for (int i = 0; i < newTrials; i++) {
            seeds = new ArrayList<>();
            for (RunnerResult<String> rr : results) {
                seeds.add(rr.getInput());
            }
            fuzzer = new GreyBoxFuzzer(seeds, schedule, 0);
            JFuzzerObservable.addObserver(fuzzer);
            results = fuzzer.run(runner, trials);
        }
        
        return results;
    }
}
