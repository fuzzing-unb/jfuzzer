package br.unb.cic.jfuzzer.greybox.tmp.spoon;

import spoon.FluentLauncher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtType;

public class Tmp {

    
    public static void main(String[] args) {
        CtModel model = new FluentLauncher()
                .inputResource("/pedro/desenvolvimento/workspaces/workspace-cnj/jfuzzer/jfuzzer-greybox/target/classes/br/unb/cic/jfuzzer/greybox/tmp/sort/InsertionSort.class")
                .noClasspath(true)
                .outputDirectory("/tmp")
                .processor(new LogProcessor())
                .buildModel();
        
        
     // list all packages of the model
        for(CtPackage p : model.getAllPackages()) {
          System.out.println("package: " + p.getQualifiedName());
        }
        // list all classes of the model
        for(CtType<?> s : model.getAllTypes()) {
          System.out.println("class: " + s.getQualifiedName());
        }
        
        
    }
}
