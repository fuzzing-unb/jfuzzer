package br.unb.cic.jfuzzer.api;

import java.util.List;

public interface Fuzzer<T> {

    T fuzz();
    
    RunnerResult<T> run(Runner runner);
    
    List<RunnerResult<T>> run(Runner runner, int trials);
    
}
