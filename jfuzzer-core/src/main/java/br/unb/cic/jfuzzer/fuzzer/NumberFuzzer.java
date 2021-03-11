package br.unb.cic.jfuzzer.fuzzer;

import java.util.Random;

import br.unb.cic.jfuzzer.FuzzerConfig;
import br.unb.cic.jfuzzer.api.AbstractFuzzer;
import br.unb.cic.jfuzzer.util.Range;

public class NumberFuzzer<T extends Number> extends AbstractFuzzer<T> {
    private final Random random;
    private final Range<T> range;

    public NumberFuzzer(Range<T> range) {
        this(range, FuzzerConfig.getDefaultRandom());
    }

    public NumberFuzzer(Range<T> range, Random random) {
        this.range = range;
        this.random = random;
    }

    @Override
    public T fuzz() {
        return next(range);
    }

    public T next(Range<T> range) {
        return checkType(range.getMin(), range.getMax());
    }

    public T checkType(T min, T max) {
        if (min instanceof Double && max instanceof Double)
            return next((double) min, (double) max);
        else if (min instanceof Float && max instanceof Float)
            return next((float) min, (float) max);
        else if (min instanceof Long && max instanceof Long)
            return next((long) min, (long) max);
        else
            return next((int) min, (int) max);
    }

    @SuppressWarnings("unchecked")
    public T next(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("'max' deve ser maior que 'min'");
        }
        return (T) Integer.valueOf(random.ints(min, max).findFirst().getAsInt());
    }

    @SuppressWarnings("unchecked")
    public T next(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("'max' deve ser maior que 'min'");
        }
        return (T) Double.valueOf(random.doubles(min, max).findFirst().getAsDouble());
    }

    @SuppressWarnings("unchecked")
    public T next(float min, float max) {
        if (min > max) {
            throw new IllegalArgumentException("'max' deve ser maior que 'min'");
        }
        return (T) Float.valueOf((float) random.doubles(min, max).findFirst().getAsDouble());
    }

    @SuppressWarnings("unchecked")
    public T next(long min, long max) {
        if (min > max) {
            throw new IllegalArgumentException("'max' deve ser maior que 'min'");
        }
        return (T) Long.valueOf(random.longs(min, max).findFirst().getAsLong());
    }

}
