package br.unb.cic.jfuzzer.runner;

import br.unb.cic.jfuzzer.api.Runner;
import br.unb.cic.jfuzzer.api.RunnerResult;
import br.unb.cic.jfuzzer.api.RunnerStatus;

public class PrintRunner implements Runner {
    public Float coverage = 0.0f;

    @Override
    public <T> RunnerResult<T> run(T input) {
        System.err.println(input);
        return new RunnerResult<>(input, RunnerStatus.UNRESOLVED, coverage);
    }

}
