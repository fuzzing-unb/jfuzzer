package br.unb.cic.jfuzzer.fuzzer.grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;

import br.unb.cic.jfuzzer.api.AbstractFuzzer;
import br.unb.cic.jfuzzer.fuzzer.ListFuzzer;

public class SimpleGrammarFuzzer extends AbstractFuzzer<String> implements GrammarFuzzer {

    private Map<String, List<String>> grammar;
    private String startSymbol;

    @Deprecated
    public SimpleGrammarFuzzer() {

    }

    public SimpleGrammarFuzzer(Map<String, List<String>> grammar, String startSymbol) {
        this.grammar = grammar;
        this.startSymbol = startSymbol;
    }

    @Override
    public String fuzz() {
        return generate(grammar, startSymbol);
    }

    private String generate(Map<String, List<String>> grammar, String startSymbol) {
        int maxNonterminals = 5;
        int maxExpansionTrials = 100;

        String term = startSymbol;
        int expansionTrials = 0;

        List<MatchResult> nonTerminals = nonTerminals(term);
        while (!nonTerminals.isEmpty()) {
            System.err.println("\nterm=" + term);
            System.err.println("nonTerminals=" + nonTerminals);
            String symbolToExpand = extract(ListFuzzer.fuzz(nonTerminals), term);
            System.err.println("symbolToExpand: " + symbolToExpand);

            List<String> expansions = grammar.get(symbolToExpand);
            System.err.println("expansions=" + expansions);
            String expansion = ListFuzzer.fuzz(expansions);
            System.err.println("expansion=" + expansion);
            String newTerm = term.replaceFirst(symbolToExpand, expansion);
            System.err.println("newTerm=" + newTerm);

            if (nonTerminals(newTerm).size() < maxNonterminals) {
                System.err.println("entrou ....");
                term = newTerm;
                System.err.println(">>>> " + symbolToExpand + " --> " + expansion);
                nonTerminals = nonTerminals(term);
                expansionTrials = 0;
            } else {
                System.err.println("ELSE .........");
                expansionTrials++;
                if (expansionTrials >= maxExpansionTrials) {
                    throw new RuntimeException("aaaa");
                }
            }

        }
        return term;
    }

    public static void main(String[] args) {
//        ArrayList<String> grammar = new ArrayList<>();
//        grammar.add("<start>: [<expr>]");
//        grammar.add("<expr>: [<term> + <expr>, <term> - <expr>, <term>]");
//        grammar.add("<term>: [<factor> * <term>, <factor> / <term>, <factor>]");
//        grammar.add("<factor>: [+<factor>, -<factor>, (<expr>), <integer>.<integer>, <integer>]");
//        grammar.add("<integer>: [<digit><integer>, <digit>]");
//        grammar.add("<digit>: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]");

//        Map<String, List<String>> map = new SimpleGrammarFuzzer().parse(grammar);
//        map.forEach((k, v) -> {
//            System.out.println(k + "=" + tmp(v));
//        });

        SimpleGrammarFuzzer simpleGrammarFuzzer = new SimpleGrammarFuzzer();
//        Map<String, List<String>> map = grammarFuzzer.parse(grammar);        
        String out = simpleGrammarFuzzer.generate(Grammars.TERMINALS, START);
        System.out.println(out);

        // TESTE ........................
        List<String> results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            results.add(simpleGrammarFuzzer.generate(Grammars.TERMINALS, START));
        }
        System.err.println("CGI:");
        for (int i = 0; i < 10; i++) {
            results.add(simpleGrammarFuzzer.generate(Grammars.CGI, START));
        }
        System.err.println("URL:");
        for (int i = 0; i < 10; i++) {
            results.add(simpleGrammarFuzzer.generate(Grammars.URL, START));
        }
        System.err.println("EMAIL:");
        for (int i = 0; i < 10; i++) {
            results.add(simpleGrammarFuzzer.generate(Grammars.EMAIL, START));
        }
        System.err.println("RESULTS::::::");
        results.forEach(System.err::println);
    }

}
