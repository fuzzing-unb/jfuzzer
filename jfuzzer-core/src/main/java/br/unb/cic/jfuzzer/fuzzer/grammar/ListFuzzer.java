package br.unb.cic.jfuzzer.fuzzer.grammar;

import java.util.List;
import java.util.Random;

import br.unb.cic.jfuzzer.FuzzerConfig;

@Deprecated
public class ListFuzzer {

    private List list;
    private Random random;

    public ListFuzzer() {
        this.random = FuzzerConfig.getDefaultRandom();
    }

    public <T> ListFuzzer setList(List<T> list) {
        this.list = list;
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T fuzz() {
        int idx = random.nextInt(list.size());
        return (T) list.get(idx);
    }

}
