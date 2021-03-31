package br.unb.cic.jfuzzer.util.coverage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Coverage implements Iterable<CoverageUnit> {
    private List<CoverageUnit> coverages;

    public Coverage() {
        this.coverages = new ArrayList<>();
    }
    
    @Override
    public Iterator<CoverageUnit> iterator() {
        return coverages.iterator();
    }

}
