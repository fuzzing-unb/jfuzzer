package br.unb.cic.jfuzzer.greybox.tmp;

import lombok.Data;

@Data
public class Seed<T> {
    private T data;
    private double energy;
    private Coverage coverage;
}
