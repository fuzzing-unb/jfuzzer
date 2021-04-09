package br.unb.cic.jfuzzer.greybox.tmp;

import lombok.Data;

@Data
public class Seed<T> {
    private T data;
    private double energy;
    private Coverage coverage;

    public Seed(T data, Coverage coverage){
        this.data = data;
        this.coverage = coverage;
        this.energy = 1.0;
    }

}
