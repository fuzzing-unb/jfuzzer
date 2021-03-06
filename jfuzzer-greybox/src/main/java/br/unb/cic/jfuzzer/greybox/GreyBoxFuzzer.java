package br.unb.cic.jfuzzer.greybox;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import br.unb.cic.jfuzzer.api.Runner;
import br.unb.cic.jfuzzer.api.RunnerResult;
import br.unb.cic.jfuzzer.fuzzer.StringFuzzer;
import br.unb.cic.jfuzzer.util.Range;
import br.unb.cic.jfuzzer.util.coverage.CoverageSummary;
import br.unb.cic.jfuzzer.util.observer.Event;
import br.unb.cic.jfuzzer.util.observer.JFuzzerObserver;

public class GreyBoxFuzzer extends StringFuzzer implements JFuzzerObserver {

    //dummy implementation for now
    
    private List<Event> events = new LinkedList<>();
    
    public GreyBoxFuzzer(Range<Integer> range, Random random, String symbols) {
        super(range, random, symbols);
    }
    
    @Override
    public List<RunnerResult<String>> run(Runner runner, int trials){        
        List<RunnerResult<String>> outcomes = new ArrayList<>(trials);
        for (int i = 0; i < trials; i++) {
            events = new LinkedList<>();
            RunnerResult<String> result = runner.run(fuzz());
            outcomes.add(result);
            
            showEvents();
            
            CoverageSummary summary = new CoverageSummary(events);
            float lineCoverage = summary.getLineCoveragePercentage();
            
            System.out.println("************** SUMMARY ********** ");
            System.out.println(summary);
            
            System.err.printf("LINE_COVERAGE_PERCENTAGE: %5.2f %% %n",lineCoverage);
        }
        return outcomes;
    }
    
    private void showEvents() {
        events.forEach(e -> System.out.println(">>> "+ e));
    }
    
    @Override
    public void updateEvent(Event event) {
        events.add(event);
    }
}
