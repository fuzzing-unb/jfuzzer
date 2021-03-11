package br.unb.cic.jfuzzer.fuzzer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import br.unb.cic.jfuzzer.FuzzerConfig;
import br.unb.cic.jfuzzer.fuzzer.StringFuzzer;
import br.unb.cic.jfuzzer.random.ClassicRandomStrategy;
import br.unb.cic.jfuzzer.util.Range;

class StringFuzzerTest {

    private static Random random;

    @BeforeAll
    public static void init() {
        random = new ClassicRandomStrategy(FuzzerConfig.DEFAULT_SEED).getRandom();
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 5, 8, 10, 49 })
    void testStringGeneratorComMinimo(int min) {
        System.err.println("novo::: " + min);
        int max = 50;
        StringFuzzer generator = new StringFuzzer(new Range<Integer>(min, max), random);

        for (int i = 0; i < 30; i++) {
            String str = generator.fuzz();
            System.out.println(">> " + str + " == " + str.length());

            assertThat(str).hasSizeGreaterThanOrEqualTo(min);
            assertThat(str).hasSizeLessThanOrEqualTo(max);
        }
    }

    @Test
    void testContrutorRangeNulo() {
        assertThrows(NullPointerException.class, () -> {
            new StringFuzzer(null, random);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, -1, -10 })
    void testContrutorRangeMinimoInvalido(int min) {
        Range<Integer> range = new Range<Integer>(min, 50);
        assertThrows(IllegalArgumentException.class, () -> {
            new StringFuzzer(range, random);
        });
    }

    @Test
    void testContrutorRangeMinMaiorQueMax() {
        Range<Integer> range = new Range<Integer>(11, 10);
        assertThrows(IllegalArgumentException.class, () -> {
            new StringFuzzer(range, random);
        });
    }

    @Test
    void testContrutorSymbolsNulo() {
        Range<Integer> range = new Range<Integer>(10, 50);
        assertThrows(NullPointerException.class, () -> {
            new StringFuzzer(range, random, null);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "a" })
    void testContrutorSymbolsMinimoInvalido(String symbols) {
        Range<Integer> range = new Range<Integer>(10, 50);
        assertThrows(IllegalArgumentException.class, () -> {
            new StringFuzzer(range, random, symbols);
        });
    }

}
