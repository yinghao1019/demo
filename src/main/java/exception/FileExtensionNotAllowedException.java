package exception;

public class FileExtensionNotAllowedException extends RuntimeException {

	public FileExtensionNotAllowedException() {
		super();
	}

	public FileExtensionNotAllowedException(String fileName) {
		super(String.format("不支援的檔案格式。檔名: %s", fileName));

	}

	public FileExtensionNotAllowedException(String fileName, Throwable cause) {
		super(String.format("不支援的檔案格式。檔名: %s", fileName), cause);
	}

	public FileExtensionNotAllowedException(Throwable cause) {
		super(cause);
	}
}
