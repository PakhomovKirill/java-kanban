package exception;

import java.io.IOException;

public class ManagerSaveException extends IOException {

    String message;

    public ManagerSaveException(final String message) {
        super(message);
        this.message = message;
    }

    public String getDetailMessage() {
        return getMessage() + " : " + this.message;
    }
}
