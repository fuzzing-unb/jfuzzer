package br.unb.cic.jfuzzer.fuzzer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import br.unb.cic.jfuzzer.FuzzerConfig;
import br.unb.cic.jfuzzer.fuzzer.DateFuzzer;
import br.unb.cic.jfuzzer.random.ClassicRandomStrategy;
import br.unb.cic.jfuzzer.util.Range;

class DateFuzzerTest {
    private static Random random;
    public static final Range<Date> DEFAULT_DATE_RANGE = new Range<>(
            new GregorianCalendar(1980, 0, 1).getTime(),
            new GregorianCalendar(2020, 11, 31).getTime());

    @BeforeAll
    public static void init() {
        random = new ClassicRandomStrategy(FuzzerConfig.DEFAULT_SEED).getRandom();
    }

    @Test
    void testDate() {
        DateFuzzer generator = new DateFuzzer(random, DEFAULT_DATE_RANGE);

        for (int i = 0; i < 10000; i++) {
            Date generatedDate = generator.fuzz();

//            System.out.println(generatedDate);

            assertThat(generatedDate).isNotNull();
            assertThat(generatedDate).isAfterOrEqualTo(DEFAULT_DATE_RANGE.getMin());
            assertThat(generatedDate).isBeforeOrEqualTo(DEFAULT_DATE_RANGE.getMax());
        }
    }

}
