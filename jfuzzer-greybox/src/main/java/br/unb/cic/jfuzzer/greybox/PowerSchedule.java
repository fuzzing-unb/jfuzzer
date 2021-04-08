package br.unb.cic.jfuzzer.greybox;

import java.util.HashMap;
import java.util.stream.Collectors;

import br.unb.cic.jfuzzer.api.RunnerResult;

import java.util.Map;
import java.util.Collections;
import java.util.List;

public class PowerSchedule {

    public HashMap<String, Float> getBestSeeders(List<RunnerResult<String>> outcomes) {

        HashMap<String, Float> seedsCoverage = new HashMap<String, Float>();

        for (RunnerResult<String> out : outcomes) {
            seedsCoverage.put(out.getInput(), out.getCoverage());
        }

        Float maxCoverage = (Collections.max(seedsCoverage.values()));
        Map<String, Float> bestSeed = seedsCoverage.entrySet().stream()
                .filter(map -> maxCoverage.equals(map.getValue()))
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
        return (HashMap<String, Float>) bestSeed;

    }

}
