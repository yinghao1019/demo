package exception;

public class FileExtensionNotAllowedException extends RuntimeException{
	public FileExtensionNotAllowedException() {
        super();
    }

    public FileExtensionNotAllowedException(String message) {
        super(message);
    }

    public FileExtensionNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileExtensionNotAllowedException(Throwable cause) {
        super(cause);
    }
}
