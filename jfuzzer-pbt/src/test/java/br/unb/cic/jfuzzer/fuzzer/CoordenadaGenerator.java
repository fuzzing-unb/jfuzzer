package br.unb.cic.jfuzzer.fuzzer;

import java.util.Random;

import br.unb.cic.jfuzzer.api.AbstractFuzzer;
import br.unb.cic.jfuzzer.beans.suas.Coordenada;
import br.unb.cic.jfuzzer.util.Range;

public class CoordenadaGenerator extends AbstractFuzzer<Coordenada> {
    private NumberFuzzer<Float> latitudeFuzzer;
    private NumberFuzzer<Float> longitudeFuzzer;

    public CoordenadaGenerator(Random random) {
        latitudeFuzzer = new NumberFuzzer<Float>(new Range<>(-90f, 90f), random);
        longitudeFuzzer = new NumberFuzzer<Float>(new Range<>(-180f, 180f), random);
    }

    @Override
    public Coordenada fuzz() {
        String latitude = latitudeFuzzer.fuzz().toString();
        String longitude = longitudeFuzzer.fuzz().toString();
        return new Coordenada(latitude, longitude);
    }

}
