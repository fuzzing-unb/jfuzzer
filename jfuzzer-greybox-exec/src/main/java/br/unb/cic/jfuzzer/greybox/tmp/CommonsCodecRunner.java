package br.unb.cic.jfuzzer.greybox.tmp;

import org.apache.commons.codec.binary.Base64;

import br.unb.cic.jfuzzer.api.Runner;
import br.unb.cic.jfuzzer.api.RunnerResult;
import br.unb.cic.jfuzzer.api.RunnerStatus;

public class CommonsCodecRunner implements Runner {

    @Override
    public <T> RunnerResult<T> run(T input) {
        System.out.println("\n[CommonsCodecRunner] Running: " + input);
        RunnerResult<T> result = new RunnerResult<>(input, RunnerStatus.UNRESOLVED);
        try {
            Base64 base64 = new Base64();

            String encodedVersion = new String(base64.encode(input.toString().getBytes()));
            String decodedVersion = new String(base64.decode(encodedVersion.getBytes()));

            if (input.equals(decodedVersion)) {
                result = new RunnerResult<>(input, RunnerStatus.PASS);
            } else  {
                result = new RunnerResult<>(input, RunnerStatus.FAIL);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result = new RunnerResult<>(input, RunnerStatus.FAIL);
        }

        return result;
    }

}
