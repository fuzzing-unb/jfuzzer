package br.unb.cic.jfuzzer.greybox.tmp;

import java.util.ArrayList;
import java.util.List;

import br.unb.cic.jfuzzer.api.RunnerResult;

public class CoverageAnalysis {

    public Seed<String> maxCoverage(List<RunnerResult<String>> results) {
        double max = 0.0f;
        RunnerResult<String> bestSeed = null;
        for (RunnerResult<String> inp : results) {
            if (inp.getCoverage() > max) {
                max = inp.getCoverage();
                bestSeed = inp;
            }
        }
        Seed<String> newSeed = new Seed<String>(bestSeed.getInput());
        newSeed.setCoverageValue(bestSeed.getCoverage());
        return newSeed;
    }

    public List<Seed<String>> bestSeeds(List<RunnerResult<String>> results) {
        double max = 0.0f;
        List<Seed<String>> bestSeeds = new ArrayList<>();
        for (RunnerResult<String> inp : results) {
            if (inp.getCoverage() > max) {
                max = inp.getCoverage();
            }
        }
        for (RunnerResult<String> inp : results) {
            if (inp.getCoverage() == max) {
                Seed<String> s = new Seed<String>(inp.getInput());
                bestSeeds.add(s);
            }
        }
        return bestSeeds;
    }

}
