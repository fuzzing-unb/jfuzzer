package br.unb.cic.jfuzzer.beans.polymorphism;

import lombok.Data;

@Data
public class Circle extends AbstractPolygon {
    private Double raio;

    @Override
    public Double area() {
        return 2 * Math.PI * raio;
    }

    @Override
    public String toString() {
        return String.format("Circle [id=%s, raio=%s]", id, raio);
    }
}
