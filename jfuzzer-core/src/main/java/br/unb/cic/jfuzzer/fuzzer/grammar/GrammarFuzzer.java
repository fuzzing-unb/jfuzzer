package br.unb.cic.jfuzzer.fuzzer.grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface GrammarFuzzer {
    static final String START = "<start>";
    static final Pattern PATTERN = Pattern.compile("(<[^<> ]*>)");

    default Map<String, List<String>> parse(List<String> lines) {
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

        // EXAMPLE:
//      ArrayList<String> grammar = new ArrayList<>();
//      grammar.add("<start>: [<expr>]");
//      grammar.add("<expr>: [<term> + <expr>, <term> - <expr>, <term>]");
//      grammar.add("<term>: [<factor> * <term>, <factor> / <term>, <factor>]");
//      grammar.add("<factor>: [+<factor>, -<factor>, (<expr>), <integer>.<integer>, <integer>]");
//      grammar.add("<integer>: [<digit><integer>, <digit>]");
//      grammar.add("<digit>: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]");
    }

    default List<MatchResult> nonTerminals(String text) {
        List<MatchResult> results = new ArrayList<>();
        Matcher matcher = PATTERN.matcher(text);
        while (matcher.find()) {
            results.add(matcher.toMatchResult());
        }
        return results;
    }

    default boolean isNonTerminal(String expansion) {
        return PATTERN.matcher(expansion).find();
    }

    default String extract(MatchResult result, String expansion) {
        return expansion.substring(result.start(), result.end());
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
