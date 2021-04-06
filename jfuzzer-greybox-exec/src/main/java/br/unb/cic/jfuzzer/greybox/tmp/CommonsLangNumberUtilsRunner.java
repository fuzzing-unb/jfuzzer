package br.unb.cic.jfuzzer.greybox.tmp;

//import org.apache.commons.lang3.math.NumberUtils;

import br.unb.cic.jfuzzer.api.Runner;
import br.unb.cic.jfuzzer.api.RunnerResult;
import br.unb.cic.jfuzzer.api.RunnerStatus;

public class CommonsLangNumberUtilsRunner implements Runner {

    @Override
    public <T> RunnerResult<T> run(T input) {
        System.out.println("\n[CommonsLangNumberUtilsRunner] Running: " + input);
//        RunnerResult<T> result = new RunnerResult<>(input, RunnerStatus.UNRESOLVED);
//        try {
//
//            NumberUtils.createLong(input.toString());
//            result = new RunnerResult<>(input, RunnerStatus.PASS);
//            
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
////            e.printStackTrace();
//            result = new RunnerResult<>(input, RunnerStatus.FAIL);
//        }

        return new RunnerResult<>(input, RunnerStatus.PASS);
    }

}
