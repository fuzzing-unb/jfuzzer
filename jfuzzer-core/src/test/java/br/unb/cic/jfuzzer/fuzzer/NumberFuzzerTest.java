package br.unb.cic.jfuzzer.fuzzer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import br.unb.cic.jfuzzer.FuzzerConfig;
import br.unb.cic.jfuzzer.fuzzer.NumberFuzzer;
import br.unb.cic.jfuzzer.random.ClassicRandomStrategy;
import br.unb.cic.jfuzzer.util.Range;

class NumberFuzzerTest {

    private static Random random;
    private static final int MIN = 10;
    private static final int MAX = 50;
    private static final Range<Integer> RANGE = new Range<Integer>(MIN, MAX);
    private static final Range<Double> RANGE1 = new Range<Double>(10.0, 50.0);
    private static final Range<Float> RANGE2 = new Range<Float>(10.1F, 50.0F);
    private static final Range<Long> RANGE3 = new Range<Long>(10L, 50L);

    @BeforeAll
    public static void init() {
        random = new ClassicRandomStrategy(FuzzerConfig.DEFAULT_SEED).getRandom();
    }

    @Test
    void testInteger() {
        NumberFuzzer<Integer> generator = new NumberFuzzer<Integer>(RANGE, random);

        Integer num = generator.fuzz();
        assertThat(num).isNotNull();
        assertThat(num).isInstanceOf(Integer.class);
        assertThat(num).isGreaterThanOrEqualTo(MIN);
        assertThat(num).isLessThanOrEqualTo(MAX);
    }

    @Test
    void testLong() {
        NumberFuzzer<Long> generator = new NumberFuzzer<Long>(RANGE3, random);

        Long num = generator.fuzz();
        assertThat(num).isNotNull();
        assertThat(num).isInstanceOf(Long.class);
        assertThat(num).isGreaterThanOrEqualTo(MIN);
        assertThat(num).isLessThanOrEqualTo(MAX);
    }

    @Test
    void testDouble() {
        NumberFuzzer<Double> generator = new NumberFuzzer<Double>(RANGE1, random);

        Double num = generator.fuzz();
        assertThat(num).isNotNull();
        assertThat(num).isInstanceOf(Double.class);
        assertThat(num).isGreaterThanOrEqualTo(MIN);
        assertThat(num).isLessThanOrEqualTo(MAX);
    }

    @Test
    void testFloat() {
        NumberFuzzer<Float> generator = new NumberFuzzer<Float>(RANGE2, random);

        Float num = generator.fuzz();
        assertThat(num).isNotNull();
        assertThat(num).isInstanceOf(Float.class);
        assertThat(num).isGreaterThanOrEqualTo(MIN);
        assertThat(num).isLessThanOrEqualTo(MAX);
    }

}
