package br.unb.cic.jfuzzer.runner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.unb.cic.jfuzzer.api.Runner;
import br.unb.cic.jfuzzer.api.RunnerResult;
import br.unb.cic.jfuzzer.api.RunnerStatus;

//TODO refazer essa classe com uma forma mais eficiente/confiavel de executar um programa externo
public class ProgramRunner implements Runner {
    private List<String> commands;

    public ProgramRunner(List<String> commands) {
        this.commands = commands;
    }

    @Override
    public <T> RunnerResult<T> run(T input) {
        RunnerResult<T> result = new RunnerResult<>(input, RunnerStatus.UNRESOLVED);
        try {
            // result = execute(input);
            result = execute2(input);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    private <T> RunnerResult<T> execute(T input) throws IOException, InterruptedException {
        // inclui o parametro (input) no comando a ser executado
        ArrayList<String> cmd = new ArrayList<>(commands);
        cmd.add(input.toString());
        System.err.println("CMD=" + cmd);
        Process p = Runtime.getRuntime().exec(cmd.toArray(new String[cmd.size()]));

        String line;
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
        in.close();

        int exitValue = p.waitFor();
        if (0 == exitValue) {
            return new RunnerResult<>(input, RunnerStatus.PASS);
        }
        return new RunnerResult<>(input, RunnerStatus.FAIL);
    }

    private <T> RunnerResult<T> execute2(T input) throws IOException, InterruptedException {
        // inclui o parametro (input) no comando a ser executado
        ArrayList<String> cmd = new ArrayList<>(commands);
        cmd.add(input.toString());
//        System.err.println("CMD=" + cmd);

        Integer processReturnCode = null;
        ProcessBuilder builder = new ProcessBuilder(cmd);
        Process process;
        int timeout = 3;
        try {
            // Process process = Runtime.getRuntime().exec(argsList.toArray(new
            // String[argsList.size()]));
            process = builder.start();
            if (timeout != 0) {
                if (!process.waitFor(timeout, TimeUnit.SECONDS))
                    process.destroy();
                else
                    processReturnCode = process.exitValue();
            } else {
                processReturnCode = process.waitFor();
            }
        } catch (IOException | InterruptedException e) {
            // TODO: bug here, if the input has null character in command whe get Exception.
//            e.printStackTrace();
            return new RunnerResult<>(input, RunnerStatus.FAIL, processReturnCode, e.getMessage());
        }

        if (processReturnCode == null) {
//            throw new RuntimeException("Timed out!");
            return new RunnerResult<>(input, RunnerStatus.FAIL, processReturnCode, "TIMEOUT");
        }
        
//System.err.println("processReturnCode="+processReturnCode);
        if (0 == processReturnCode) {
            return new RunnerResult<>(input, RunnerStatus.PASS, processReturnCode, "");
        }
        return new RunnerResult<>(input, RunnerStatus.FAIL, processReturnCode, "");
    }

}
