package br.unb.cic.jfuzzer.fuzzer.grammar;

import java.util.List;
import java.util.Random;

import br.unb.cic.jfuzzer.FuzzerConfig;
import br.unb.cic.jfuzzer.fuzzer.NumberFuzzer;
import br.unb.cic.jfuzzer.util.Range;

@Deprecated
public class ListFuzzer {

    private List list;
    private Random random;

    public ListFuzzer() {
        this.random = FuzzerConfig.getDefaultRandom();
//        random = new Random();
    }

    public <T> ListFuzzer setList(List<T> list) {
        this.list = list;
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T fuzz() {
        int idx = new NumberFuzzer<Integer>(new Range<>(0,list.size()-1)).fuzz();
//        int idx = random.nextInt(list.size());
        return (T) list.get(idx);
    }

}
