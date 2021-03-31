package br.unb.cic.jfuzzer.greybox.tmp;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import br.unb.cic.jfuzzer.api.Runner;
import br.unb.cic.jfuzzer.api.RunnerResult;
import br.unb.cic.jfuzzer.api.RunnerStatus;
import br.unb.cic.jfuzzer.util.coverage.CoverageUtil;
import br.unb.cic.jfuzzer.util.observer.Event;
import br.unb.cic.jfuzzer.util.observer.JFuzzerObserver;

public class CommonsCodecRunner implements Runner, JFuzzerObserver {

    private List<Event> events = new LinkedList<>();

    @Override
    public <T> RunnerResult<T> run(T input) {
        System.out.println("\n[CommonsCodecRunner] Running: " + input);
        events = new LinkedList<>();
        RunnerResult<T> result = new RunnerResult<>(input, RunnerStatus.UNRESOLVED);
        try {
            Base64 base64 = new Base64();

            // TODO criar utils to/from byte array
            String encodedVersion = new String(base64.encode(input.toString().getBytes()));
            String decodedVersion = new String(base64.decode(encodedVersion.getBytes()));

            if (input.equals(decodedVersion)) {
                result = new RunnerResult<>(input, RunnerStatus.PASS);
            } else {
                result = new RunnerResult<>(input, RunnerStatus.FAIL);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result = new RunnerResult<>(input, RunnerStatus.FAIL);
        }

//        events.forEach(e -> System.out.println(">>> "+ e));
        showEvents();

//        tmpShowMethods();
        tmpShowLines();

        return result;
    }

    private void showEvents() {
        events.forEach(e -> System.out.println(">>> " + e));
    }

//    private void tmpShowMethods() {
//        System.err.println("METHODS ......");
//        Map<String, Integer> methods = CoverageUtil.getMethods();
//        methods.keySet().forEach(k -> System.err.println(k + "=" + methods.get(k)));
//    }
    
    private void tmpShowLines() {
        System.err.println("LINES ...................................");
        Map<String, List<Integer>> lines = CoverageUtil.getLines();
        lines.keySet().forEach(k -> System.err.println(k + "=" + lines.get(k)));
    }

    public void updateEvent(Event event) {
        events.add(event);
    }

}
