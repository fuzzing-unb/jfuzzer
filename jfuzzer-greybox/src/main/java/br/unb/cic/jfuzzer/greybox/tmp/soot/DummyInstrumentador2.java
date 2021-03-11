package br.unb.cic.jfuzzer.greybox.tmp.soot;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import soot.Body;
import soot.BodyTransformer;
import soot.G;
import soot.Local;
import soot.PackManager;
import soot.RefType;
import soot.Scene;
import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.Transform;
import soot.Type;
import soot.Unit;
import soot.UnitPatchingChain;
import soot.Value;
import soot.javaToJimple.LocalGenerator;
import soot.jimple.AssignStmt;
import soot.jimple.InvokeStmt;
import soot.jimple.Jimple;
import soot.jimple.JimpleBody;
import soot.jimple.StringConstant;
import soot.jimple.internal.JIfStmt;
import soot.options.Options;

public class DummyInstrumentador2 {

    public static String sourceDirectory = System.getProperty("user.dir") + File.separator + "target/classes/br/unb/cic/fuzzer/greybox/tmp/sort";// + File.separator + "BasicAPI";
    public static String clsName = "InsertionSort";
    public static String methodName = "sort";


    public static void setupSoot() {
        System.err.println("setupSoot .................");
        
        G.reset();
        Options.v().set_prepend_classpath(true);
        Options.v().set_allow_phantom_refs(true);
        Options.v().set_soot_classpath(sourceDirectory);
        Options.v().set_output_format(Options.output_format_jimple);
        SootClass sc = Scene.v().loadClassAndSupport(clsName);
        sc.setApplicationClass();
        Scene.v().loadNecessaryClasses();

        tmp();

        System.err.println("runPacks ................");
        PackManager.v().runPacks();
//
//        PackManager.v().writeOutput();
    }

    public static void tmp() {
        System.err.println("tmp ..............");
        PackManager.v().getPack("jtp").add(new Transform("jtp.fuzzyInstrumentation", new BodyTransformer() {
            @Override
            protected void internalTransform(Body b, String phaseName, Map<String, String> options) {
                System.err.println("TRANSFORM");
                JimpleBody body = (JimpleBody) b;
                UnitPatchingChain units = b.getUnits();
                List<Unit> generatedUnits = new ArrayList<>();

                // The message that we want to log
                String content = String.format("Beginning of method %s", body.getMethod().getSignature());
//                // In order to call "System.out.println" we need to create a local containing "System.out" value
//                Local psLocal = generateNewLocal(body, RefType.v("java.io.PrintStream"));
//                // Now we assign "System.out" to psLocal
//                SootField sysOutField = Scene.v().getField("<java.lang.System: java.io.PrintStream out>");
//                AssignStmt sysOutAssignStmt = Jimple.v().newAssignStmt(psLocal, Jimple.v().newStaticFieldRef(sysOutField.makeRef()));
//                generatedUnits.add(sysOutAssignStmt);
//
//                // Create println method call and provide its parameter
////                SootMethod printlnMethod = Scene.v().grabMethod("<java.io.PrintStream: void println(java.lang.String)>");
////                Value printlnParamter = StringConstant.v(content);
////                InvokeStmt printlnMethodCallStmt = Jimple.v().newInvokeStmt(Jimple.v().newVirtualInvokeExpr(psLocal, printlnMethod.makeRef(), printlnParamter));
//                generatedUnits.add(println(content,psLocal));

                generatedUnits.addAll(println(content, body));
                units.insertBefore(generatedUnits, body.getFirstNonIdentityStmt());
                b.validate();

                Map<Unit, List<Unit>> printlnMap = new HashMap<>();
                System.err.println("aaaa");
                for (Unit u : body.getUnits()) {
                    content = String.format("Unit %s, linha=%d", u.toString(), u.getJavaSourceStartLineNumber());
                    System.err.println(content);
                    //generatedUnits.addAll(println(content, body));
                    List<Unit> printlnUnits = println(content, body);
                    printlnMap.put(u, printlnUnits);
                    //units.insertBefore(printlnUnits, u);
                    
                    //units.insertAfter(printlnUnits, u);                    
                    //b.validate();
                }
                
                printlnMap.forEach((k, v) -> units.insertBefore(v,k));
                b.validate();

                // Insert the generated statement before the first non-identity stmt
               // units.insertBefore(generatedUnits, body.getFirstNonIdentityStmt());
                // Validate the body to ensure that our code injection does not introduce any
                // problem (at least statically)
//                b.validate();
            }

            private List<Unit> println(String content, JimpleBody body) {
                List<Unit> generatedUnits = new ArrayList<>();

                // The message that we want to log
//                String content = String.format("Beginning of method %s", body.getMethod().getSignature());
                // In order to call "System.out.println" we need to create a local containing
                // "System.out" value
                Local psLocal = generateNewLocal(body, RefType.v("java.io.PrintStream"));
                // Now we assign "System.out" to psLocal
                SootField sysOutField = Scene.v().getField("<java.lang.System: java.io.PrintStream out>");
                AssignStmt sysOutAssignStmt = Jimple.v().newAssignStmt(psLocal, Jimple.v().newStaticFieldRef(sysOutField.makeRef()));
                generatedUnits.add(sysOutAssignStmt);

                // Create println method call and provide its parameter
                SootMethod printlnMethod = Scene.v().grabMethod("<java.io.PrintStream: void println(java.lang.String)>");
                Value printlnParamter = StringConstant.v(content);
                InvokeStmt printlnMethodCallStmt = Jimple.v().newInvokeStmt(Jimple.v().newVirtualInvokeExpr(psLocal, printlnMethod.makeRef(), printlnParamter));
                generatedUnits.add(printlnMethodCallStmt);

                return generatedUnits;
            }
        }));
    }

    public static Local generateNewLocal(Body body, Type type) {
        LocalGenerator lg = new LocalGenerator(body);
        return lg.generateLocal(type);
    }

    public static void main(String[] args) {
        setupSoot();

        // Retrieve printFizzBuzz's body
        SootClass mainClass = Scene.v().getSootClass(clsName);
        SootMethod sm = mainClass.getMethodByName(methodName);
        System.err.println(">>> sm=" + sm);
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
    }
}
