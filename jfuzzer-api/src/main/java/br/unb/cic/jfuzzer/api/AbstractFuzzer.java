package br.unb.cic.jfuzzer.api;

import java.util.ArrayList;
import java.util.List;

//TODO fazer com interface default
public abstract class AbstractFuzzer<T> implements Fuzzer<T> {

    //TODO validacoes ... (EX: runner nao pode ser nulo, trials > 0, ...)
    
    @Override
    public RunnerResult<T> run(Runner runner) {
        return runner.run(fuzz());
    }

    @Override
    public List<RunnerResult<T>> run(Runner runner, int trials) {
        List<RunnerResult<T>> outcomes = new ArrayList<>(trials);
        for (int i = 0; i < trials; i++) {
            outcomes.add(runner.run(fuzz()));
        }
        return outcomes;
    }

}
