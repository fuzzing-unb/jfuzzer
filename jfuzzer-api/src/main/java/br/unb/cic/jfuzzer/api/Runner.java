package br.unb.cic.jfuzzer.api;

public interface Runner {

    public <T> RunnerResult<T> run(T input);
    
}
