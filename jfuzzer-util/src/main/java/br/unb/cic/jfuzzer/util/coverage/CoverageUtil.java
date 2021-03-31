package br.unb.cic.jfuzzer.util.coverage;

import java.util.HashMap;
import java.util.Map;

public class CoverageUtil {

    private static final Map<String, Integer> lines = new HashMap<>();
    private static final Map<String, Integer> methods = new HashMap<>();
    private static final Map<String, Integer> branches = new HashMap<>();

    public static void updateLine(String className) {
        if (!lines.containsKey(className)) {
            lines.put(className, 0);
        }
        lines.put(className, lines.get(className) + 1);
    }
    
    public static void updateMethod(String className) {
        if (!methods.containsKey(className)) {
            methods.put(className, 0);
        }
        methods.put(className, methods.get(className) + 1);
    }
    
    public static void updateBranch(String className, String methodName) {
        String fullName = className+"."+methodName;
        if (!branches.containsKey(fullName)) {
            branches.put(fullName, 0);
        }
        branches.put(fullName, branches.get(className) + 1);
    }

    public static Map<String, Integer> getLines() {
        return lines;
    }

    public static Map<String, Integer> getMethods() {
        return methods;
    }

    public static Map<String, Integer> getBranches() {
        return branches;
    }
    
    

}
