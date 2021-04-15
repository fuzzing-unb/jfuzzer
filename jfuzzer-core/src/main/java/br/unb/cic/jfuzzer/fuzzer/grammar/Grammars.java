package br.unb.cic.jfuzzer.fuzzer.grammar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grammars {
    public static final Map<String, List<String>> TERMINALS = terminals();
    public static final Map<String, List<String>> CGI = cgi();
    public static final Map<String, List<String>> URL = url();
    public static final Map<String, List<String>> EMAIL = email();
    public static final Map<String, List<String>> PHONE = phone();

    private static Map<String, List<String>> terminals() {
        Map<String, List<String>> grammar = new HashMap<>();
        grammar.put("<start>", List.of("<expr>"));
        grammar.put("<expr>", List.of("<term> + <expr>", "<term> - <expr>", "<term>"));
        grammar.put("<term>", List.of("<factor> * <term>", "<factor> / <term>", "<factor>"));
        grammar.put("<factor>", List.of("+<factor>", "-<factor>", "(<expr>)", "<integer>.<integer>", "<integer>"));
        grammar.put("<integer>", List.of("<digit><integer>", "<digit>"));
        grammar.put("<digit>", List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));
        return grammar;
    }

    private static Map<String, List<String>> cgi() {
        Map<String, List<String>> grammar = new HashMap<>();
        grammar.put("<start>", List.of("<string>"));
        grammar.put("<string>", List.of("<letter>", "<letter><string>"));
        grammar.put("<letter>", List.of("<plus>", "<percent>", "<other>"));
        grammar.put("<plus>", List.of("+"));
        grammar.put("<percent>", List.of("%<hexdigit><hexdigit>"));
        grammar.put("<hexdigit>", List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"));
        grammar.put("<other>", List.of("0", "1", "2", "3", "4", "5", "a", "b", "c", "d", "e", "-", "_"));
        return grammar;
    }

    private static Map<String, List<String>> url() {
        Map<String, List<String>> grammar = new HashMap<>();
        grammar.put("<start>", List.of("<url>"));
        grammar.put("<url>", List.of("<scheme>://<authority><path><query>"));
        grammar.put("<scheme>", List.of("http", "https", "ftp", "ftps"));
        grammar.put("<authority>", List.of("<host>", "<host>:<port>", "<userinfo>@<host>", "<userinfo>@<host>:<port>"));
        grammar.put("<host>", List.of("cispa.saarland", "www.google.com", "fuzzingbook.com", "ppgi.unb.br", "cic.unb.br"));
        grammar.put("<port>", List.of("80", "8080", "<nat>"));
        grammar.put("<nat>", List.of("<digit>", "<digit><digit>"));
        grammar.put("<digit>", List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));
        grammar.put("<userinfo>", List.of("user:password"));
        grammar.put("<path>", List.of("", "/", "/<id>"));
        grammar.put("<id>", List.of("abc", "def", "x<digit><digit>"));
        grammar.put("<query>", List.of("", "?<params>"));
        grammar.put("<params>", List.of("<param>", "<param>&<params>"));
        grammar.put("<param>", List.of("<id>=<id>", "<id>=<nat>"));
        return grammar;
    }

    private static Map<String, List<String>> email() {
        Map<String, List<String>> grammar = new HashMap<>();
        grammar.put("<start>", List.of("<email>"));
        grammar.put("<email>", List.of("<user>@<host>"));
        grammar.put("<host>", List.of("gmail.com", "outlook.com", "unb.br", "protonmail.com"));
        grammar.put("<user>", List.of("fausto", "luis", "pedro", "rodrigo", "walter"));
        return grammar;
    }

    private static Map<String, List<String>> phone() {
        Map<String, List<String>> grammar = new HashMap<>();
        grammar.put("<start>", List.of("<phone-number>"));
        grammar.put("<phone-number>", List.of("(<area>)<exchange>-<line>"));
        grammar.put("<area>", List.of("<lead-digit><digit>"));
        grammar.put("<exchange>", List.of("<lead-digit><digit><digit><digit>"));
        grammar.put("<line>", List.of("<digit><digit><digit><digit>"));
        grammar.put("<lead-digit>", List.of("2", "3", "4", "5", "6", "7", "8", "9"));
        grammar.put("<digit>", List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));
        return grammar;
    }

}
