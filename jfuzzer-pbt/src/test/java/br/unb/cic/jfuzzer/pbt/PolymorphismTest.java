package br.unb.cic.jfuzzer.pbt;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unb.cic.jfuzzer.beans.polymorphism.Circle;
import br.unb.cic.jfuzzer.beans.polymorphism.Polygon;
import br.unb.cic.jfuzzer.beans.polymorphism.Square;
import br.unb.cic.jfuzzer.beans.polymorphism.Triangle;
import br.unb.cic.jfuzzer.fuzzer.NumberFuzzer;
import br.unb.cic.jfuzzer.util.Range;

class PolymorphismTest {
    private static final List<Class<? extends Polygon>> classes = List.of(Square.class, Circle.class, Triangle.class);
    private static NumberFuzzer<Integer> numberFuzzer;

    private PbtMain generator;

    @BeforeAll
    public static void beforeAll() {
        numberFuzzer = new NumberFuzzer<Integer>(new Range<>(0, classes.size() - 1));
    }

    @BeforeEach
    public void before() {
        generator = new PbtMain();
    }

    @Test
    void testPolygons() {
        for (int i = 0; i < 30; i++) {
            Integer idx = numberFuzzer.fuzz();
            Polygon polygon = generator.next(classes.get(idx));

            System.err.println(polygon);
        }
    }

}
