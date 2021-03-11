package br.unb.cic.jfuzzer.fuzzer.mutator;

import java.util.List;
import java.util.Random;

import br.unb.cic.jfuzzer.FuzzerConfig;
import br.unb.cic.jfuzzer.api.AbstractFuzzer;
import br.unb.cic.jfuzzer.api.Mutator;
import br.unb.cic.jfuzzer.fuzzer.NumberFuzzer;
import br.unb.cic.jfuzzer.util.Range;

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
        mutators = List.of(new DeleteRandomCharacterMutation(),
                new InsertRandomCharacterMutation(),
                new FlipRandomCharacterMutation());
        // TODO rever o numberFuzzer ... nao pega o limite superior (por isso ficou com
        // mais 1)
        numberFuzzer = new NumberFuzzer<Integer>(new Range<>(1, mutators.size() + 1), random);
    }

    @Override
    public String fuzz() {
        
        //TODO tratar o caso de mutacao sobre mutacao
        
        int index = numberFuzzer.fuzz() - 1;
        Mutator<String> mutation = mutators.get(index);
        return mutation.mutate(input);
    }

    class DeleteRandomCharacterMutation implements Mutator<String> {

        @Override
        public String mutate(String input) {
            // TODO Auto-generated method stub
            System.err.println("DeleteRandomCharacterMutation");
            return input;
        }

    }

    class InsertRandomCharacterMutation implements Mutator<String> {

        @Override
        public String mutate(String input) {
            // TODO Auto-generated method stub
            System.err.println("InsertRandomCharacterMutation");
            return input;
        }

    }

    class FlipRandomCharacterMutation implements Mutator<String> {

        @Override
        public String mutate(String input) {
            // TODO Auto-generated method stub
            System.err.println("FlipRandomCharacterMutation");
            return input;
        }

    }

}
