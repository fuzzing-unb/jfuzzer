package br.unb.cic.jfuzzer.fuzzer;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import br.unb.cic.jfuzzer.api.AbstractFuzzer;
import br.unb.cic.jfuzzer.util.Range;

public class DateFuzzer extends AbstractFuzzer<Date> {
    public static final Range<Date> DEFAULT_DATE_RANGE = new Range<>(
            new GregorianCalendar(1980, 0, 1).getTime(),
            new GregorianCalendar(2020, 11, 31).getTime());

    private final Random random;

    private final Range<Date> range;

    private final LocalDate min;
    private final LocalDate max;

    public DateFuzzer(Random random) {
        this(random, DEFAULT_DATE_RANGE);
    }

    public DateFuzzer(Random random, Range<Date> range) {
        // TODO validar random
        this.random = random;
        // TODO validar range
        this.range = range;
        min = toLocalDate(range.getMin());
        max = toLocalDate(range.getMax());
    }

    @Override
    public Date fuzz() {
        long days = differenceInDays(min, max);
        long randomDays = nextLong(days);

        return toDate(min.plusDays(randomDays));
    }

    private long differenceInDays(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start, end);
//        Period period = Period.between(start, end);
//        return Math.abs(period.getDays());
    }

    private LocalDate toLocalDate(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private Date toDate(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    private long nextLong(long max) {
        // TODO usar um long generator
        return (long) new NumberFuzzer<Integer>(new Range<>(1, (int) max), random).fuzz();
    }
}
