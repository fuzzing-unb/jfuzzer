package br.unb.cic.jfuzzer.greybox.tmp.soot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import soot.Body;
import soot.BodyTransformer;
import soot.Local;
import soot.RefType;
import soot.Scene;
import soot.SootField;
import soot.SootMethod;
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

public class JfuzzerBodyTransformer extends BodyTransformer {

    @Override
    protected void internalTransform(Body b, String phaseName, Map<String, String> options) {
//        System.err.println("TRANSFORM");
        JimpleBody body = (JimpleBody) b;
        UnitPatchingChain units = b.getUnits();
        List<Unit> generatedUnits = new ArrayList<>();

        // The message that we want to log
        String content = String.format("Beginning of method %s", body.getMethod().getSignature());
        // In order to call "System.out.println" we need to create a local containing
        // "System.out" value
        Local psLocal = generateNewLocal(body, RefType.v("java.io.PrintStream"));
        // Now we assign "System.out" to psLocal
        SootField sysOutField = Scene.v().getField("<java.lang.System: java.io.PrintStream out>");
        AssignStmt sysOutAssignStmt = Jimple.v().newAssignStmt(psLocal, Jimple.v().newStaticFieldRef(sysOutField.makeRef()));
        generatedUnits.add(sysOutAssignStmt);

        generatedUnits.addAll(println(content, psLocal));
        // Insert the generated statement before the first non-identity stmt
        units.insertBefore(generatedUnits, body.getFirstNonIdentityStmt());
        // Validate the body to ensure that our code injection does not introduce any
        // problem (at least statically)
        b.validate();

        Map<Unit, List<Unit>> printlnMap = new HashMap<>();
//        System.err.println("aaaa");
        for (Unit u : units) {
            if(! u.toString().contains("@this")) {
            content = String.format("Unit %s, linha=%d", u.toString(), u.getJavaSourceStartLineNumber());
//            System.err.println(content);
            List<Unit> printlnUnits = println(content, psLocal);
            printlnMap.put(u, printlnUnits);
            // units.insertBefore(printlnUnits, u);
            // units.insertAfter(printlnUnits, u);
            // b.validate();
            }
        }

//        printlnMap.forEach((k, v) -> System.out.println(v + "=" + k));
        //printlnMap.forEach((k, v) -> units.insertBefore(v,k));
        
//        b.validate();
    }

    private List<Unit> println(String content, Local psLocal) {
        List<Unit> generatedUnits = new ArrayList<>();

        // Create println method call and provide its parameter
        SootMethod printlnMethod = Scene.v().grabMethod("<java.io.PrintStream: void println(java.lang.String)>");
        Value printlnParamter = StringConstant.v(content);
        InvokeStmt printlnMethodCallStmt = Jimple.v().newInvokeStmt(Jimple.v().newVirtualInvokeExpr(psLocal, printlnMethod.makeRef(), printlnParamter));
        generatedUnits.add(printlnMethodCallStmt);

        return generatedUnits;
    }
    
    public static Local generateNewLocal(Body body, Type type) {
        LocalGenerator lg = new LocalGenerator(body);
        return lg.generateLocal(type);
    }
}
