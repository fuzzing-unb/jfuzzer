package br.unb.cic.jfuzzer.greybox.tmp;

import java.util.Map;

public class Seed<T> {
    
    private T data;
    private double energy;
    private Map<String,Double> coverage;

    public Seed(T data){
        this.data = data;
    }

    public Map<String,Double> getCoverage() {
        return coverage;
    }

    public void setCoverage(Map<String,Double> coverage) {
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
