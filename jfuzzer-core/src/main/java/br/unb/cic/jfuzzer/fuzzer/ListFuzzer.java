package br.unb.cic.jfuzzer.fuzzer;

import java.util.List;

import br.unb.cic.jfuzzer.api.AbstractFuzzer;
import br.unb.cic.jfuzzer.util.Range;

public class ListFuzzer<T> extends AbstractFuzzer<T> {

    private List<T> list;
    private NumberFuzzer<Integer> numberFuzzer;

    public ListFuzzer(List<T> collection) {
        this.list = collection;
        numberFuzzer = new NumberFuzzer<>(new Range<>(0, collection.size()));
    }

    @Override
    public T fuzz() {
        int idx = numberFuzzer.fuzz();
        return list.get(idx);
    }

    public static <K> K fuzz(List<K> list) {
        int idx = new NumberFuzzer<Integer>(new Range<>(0, list.size() - 1)).fuzz();
        return list.get(idx);
    }
    
}
