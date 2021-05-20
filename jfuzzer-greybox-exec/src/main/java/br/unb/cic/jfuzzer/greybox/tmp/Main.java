package br.unb.cic.jfuzzer.greybox.tmp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import org.apache.commons.lang3.math.NumberUtils;

import br.unb.cic.jfuzzer.FuzzerConfig;
import br.unb.cic.jfuzzer.api.RunnerResult;
import br.unb.cic.jfuzzer.greybox.BlackBoxFuzzer;
import br.unb.cic.jfuzzer.greybox.GreyBoxFuzzer;
import br.unb.cic.jfuzzer.greybox.PowerSchedule;
import br.unb.cic.jfuzzer.util.observer.JFuzzerObservable;

public class Main {

    public static void main(String[] args) {

        System.out.println("Beginning execution ...");

        CommonsCodecRunner runner = new CommonsCodecRunner();
        PowerSchedule schedule = new PowerSchedule();
        List<String> seeds = new ArrayList<>();
        seeds.add("Ayalaf40h3#&*F&*(NG(&N");
        seeds.add("Luis");
        seeds.add("Walter");
        seeds.add("Good");
        seeds.add("Rodrigo");
        int trials = 1000;
        int newTrials = 10;

        RunFuzzer rf = new RunFuzzer();
        List<RunnerResult<String>> results = rf.improveCoverageGB(seeds, schedule, runner, trials, newTrials);
       
        System.out.println(results);

        // System.out.println("\n ********* RESULTS **********");
        // long startTime = System.nanoTime();
       
        // long endTime = System.nanoTime();
        // System.out.println((endTime - startTime)/1000000);

        // System.out.println(seeds);
    }
}
