package br.unb.cic.jfuzzer.api;

public interface Mutator<T> {

    T mutate(T input);

}
