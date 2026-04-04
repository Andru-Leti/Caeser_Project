package Exception;

public class StatisticalAnalyzerException extends RuntimeException {
    public StatisticalAnalyzerException(String message) {
        super(message);
    }

    public StatisticalAnalyzerException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
