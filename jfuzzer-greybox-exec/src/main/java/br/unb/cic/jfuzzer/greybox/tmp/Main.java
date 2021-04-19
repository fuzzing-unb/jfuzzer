package br.unb.cic.jfuzzer.greybox.tmp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import org.apache.commons.lang3.math.NumberUtils;

import br.unb.cic.jfuzzer.FuzzerConfig;
import br.unb.cic.jfuzzer.api.RunnerResult;
import br.unb.cic.jfuzzer.greybox.GreyBoxFuzzer;
import br.unb.cic.jfuzzer.greybox.PowerSchedule;
import br.unb.cic.jfuzzer.util.observer.JFuzzerObservable;

public class Main {

    public static void main(String[] args) {

        System.out.println("Beginning execution ...");
        
        CommonsCodecRunner runner = new CommonsCodecRunner();
        List<String> seeds = new ArrayList<>();
        seeds.add("testandootestedostestesparatestar");
        seeds.add("luis");
        seeds.add("walter");
        seeds.add("pedro");
        seeds.add("23547982345@#%#$%*zz$$_+{??//}|||{?");
        seeds.add("zé");
        seeds.add("luis");
        PowerSchedule schedule = new PowerSchedule();
        int trials = seeds.size()*seeds.size();
        GreyBoxFuzzer fuzzer = new GreyBoxFuzzer(seeds, schedule, 0);
        JFuzzerObservable.addObserver(fuzzer);
        System.out.println("\n ********* RESULTS **********");
        long startTime = System.nanoTime();
        List<RunnerResult<String>> results = fuzzer.run(runner, trials);
        long endTime = System.nanoTime();
        System.out.println(results);
        System.out.println((endTime - startTime)/1000000);
        
    }
}
