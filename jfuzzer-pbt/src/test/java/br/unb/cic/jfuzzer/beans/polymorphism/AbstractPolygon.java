package br.unb.cic.jfuzzer.beans.polymorphism;

import lombok.Data;

@Data
public abstract class AbstractPolygon implements Polygon {
    protected String id;
}
