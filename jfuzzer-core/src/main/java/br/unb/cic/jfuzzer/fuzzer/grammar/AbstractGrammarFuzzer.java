package br.unb.cic.jfuzzer.fuzzer.grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractGrammarFuzzer {

    
    protected Map<String, List<String>> parse(List<String> lines) {
        Map<String, List<String>> mapa = new HashMap<>();

        for (String line : lines) {
            int indexOf = line.indexOf(":");
            if (indexOf != -1) {
                String keyword = line.substring(0, indexOf);
                String values = line.substring(indexOf + 1).strip();

                mapa.put(keyword, toList(values));
            }
        }

        return mapa;
    }
    
    @Deprecated
    public Map<String, List<String>> setGrammar(List<String> lines) {
        Map<String, List<String>> mapa = new HashMap<>();
        for (String line : lines) {
            int indexOf = line.indexOf(":");
            if (indexOf != -1) {
                String keyword = line.substring(0, indexOf);
                String values = line.substring(indexOf + 1).strip();
                mapa.put(keyword, toList(values));
            }
        }
        return mapa;
    }
    
    private List<String> toList(final String str) {
        List<String> lista = new ArrayList<>();

        String tmp = str.substring(str.indexOf("[") + 1).strip();
        tmp = tmp.substring(0, tmp.length() - 1);

        String[] values = tmp.split(",");

        for (String value : values) {
            lista.add(value.strip());
        }

        return lista;
    }
}
