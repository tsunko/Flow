package ai.seitok.flow;

/**
 * FlowEmptyException is thrown when a Flow is empty (hence its name)
 */
public class FlowEmptyException extends FlowException {

    public FlowEmptyException(){
        super();
    }

    public FlowEmptyException(String message){
        super(message);
    }

    public FlowEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlowEmptyException(String message, Throwable cause, boolean fillStack) {
        super(message, cause, fillStack);
    }

    public FlowEmptyException(String message, boolean fillStack) {
        super(message, fillStack);
    }

}
