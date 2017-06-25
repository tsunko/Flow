package ai.seitok.flow;

import java.util.List;

/**
 * The interface for working with command arguments.
 * Command methods must accept a Flow type (or subtype).
 */
public interface Flow {

    /**
     * Gets the argument as a string.
     * This is the same as calling <code>next(String.class)</code>
     * @return the next argument as-is
     * @throws FlowEmptyException If there are no remaining arguments.
     */
    default String next(){
        return next(String.class);
    }

    /**
     * Get the next argument as a type of <code>type</code>.
     * This is the same as calling <code>next(type, null)</code>
     * @param type                The type that the argument should be interpreted as
     * @param <T>                 The type for the next argument to be.
     * @throws FlowException      If the next argument failed to be interpreted
     * @throws FlowEmptyException If there are no remaining arguments.
     * @return                    The given argument as an object type of <code>type</code>
     */
    default <T> T next(Class<T> type){
        return next(type, null);
    }

    /**
     * Get the next argument as a type of <code>type</code>, returning <code>def</code> if it fails.
     * @param type                The type that the argument should be interpreted as
     * @param def                 The default value that should be returned should the interpretation fail.
     * @param <T>                 The type for the next argument to be.
     * @throws FlowException      If the argument could not be interpreted <b>AND</b> <code>def</code> is null.
     * @throws FlowEmptyException If there are no remaining arguments.
     * @return                    The given argument as a type of <code>type</code> or <code>def</code> if it fails.
     */
    <T> T next(Class<T> type, T def);

    /**
     * Gets the remaining arguments as a, possibly empty, String <code>List</code>
     * @return               The remaining arguments as a String <code>List</code>
     */
    default List<String> remaining(){
        return remaining(String.class);
    }

    /**
     * @return The current index of this Flow (typically the amount of arguments that have been processed so far)
     */
    int index();

    /**
     * Gets the remaining arguments as a, possibly empty, <code>List</code>
     * @param type           The type to cast to
     * @param <T>            The generic type to cast to
     * @throws FlowException If, at any point of processing, interpretation fails.
     * @return               A list containing the remaining arguments as type <code>T</code>
     */
    <T> List<T> remaining(Class<T> type);

    /**
     * @return <code>true</code> if there are any elements remaining, <code>false</code> otherwise
     */
    boolean hasRemaining();

}
