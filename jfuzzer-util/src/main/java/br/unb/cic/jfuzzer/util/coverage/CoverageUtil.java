package br.unb.cic.jfuzzer.util.coverage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CoverageUtil {

    private CoverageUtil() {

    }

    private static final Map<String, List<Integer>> lines = new HashMap<>();
    private static final Map<String, List<String>> methods = new HashMap<>();
    private static final Map<String, List<Integer>> branches = new HashMap<>();

    public static void updateMethod(String className, String methodName) {
        if (!methods.containsKey(className)) {
            methods.put(className, new LinkedList<>());
        }
        List<String> list = methods.get(className);
        if (!list.contains(methodName)) {
            list.add(methodName);
            methods.put(className, list);
        }        
    }

    public static void updateLine(String className, Integer line) {
        if (!lines.containsKey(className)) {
            lines.put(className, new LinkedList<>());
        }
        List<Integer> list = lines.get(className);
        if (!list.contains(line)) {
            list.add(line);
            lines.put(className, list);
        }
    }

    public static void updateBranch(String className, String methodName, Integer line) {
        String fullName = className + "." + methodName;
        if (!branches.containsKey(fullName)) {
            branches.put(fullName, new LinkedList<>());
        }
        List<Integer> list = branches.get(fullName);
        if (!list.contains(line)) {
            list.add(line);
            branches.put(fullName, list);
        }
    }

    public static void printMethods() {
        System.err.println("METHODS ...................................");
        Map<String, List<String>> methods = getMethods();
        //methods.keySet().forEach(k -> System.err.println(k + "=" + methods.get(k).size()));
        methods.keySet().forEach(k -> System.err.println(k + "=" + methods.get(k)));
    }

    public static void printLines() {
        System.err.println("LINES ...................................");
        Map<String, List<Integer>> lines = getLines();
        lines.keySet().forEach(k -> System.err.println(k + "=" + lines.get(k)));
    }

    public static void printBranches() {
        System.err.println("BRANCHES ...................................");
        Map<String, List<Integer>> branches = getBranches();
        branches.keySet().forEach(k -> System.err.println(k + "=" + branches.get(k)));
    }

    public static Map<String, List<Integer>> getLines() {
        return lines;
    }

    public static Map<String, List<String>> getMethods() {
        return methods;
    }

    public static Map<String, List<Integer>> getBranches() {
        return branches;
    }

}
