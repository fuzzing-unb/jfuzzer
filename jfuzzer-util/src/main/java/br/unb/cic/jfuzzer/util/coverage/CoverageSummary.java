package br.unb.cic.jfuzzer.util.coverage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.unb.cic.jfuzzer.util.observer.Event;

public class CoverageSummary {

    private int totalLines;
    private int totalMethods;
    private int totalBranches;

    private int coveredLines;
    private int coveredMethods;
    private int coveredBranches;

    private List<Event> events;

    private Map<String, List<Integer>> lines = new HashMap<>();
    private Map<String, List<String>> methods = new HashMap<>();
    private Map<String, List<Integer>> branches = new HashMap<>();

    public CoverageSummary(List<Event> events) {
        this.events = events;

        this.totalLines = count(CoverageUtil.getLines());
        this.totalMethods = count(CoverageUtil.getMethods());
        this.totalBranches = count(CoverageUtil.getBranches());

        events.forEach(e -> computeCoverageEvent(e));
    }

    private <T> int count(Map<String, List<T>> map) {
        int count = 0;
        for (String key : map.keySet()) {
            count += map.get(key).size();
        }
        return count;
    }

    private void computeCoverageEvent(Event event) {
        String msg = event.getMsg();
        Integer line = Integer.parseInt(event.getLine());

        if (msg.startsWith("LINE_")) {
            updateLine(event.getClassName(), line);
        }

        if (msg.startsWith("IF_") || msg.startsWith("GOTO_")) {
            updateBranch(event.getClassName(), event.getMethodName(), line);
        }

        this.coveredLines = count(lines);
//        this.totalMethods = count(CoverageUtil.getMethods());
        this.coveredBranches = count(branches);
    }

    private void updateMethod(String className, String methodName) {
        if (!methods.containsKey(className)) {
            methods.put(className, new LinkedList<>());
        }
        List<String> list = methods.get(className);
        if (!list.contains(methodName)) {
            list.add(methodName);
            methods.put(className, list);
        }
    }

    private void updateLine(String className, Integer line) {
        if (!lines.containsKey(className)) {
            lines.put(className, new LinkedList<>());
        }
        List<Integer> list = lines.get(className);
        if (!list.contains(line)) {
            list.add(line);
            lines.put(className, list);
        }
    }

    private void updateBranch(String className, String methodName, Integer line) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("LINES ...................................\n");
        sb.append("TOTAL: \n");
        CoverageUtil.getLines().keySet().forEach(k -> sb.append(k + "=" + CoverageUtil.getLines().get(k) + "\n"));
        sb.append("COVERED: \n");
        lines.keySet().forEach(k -> sb.append(k + "=" + lines.get(k) + "\n"));
        sb.append("TOTAL: "+totalLines+"\n");
        sb.append("COVERED: "+coveredLines+"\n");
        sb.append("% covered: "+ (coveredLines*100)/totalLines);

        return sb.toString();
    }

}
