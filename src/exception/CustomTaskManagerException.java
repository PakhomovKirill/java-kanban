package exception;

public class CustomTaskManagerException extends IllegalStateException {
    String message;

    public CustomTaskManagerException(final String message) {
        super(message);
        this.message = message;
    }

    public String getDetailMessage() {
        return getMessage() + " : " + this.message;
    }
}
