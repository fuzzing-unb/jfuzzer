package br.unb.cic.jfuzzer.greybox.tmp.soot;

import java.io.File;
import java.util.Collections;

import soot.G;
import soot.Local;
import soot.PackManager;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.JimpleBody;
import soot.jimple.internal.JIfStmt;
import soot.options.Options;

public class HelloSoot {

    public static String sourceDirectory = System.getProperty("user.dir") + File.separator + "target/classes/br/unb/cic/fuzzer/greybox/tmp/sort";// + File.separator + "BasicAPI";
    public static String clsName = "InsertionSort";
    public static String methodName = "sort";

        

//    public static void setupSoot() {
//        G.reset();
//
//// Uncomment line below to import essential Java classes
////        Options.v().set_prepend_classpath(true);
//// Comment the line below to not have phantom refs (you need to uncomment the line above)
//        Options.v().set_prepend_classpath(true);
//        Options.v().set_allow_phantom_refs(true);
//        Options.v().set_soot_classpath(sourceDirectory);
//        Options.v().set_output_format(Options.output_format_jimple);
//        Options.v().set_process_dir(Collections.singletonList(sourceDirectory));
//        Options.v().set_whole_program(true);
//        Scene.v().loadNecessaryClasses();
//        PackManager.v().runPacks();
//    }
    
    public static void setupSoot() {
        G.reset();
        Options.v().set_prepend_classpath(true);
        Options.v().set_allow_phantom_refs(true);
        Options.v().set_soot_classpath(sourceDirectory);
        Options.v().set_output_format(Options.output_format_jimple);
        SootClass sc = Scene.v().loadClassAndSupport(clsName);
        sc.setApplicationClass();
        Scene.v().loadNecessaryClasses();

    }

    public static void main(String[] args) {
        setupSoot();

        // Retrieve printFizzBuzz's body
        SootClass mainClass = Scene.v().getSootClass(clsName);
        SootMethod sm = mainClass.getMethodByName(methodName);
        JimpleBody body = (JimpleBody) sm.retrieveActiveBody();

        // Print some information about printFizzBuzz
        System.out.println("Method Signature: " + sm.getSignature());
        System.out.println("--------------");
        System.out.println("Argument(s):");
        for (Local l : body.getParameterLocals()) {
            System.out.println(l.getName() + " : " + l.getType());
        }
        System.out.println("--------------");
        System.out.println("This: " + body.getThisLocal());
        System.out.println("--------------");
        System.out.println("Units:");
        int c = 1;
        for (Unit u : body.getUnits()) {
            System.out.println("(" + c + ") " + u.toString());
            c++;
        }
        System.out.println("--------------");

        // Print statements that have branch conditions
        System.out.println("Branch Statements:");
        for (Unit u : body.getUnits()) {
            if (u instanceof JIfStmt)
                System.out.println(u.toString());
        }

        // Draw the control-flow graph of the method if 'draw' is provided in arguments
//        boolean drawGraph = false;
//        if (args.length > 0 && args[0].equals("draw"))
//            drawGraph = true;
//        if (drawGraph) {
//            UnitGraph ug = new ClassicCompleteUnitGraph(sm.getActiveBody());
//            Visualizer.v().addUnitGraph(ug);
//            Visualizer.v().draw();
//        }
    }
}
