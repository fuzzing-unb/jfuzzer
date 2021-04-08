package br.unb.cic.jfuzzer.greybox;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;

import br.unb.cic.jfuzzer.api.RunnerResult;
import br.unb.cic.jfuzzer.fuzzer.mutator.StringMutatorFuzzer;
import br.unb.cic.jfuzzer.util.coverage.CoverageSummary;
import br.unb.cic.jfuzzer.util.observer.Event;

import java.util.Map;
import java.util.Collections;
import java.util.List;

public class PowerSchedule {

    private List<Event> events = new LinkedList<>();

    public HashMap<String, Float> getBestSeeders(List<RunnerResult<String>> outcomes) {

        HashMap<String, Float> seedsCoverage = new HashMap<String, Float>();

        for (RunnerResult<String> out : outcomes) {
            seedsCoverage.put(out.getInput(), out.getCoverage());
        }

        Float maxCoverage = (Collections.max(seedsCoverage.values()));
        Map<String, Float> bestSeed = seedsCoverage.entrySet().stream()
                .filter(map -> maxCoverage.equals(map.getValue()))
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));


        Map<String, Float> bestSeedMutated = mutatedPopulationOfSeeds(bestSeed, 50.0f);
        return (HashMap<String, Float>) bestSeedMutated;

    }

    public Map<String, Float> mutatedPopulationOfSeeds (Map<String, Float> hm, Float  goal){
        
        List<String> inputs = hm.keySet().stream()
        .collect(Collectors.toList());

        Map<String, Float> population = new HashMap<String, Float>();

        for (String input : inputs) {
            events = new LinkedList<>();
            StringMutatorFuzzer smf = new StringMutatorFuzzer(input);
            String result = smf.fuzz();
            CoverageSummary summary = new CoverageSummary(events);
            Float lineCoverage = summary.getLineCoveragePercentage();
            population.put(result, lineCoverage);
        }

        if(averagePopulationMuted(population) < goal){
            mutatedPopulationOfSeeds(population, goal);
        }
        return population;
    }


    public Float averagePopulationMuted(Map<String, Float> hm){
        Float result = 0.0f;
        List<Float> coverages = hm.values().stream()
        .collect(Collectors.toList());
        for (Float cover : coverages) {
            result += cover;
        }
        return result/coverages.size();
    }

}
