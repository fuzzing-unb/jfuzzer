package br.unb.cic.jfuzzer.greybox.tmp;

import lombok.Data;

@Data
public class CoverageUnit {

    private String className;
    private String methodName;
    private int lineNumber;
    
}
