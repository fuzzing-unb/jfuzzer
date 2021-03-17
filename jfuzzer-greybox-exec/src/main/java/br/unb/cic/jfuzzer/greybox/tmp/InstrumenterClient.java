package br.unb.cic.jfuzzer.greybox.tmp;

import br.unb.cic.jfuzzer.util.observer.Event;
import br.unb.cic.jfuzzer.util.observer.JFuzzerObserver;

public class InstrumenterClient implements JFuzzerObserver {

    public void update(Event event) {
        System.out.println(">>> "+event);
    }

}
