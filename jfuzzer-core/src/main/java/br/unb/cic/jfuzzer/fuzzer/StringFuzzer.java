package br.unb.cic.jfuzzer.fuzzer;

import java.util.Objects;
import java.util.Random;

import br.unb.cic.jfuzzer.FuzzerConfig;
import br.unb.cic.jfuzzer.api.AbstractFuzzer;
import br.unb.cic.jfuzzer.random.ClassicRandomStrategy;
import br.unb.cic.jfuzzer.util.Range;

public class StringFuzzer extends AbstractFuzzer<String> {

    public static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    public static final String DIGITS = "0123456789";
    public static final String ALPHABET = UPPER + LOWER;
    public static final String ALPHANUMERIC = ALPHABET + DIGITS;

    private final Random random;
    private final Range<Integer> range;
    private final char[] symbols;

    public StringFuzzer(Range<Integer> range) {
        this(range, FuzzerConfig.getDefaultRandom());
    }

    public StringFuzzer(Range<Integer> range, Random random) {
        this(range, random, ALPHABET);
    }

    public StringFuzzer(Range<Integer> range, Random random, String symbols) {
        this.random = Objects.requireNonNull(random);

        this.range = Objects.requireNonNull(range);
        if (range.getMin() < 1)
            throw new IllegalArgumentException();
        if (range.getMin() > range.getMax()) {
            throw new IllegalArgumentException("'max' deve ser maior que 'min'");
        }

        this.symbols = Objects.requireNonNull(symbols).toCharArray();
        if (symbols.length() < 2)
            throw new IllegalArgumentException();
    }

    @Override
    public String fuzz() {
        Integer qtde = new NumberFuzzer<>(range, random).fuzz();
        char[] buf = new char[qtde];
        for (int idx = 0; idx < qtde; idx++) {
            buf[idx] = symbols[random.nextInt(symbols.length)];
        }
        return new String(buf);
    }

}
