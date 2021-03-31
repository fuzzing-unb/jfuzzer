package br.unb.cic.jfuzzer.util.coverage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CoverageUtil {

    private CoverageUtil() {

    }

    private static final Map<String, List<Integer>> lines = new HashMap<>();
    private static final Map<String, Integer> methods = new HashMap<>();
    private static final Map<String, List<Integer>> branches = new HashMap<>();

    public static void updateMethod(String className) {
        if (!methods.containsKey(className)) {
            methods.put(className, 0);
        }
        methods.put(className, methods.get(className) + 1);
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
        List<Integer> list = branches.get(className);
        if (!list.contains(line)) {
            list.add(line);
            branches.put(className, list);
        }
    }

    public static Map<String, List<Integer>> getLines() {
        return lines;
    }

    public static Map<String, Integer> getMethods() {
        return methods;
    }

    public static Map<String, List<Integer>> getBranches() {
        return branches;
    }

}
