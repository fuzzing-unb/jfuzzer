package br.unb.cic.jfuzzer.util.observer;

import java.util.HashSet;
import java.util.Set;

public class JFuzzerObservable {
    private static Set<JFuzzerObserver> observers = new HashSet<>();

    public static void addObserver(JFuzzerObserver observer) {
        observers.add(observer);
    }

    public static void removeObserver(JFuzzerObserver observer) {
        observers.remove(observer);
    }

    public static void setEvent(String className, String methodName, String msg) {
        setEvent(new Event(className, methodName, msg));
    }

    public static void setEvent(Event event) {
        observers.forEach(o -> o.updateEvent(event));
    }

}
