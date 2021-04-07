package br.unb.cic.jfuzzer.pbt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unb.cic.jfuzzer.beans.suas.Coordenada;
import br.unb.cic.jfuzzer.beans.suas.Equipamento;
import br.unb.cic.jfuzzer.fuzzer.CoordenadaGenerator;
import br.unb.cic.jfuzzer.util.Range;

class SuasTest {
    private PbtMain generator;

    private static final Range<Integer> RANGE_STRING = new Range<Integer>(10, 30);
    private static final Range<Integer> RANGE_INT = new Range<Integer>(10, 500);
    private static final Range<Integer> RANGE_COLLECTION = new Range<Integer>(0, 5);

    @BeforeEach
    public void before() {
        PbtConfig config = new PbtConfig();
        config.setStringLengthRange(RANGE_STRING);
        config.setIntegerLengthRange(RANGE_INT);
        config.setCollectionSizeRange(RANGE_COLLECTION);
        generator = new PbtMain(config);
        generator.register(Coordenada.class, new CoordenadaGenerator(generator.getRandom()));
    }

    @Test
    void testEquipamentos() {
        for (int i = 0; i < 5; i++) {
            Equipamento equipamento = generator.next(Equipamento.class);

            System.err.println(equipamento + "\n");
        }
    }

}
