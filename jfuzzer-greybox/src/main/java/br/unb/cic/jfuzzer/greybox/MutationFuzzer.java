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

    private List<String> seeds;
    private Mutator mutator;
    private PowerSchedule schedule;
    private List<String> inputs;

    public MutationFuzzer(List<String> seeds, Mutator mutator, PowerSchedule schedule) {
        this.seeds = seeds;
        this.mutator = mutator;
        this.schedule = schedule;
        this.inputs = new ArrayList<String>();
    }

    @SuppressWarnings("unchecked")
    public List<Seed<String>> reset(List<String> seeds){
        List<Seed<String>> population = new ArrayList<>();
        for (String s: seeds) {
            Seed<String> seed = new Seed(s);
            population.add(seed);
        }
        System.out.println(population);
        return population;
    }

    @SuppressWarnings("unchecked")
    public String createCandidate(){
        PowerSchedule ps = new PowerSchedule();
        Seed<String> seed = ps.choose(reset(seeds));
        String candidate = seed.getData();
        System.out.println(candidate);
        int trials = Math.min(candidate.length(), getRandomNumber(1, 5));
        for (int i = 0; i < trials; i++) {
            candidate = (String) mutator.mutate(candidate);
        }
        System.out.println(candidate);
        return candidate;
    }

    
    @Override
    public String fuzz() {
        int seed_index = 0;
        String input = null;
        if(seed_index < seeds.size()){
            input = seeds.get(seed_index);
            seed_index += 1;
        }else{
            input = createCandidate();
        }
        inputs.add(input);
        return input;
    }

    @Override
    public RunnerResult run(Runner runner) {
        return null;
    }

    @Override
    public List run(Runner runner, int trials) {
        return null;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
