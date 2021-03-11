package br.unb.cic.jfuzzer.runner;

import br.unb.cic.jfuzzer.FuzzerConfig;
import br.unb.cic.jfuzzer.fuzzer.StringFuzzer;
import br.unb.cic.jfuzzer.fuzzer.mutator.StringMutatorFuzzer;
import br.unb.cic.jfuzzer.util.Range;

//classe utilitaria para realizacao de testes manuais durante o desenvolvimento
@Deprecated
public class Teste {

    public static void main(String[] args) {
        StringFuzzer fuzzer = new StringFuzzer(new Range<>(5, 20), FuzzerConfig.getDefaultRandom());
//        fuzzer.run(new PrintRunner(), 10);

//        ProgramRunner runner = new ProgramRunner(List.of("/bin/sh", "-c", "ping"));
//        List<RunnerResult<String>> results = fuzzer.run(runner, 10);
//        results.forEach(System.out::println);

        String input = fuzzer.fuzz();
//        String input = "http://www.google.com/search?q=fuzzing";
        StringMutatorFuzzer mutatorFuzzer = new StringMutatorFuzzer(input);
        System.err.println(input);
        for (int i = 0; i < 10; i++) {
            System.err.println(mutatorFuzzer.fuzz());
        }
    }

}
