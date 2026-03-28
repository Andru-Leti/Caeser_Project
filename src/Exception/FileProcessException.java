package Exception;

public class FileProcessException extends RuntimeException {
    public FileProcessException(String message) {
        super(message);
    }

    public FileProcessException(String message, Throwable throwable){
        super(message, throwable);
    }
}
