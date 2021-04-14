package br.unb.cic.jfuzzer.greybox;

import java.util.ArrayList;
import java.util.List;

import br.unb.cic.jfuzzer.FuzzerConfig;
import br.unb.cic.jfuzzer.api.Fuzzer;
import br.unb.cic.jfuzzer.api.Mutator;
import br.unb.cic.jfuzzer.api.Runner;
import br.unb.cic.jfuzzer.api.RunnerResult;
import br.unb.cic.jfuzzer.fuzzer.mutator.StringMutatorFuzzer;
import br.unb.cic.jfuzzer.greybox.tmp.Seed;


public class StringMutationFuzzer implements Fuzzer<String>{

    private List<String> seeds;
    private PowerSchedule schedule;
    private List<String> inputs;
    private int index;

    public StringMutationFuzzer(List<String> seeds, PowerSchedule schedule, int index) {
        this.seeds = seeds;
        this.schedule = schedule;
        this.inputs = new ArrayList<String>();
        this.index = index;
    }

    public List<Seed<String>> reset(List<String> seeds){
        List<Seed<String>> population = new ArrayList<>();
        for (String s: seeds) {
            Seed<String> seed = new Seed<>(s);
            population.add(seed);
        }
        return population;
    }

    public String createCandidate(){
        List<Seed<String>> population = reset(seeds);
        Seed<String> seed = schedule.choose(population);
        String candidate = seed.getData();
        int trials = Math.min(candidate.length(), getRandomNumber(1, 5));
        StringMutatorFuzzer smf = new StringMutatorFuzzer(candidate, 1, trials,  FuzzerConfig.getDefaultRandom());
        for (int i = 0; i < trials; i++) {
            candidate = smf.fuzz();
        }
        return candidate;
    }
    
    @Override
    public String fuzz() {
        String input = null;
        if(index < seeds.size()){
            input = seeds.get(index);
            setIndex(index+1);
        }else{
            input = createCandidate();
        }
        inputs.add(input);
        return input;
    }

    @Override
    public RunnerResult<String> run(Runner runner) {
        return null;
    }

    @Override
    public List<RunnerResult<String>> run(Runner runner, int trials) {
        return null;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public int getIndex(){
        return index;
    }

    public void setIndex(int index){
        this.index = index;
    }
}
