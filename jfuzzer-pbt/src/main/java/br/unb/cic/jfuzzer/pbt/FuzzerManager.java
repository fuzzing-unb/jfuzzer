package br.unb.cic.jfuzzer.pbt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import br.unb.cic.jfuzzer.api.Fuzzer;
import br.unb.cic.jfuzzer.fuzzer.DateFuzzer;
import br.unb.cic.jfuzzer.fuzzer.NumberFuzzer;
import br.unb.cic.jfuzzer.fuzzer.StringFuzzer;

public class FuzzerManager {

    private Map<Class, Fuzzer> cache = new HashMap<>();
    private PbtConfig config;

    public FuzzerManager(PbtConfig config) {
        this.config = config;
        init();
    }

    private void init() {
        cache.put(String.class, new StringFuzzer(config.getStringLengthRange(), config.getRandom().getRandom()));
        cache.put(Integer.class, new NumberFuzzer<>(config.getIntegerLengthRange(), config.getRandom().getRandom()));
        cache.put(Float.class, new NumberFuzzer<>(config.getFloatLengthRange(), config.getRandom().getRandom()));
        cache.put(Double.class, new NumberFuzzer<>(config.getDoubleLengthRange(), config.getRandom().getRandom()));
        cache.put(Long.class, new NumberFuzzer<>(config.getLongLengthRange(), config.getRandom().getRandom()));
        cache.put(Date.class, new DateFuzzer(config.getRandom().getRandom()));
    }

    public <T> void register(Class<T> clazz, Fuzzer<T> generator) {
        cache.put(Objects.requireNonNull(clazz), Objects.requireNonNull(generator));
    }

    @SuppressWarnings("unchecked")
    public <T> Fuzzer<T> get(Class<T> clazz) {
        return cache.get(clazz);
    }

}
