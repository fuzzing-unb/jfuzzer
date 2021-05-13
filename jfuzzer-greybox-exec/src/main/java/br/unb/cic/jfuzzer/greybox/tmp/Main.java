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
        List<String> seeds2 = new ArrayList<>();
        seeds.add("Rodrigo");
        seeds.add("Pedro");
        seeds.add("walter");
        seeds.add("good");
        PowerSchedule schedule = new PowerSchedule();
        int trials = 100000;
        GreyBoxFuzzer fuzzer = new GreyBoxFuzzer(seeds, schedule, 0);
        JFuzzerObservable.addObserver(fuzzer);
        // System.out.println("\n ********* RESULTS **********");
        // long startTime = System.nanoTime();
        List<RunnerResult<String>> results = fuzzer.run(runner, trials);
        CoverageAnalysis ca = new CoverageAnalysis();
        // System.out.println((ca.maxCoverage(results).getCoverageValue()));
        double max = ca.maxCoverage(results).getCoverageValue();
        seeds.add(ca.maxCoverage(results).getData());
        List<Seed<String>> bestSeeds = ca.bestSeeds(results);
        for (Seed<String> s : bestSeeds) {
            seeds2.add(s.getData());
        }
        // long endTime = System.nanoTime();
        // System.out.println((endTime - startTime)/1000000);
        System.out.println(max);
        // System.out.println(seeds);
        System.out.println(seeds2.size());
    }
}
