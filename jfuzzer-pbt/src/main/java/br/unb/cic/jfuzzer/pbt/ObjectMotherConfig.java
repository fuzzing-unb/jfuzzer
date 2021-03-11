package br.unb.cic.jfuzzer.pbt;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import br.unb.cic.jfuzzer.api.Randomizer;
import br.unb.cic.jfuzzer.random.ClassicRandomStrategy;
import br.unb.cic.jfuzzer.util.Range;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ObjectMotherConfig {
    public static final Locale LOCALE = new Locale("pt", "BR");

    public static final long DEFAULT_SEED = 123L;
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    public static final Range<Integer> DEFAULT_COLLECTION_SIZE_RANGE = new Range<>(1, 100);
    public static final int DEFAULT_RANDOMIZATION_DEPTH = 5;
    public static final Range<Integer> DEFAULT_STRING_LENGTH_RANGE = new Range<>(1, 32);

    private long seed;
    private int randomizationDepth;
    private Charset charset;
    private Range<Integer> collectionSizeRange;
    private Range<Integer> stringLengthRange;
    private Range<Integer> integerLengthRange;
    private Range<Float> floatLengthRange;
    private Range<Double> doubleLengthRange;
    private Range<Long> longLengthRange;

    private Randomizer random;

    public ObjectMotherConfig() {
        seed = DEFAULT_SEED;
        charset = DEFAULT_CHARSET;
        randomizationDepth = DEFAULT_RANDOMIZATION_DEPTH;
        collectionSizeRange = DEFAULT_COLLECTION_SIZE_RANGE;
        stringLengthRange = DEFAULT_STRING_LENGTH_RANGE;
        integerLengthRange = new Range<>(1, 1000);
        floatLengthRange = new Range<>(1.0f, 1000.0f);
        doubleLengthRange = new Range<>(1.0, 1000.0);
        longLengthRange = new Range<>(1L, 1000L);
        random = new ClassicRandomStrategy(seed);
    }

}
