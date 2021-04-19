package br.unb.cic.jfuzzer.greybox.tmp;

import java.util.Map;

import br.unb.cic.jfuzzer.util.coverage.Coverage;

public class Seed<T> {
    
    private T data;
    private double energy;
    private Coverage coverage;

    public Seed(T data){
        this.data = data;
    }

    public Coverage getCoverage() {
        return coverage;
    }

    public void setCoverage(Coverage coverage) {
        this.coverage = coverage;
    }

    public T getData(){
        return data;
    }

    public void setData(T data){
        this.data = data;
    }

    public double getEnergy(){
        return energy;
    }

    public void setEnergy(double energy){
        this.energy = energy;
    }
}
