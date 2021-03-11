package br.unb.cic.jfuzzer.api;

public enum RunnerStatus {
    // The run produced correct results.
    PASS,
    // The run produced incorrect results.
    FAIL,
    // the test neither passed nor failed. This happens if the run could not take
    // place for instance, because the input was invalid.
    UNRESOLVED;
}
