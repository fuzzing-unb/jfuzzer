package br.unb.cic.jfuzzer.beans.polymorphism;

import lombok.Data;

@Data
public class Triangle extends AbstractPolygon {
    private Double base;
    private Double altura;

    @Override
    public Double area() {
        return (base * altura) / 2;
    }

    @Override
    public String toString() {
        return String.format("Triangle [id=%s, base=%s, altura=%s]", id, base, altura);
    }

}
