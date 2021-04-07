package br.unb.cic.jfuzzer.beans.polymorphism;

import lombok.Data;

@Data
public class Square extends AbstractPolygon {
    private Double side;

    @Override
    public Double area() {
        return side * side;
    }

    @Override
    public String toString() {
        return String.format("Square [id=%s, side=%s]", id, side);
    }
}
