package br.unb.cic.jfuzzer.fuzzer.mutator;

import java.util.List;
import java.util.Random;

import br.unb.cic.jfuzzer.FuzzerConfig;
import br.unb.cic.jfuzzer.api.AbstractFuzzer;
import br.unb.cic.jfuzzer.api.Mutator;
import br.unb.cic.jfuzzer.fuzzer.NumberFuzzer;
import br.unb.cic.jfuzzer.util.Range;
import br.unb.cic.jfuzzer.fuzzer.StringFuzzer;

public class StringMutatorFuzzer extends AbstractFuzzer<String> {

    private final String input;
    private final int minMutations;
    private final int maxMutations;
    private final Random random;
    private final List<Mutator<String>> mutators;
    private final NumberFuzzer<Integer> numberFuzzer;

    public StringMutatorFuzzer(String input) {
        this(input, 1, 1, FuzzerConfig.getDefaultRandom());
    }

    public StringMutatorFuzzer(String input, Random random) {
        this(input, 1, 1, random);
    }

    public StringMutatorFuzzer(String input, int minMutations, int maxMutations, Random random) {
        this.input = input;
        this.minMutations = minMutations;
        this.maxMutations = maxMutations;
        this.random = random;
        mutators = List.of(new FlipRandomCharacterMutation(),
            new InsertRandomCharacterMutation(),
            new DeleteRandomCharacterMutation());            
        numberFuzzer = new NumberFuzzer<Integer>(new Range<>(1, mutators.size()), random);
    }

    @Override
    public String fuzz() {
        //TODO tratar o caso de mutacao sobre mutacao
        int index = numberFuzzer.fuzz() - 1;
        Mutator<String> mutation = mutators.get(index);
        
        return mutation.mutate(input);
    }

    public String fuzzMutator(int idx) {
        Mutator<String> mutation = mutators.get(idx);
        return mutation.mutate(input);
    }

    class DeleteRandomCharacterMutation implements Mutator<String> {

        @Override
        public String mutate(String input) {
            Random random = new Random();
            int index = random.nextInt(input.length());
            StringBuilder sb = new StringBuilder(input);
            sb.deleteCharAt(index);
            String resultString = sb.toString();
            return resultString;
        }

    }
    @Deprecated
    class InsertRandomCharacterMutation implements Mutator<String> {

        @Override
        public String mutate(String input) {
            StringFuzzer fuzzer = new StringFuzzer(new Range<>(1, 1), FuzzerConfig.getDefaultRandom());
            Random random = new Random();
            int index = random.nextInt(input.length());
            StringBuilder sb = new StringBuilder(input);
            sb.insert(index,fuzzer.fuzz());
            String resultString = sb.toString();
            return resultString;
        }
        

    }

    @Deprecated
    class FlipRandomCharacterMutation implements Mutator<String> {

        @Override
        public String mutate(String input) {
            StringFuzzer fuzzer = new StringFuzzer(new Range<>(1, 1), FuzzerConfig.getDefaultRandom());
            Random random = new Random();
            int index = random.nextInt(input.length());
            StringBuilder sb = new StringBuilder(input);
            sb.replace(index, index+1, fuzzer.fuzz());
            String resultString = sb.toString();
            return resultString;
        }
    }

}
