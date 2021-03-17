package br.unb.cic.jfuzzer.instrumenter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class JFuzzerInstrumenterLogger {

    private static final String FILE = "/home/pedro/tmp/agent.txt";

    public static void log(String msg) {
        String m = msg + "\n";
        try {
            Files.write(
                    Paths.get(FILE),
                    msg.getBytes(),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
