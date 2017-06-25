package ai.seitok.flow;

/**
 * General exception thrown when arguments can't be interpreted
 */
public class FlowException extends RuntimeException {

    public FlowException(){
        super(null, null, false, false);
    }

    public FlowException(String message){
        super(message, null, false, false);
    }

    public FlowException(String message, Throwable cause) {
        super(message, cause, false, false);
    }

    public FlowException(String message, Throwable cause, boolean fillStack) {
        super(message, cause, false, fillStack);
    }

    public FlowException(String message, boolean fillStack) {
        super(message, null, false, fillStack);
    }

}
