package br.unb.cic.jfuzzer.util.coverage;

public class CoverageException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CoverageException() {
        super();
    }

    public CoverageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CoverageException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoverageException(String message) {
        super(message);
    }

    public CoverageException(Throwable cause) {
        super(cause);
    }

}
