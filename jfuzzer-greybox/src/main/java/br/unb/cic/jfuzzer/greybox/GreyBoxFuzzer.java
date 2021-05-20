package br.unb.cic.jfuzzer.greybox;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import br.unb.cic.jfuzzer.api.Runner;
import br.unb.cic.jfuzzer.api.RunnerResult;
import br.unb.cic.jfuzzer.greybox.tmp.CoverageAnalysis;
import br.unb.cic.jfuzzer.greybox.tmp.Seed;
import br.unb.cic.jfuzzer.util.coverage.CoverageSummary;
import br.unb.cic.jfuzzer.util.observer.Event;
import br.unb.cic.jfuzzer.util.observer.JFuzzerObserver;

public class GreyBoxFuzzer extends StringMutationFuzzer implements JFuzzerObserver {

    //dummy implementation for now
    
    private List<Event> events = new LinkedList<>();
    
    public GreyBoxFuzzer(List<String> seeds, PowerSchedule schedule, int index) {
        super(seeds,schedule,index);
    }
    
    @Override
    public List<RunnerResult<String>> run(Runner runner, int trials){        
        List<RunnerResult<String>> outcomes = new ArrayList<>(trials);
        CoverageAnalysis ca = new CoverageAnalysis();
        for (int i = 0; i < trials; i++) {
            events = new LinkedList<>();
            RunnerResult<String> result = runner.run(fuzz());
            
            CoverageSummary summary = new CoverageSummary(events);
            float lineCoverage = summary.getLineCoveragePercentage();

            if(outcomes.size() > 0){
                Seed<String> maxSeed = ca.maxCoverage(outcomes);
                double maxCoverage = maxSeed.getCoverageValue();
                if(lineCoverage > maxCoverage){
                    result.setCoverage(lineCoverage);
                    outcomes.add(result);
                }
            }else{
                result.setCoverage(lineCoverage);
                outcomes.add(result);
            }
        }
        return outcomes;
    }
        
    @Override
    public void updateEvent(Event event) {
        events.add(event);
    }
}
