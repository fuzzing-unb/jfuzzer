package br.unb.cic.jfuzzer.fuzzer.grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.unb.cic.jfuzzer.fuzzer.NumberFuzzer;
import br.unb.cic.jfuzzer.util.Range;

public class GrammarFuzzer extends AbstractGrammarFuzzer {
    private static final String START = "<start>";

    private static final Random random = new Random();
    private static final Pattern pattern = Pattern.compile("(<[^<> ]*>)");

    Map<String, List<String>> grammar;
    ListFuzzer listSelector;

    public GrammarFuzzer() {
        listSelector = new ListFuzzer();
    }

    
    
//    private String resolve(String key) {
//        List<String> list = grammar.get(key);
//        String selectedValue = listSelector.setList(list).fuzz();
//        
//        return "";
//    }
    
//    private boolean hasKeyword(String value) {
//        Matcher matcher = pattern.matcher(text);
//        int matches = 0;
//        while (matcher.find()) {
//            System.err.println("matcher=" + text.substring(matcher.start(), matcher.end()));
//            matches++;
//        }
//        return matches;
//    }
    

//    private void teste(Map<String, List<String>> grammar) {
//        List<String> lista = grammar.get(START);
//    }


       

    public static int runTest(String regex, String text) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        int matches = 0;
        while (matcher.find()) {
            System.err.println("matcher=" + text.substring(matcher.start(), matcher.end()));
            matches++;
        }
        return matches;
    }

    public static void main(String[] args) {
        ArrayList<String> grammar = new ArrayList<>();
        grammar.add("<start>: [<expr>]");
        grammar.add("<expr>: [<term> + <expr>, <term> - <expr>, <term>]");
        grammar.add("<term>: [<factor> * <term>, <factor> / <term>, <factor>]");
        grammar.add("<factor>: [+<factor>, -<factor>, (<expr>), <integer>.<integer>, <integer>]");
        grammar.add("<integer>: [<digit><integer>, <digit>]");
        grammar.add("<digit>: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]");

//        Map<String, List<String>> map = new GrammarFuzzer().parse(grammar);
//        map.forEach((k, v) -> {
//            System.out.println(k + "=" + tmp(v));
//        });
//
//        map.values().forEach(System.err::println);
//
//        // int matches = runTest("[^bcr]at", "sat mat eat");
//        int matches = runTest("<\\S+>", "<term> + <expr>, <term> - <expr>, <term>");
//        System.err.println("matches=" + matches);
        
        
        
//        final String tmp = "\"<1>\", {'option': 'value'}";
//        nonTerminals(tmp).stream().map(t -> extract(t, tmp)).forEach(System.out::println);
        
        
        GrammarFuzzer grammarFuzzer = new GrammarFuzzer();
        Map<String, List<String>> map = grammarFuzzer.parse(grammar);        
        String simpleGrammarFuzzer = grammarFuzzer.simpleGrammarFuzzer(map, START);
        System.out.println(simpleGrammarFuzzer);
        
        
//        List<String> results = new ArrayList<>(); 
//        for(int i=0; i < 10; i++) {
//            results.add(grammarFuzzer.simpleGrammarFuzzer(map, START));
//        }
//        results.forEach(System.out::println);
    }
    
    
    private String simpleGrammarFuzzer(Map<String, List<String>> grammar, String start_symbol) {
        int max_nonterminals=5;
        int max_expansion_trials=100;
        
        String term = start_symbol;
        int expansion_trials = 0;
        
        List<MatchResult> nonTerminals = nonTerminals(term);
        while(!nonTerminals.isEmpty()) {
            System.err.println("\nterm="+term);
            System.err.println("nonTerminals="+nonTerminals);
            String symbol_to_expand = extract(listSelector.setList(nonTerminals).fuzz(), term);
            System.err.println("symbol_to_expand: "+symbol_to_expand);
            
            List<String> expansions = grammar.get(symbol_to_expand);
            System.err.println("expansions="+expansions);
//            int idx = new NumberFuzzer<Integer>(new Range<>(0,expansions.size()-1)).fuzz();
//            System.err.println("idx="+idx);
//            String expansion = expansions.get(idx);
            String expansion = listSelector.setList(expansions).fuzz();
            System.err.println("expansion="+expansion);
            
            String newTerm = term.replaceFirst(symbol_to_expand, expansion);
            System.err.println("newTerm="+newTerm);
            
//            term = newTerm;
//            nonTerminals = nonTerminals(term);
            
            if(nonTerminals(newTerm).size() < max_nonterminals) {
                System.err.println("entrou ....");
                term = newTerm;
                System.err.println(">>>> "+symbol_to_expand+" --> "+expansion);
                nonTerminals = nonTerminals(term);
                expansion_trials = 0;
            }else {
                System.err.println("ELSE .........");
                expansion_trials++;
                if(expansion_trials >= max_expansion_trials) {
                    throw new RuntimeException("aaaa");
                }
            }
            
            
//            nonTerminals = nonTerminals(symbol_to_expand);
//            System.out.println(nonTerminals);
//            cond_tmp = false;
        }
        return term;
        
//        String selectedValue = listSelector.setList(list).fuzz();
    }
    
    
    private static List<MatchResult> nonTerminals(String text) {
        List<MatchResult> results = new ArrayList<>();
//        Pattern pattern = Pattern.compile("(<[^<> ]*>)");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            results.add(matcher.toMatchResult());
        }
        return results;
    }
    
    private static boolean isNonTerminal(String expansion) {
        return pattern.matcher(expansion).find();
    }
    
    private static String extract(MatchResult result, String expansion) {
        return expansion.substring(result.start(), result.end());
    }

    private static String tmp(List<String> lista) {
        StringBuilder sb = new StringBuilder();
        for (String a : lista) {
            sb.append(a + " ## ");
        }
        return sb.toString();
    }
}