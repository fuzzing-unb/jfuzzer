package br.unb.cic.jfuzzer.greybox;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import br.unb.cic.jfuzzer.api.Fuzzer;
import br.unb.cic.jfuzzer.api.Mutator;
import br.unb.cic.jfuzzer.api.Runner;
import br.unb.cic.jfuzzer.api.RunnerResult;
import br.unb.cic.jfuzzer.greybox.tmp.Seed;


public class MutationFuzzer implements Fuzzer{

    private List<Seed<String>> seeds;
    private Mutator mutator;
    private PowerSchedule schedule;
    private List<String> inputs;

    public MutationFuzzer(List<Seed<String>> seeds, Mutator mutator, PowerSchedule schedule) {
        this.seeds = seeds;
        this.mutator = mutator;
        this.schedule = schedule;
        this.inputs = new ArrayList<String>();
        this.reset(seeds);
    }

    public void reset(List<Seed<String>> seeds){
        List<String> population = new ArrayList();
        for (Seed<String> s: seeds) {
            population.add(s.getData().toString());
        }

        int seed_index = 0;
        System.out.println(population);
        System.out.println(seed_index);
    }

    public String createCandidate(Seed<String> seed){
        String candidate = seed.getData();
        int trials = candidate.length();
        for (int i = 0; i < trials; i++) {
            candidate = mutator.mutate(candidate);
        }
        System.out.println(candidate);
        return candidate;
    }

    
    @Override
    public Object fuzz() {
        return null;
        // TODO
    }

    @Override
    public RunnerResult run(Runner runner) {
        return null;
    }

    @Override
    public List run(Runner runner, int trials) {
        return null;
    }
}
