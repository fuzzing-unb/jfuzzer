package br.unb.cic.jfuzzer.instrumenter.coverage;

import java.util.Optional;

public enum JFuzzerInstrumenterCoverageType {
    CONTROL_FLOW("Control Flow Coverage", "c"),
    BRANCH("Branch Coverage", "b"),
    LINE("Line Coverage", "l"),
    FULL("Full Coverage", "f");

    private String label;
    private String acronym;

    private JFuzzerInstrumenterCoverageType(String label, String acronym) {
        this.label = label;
        this.acronym = acronym;
    }

    public static Optional<JFuzzerInstrumenterCoverageType> fromAcronym(String acronym) {
        for (JFuzzerInstrumenterCoverageType type : values()) {
            if (type.getAcronym().equalsIgnoreCase(acronym)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }

    public String getLabel() {
        return label;
    }

    public String getAcronym() {
        return acronym;
    }

}
