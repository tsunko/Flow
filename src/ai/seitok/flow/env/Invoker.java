package ai.seitok.flow.env;

/**
 * Represents an entity executing a command
 * @param <U> The underlying implementation type.
 */
public interface Invoker<U> extends Messagable, Wrapper<U> {

    /**
     * @return The name of the invoker
     */
    String getName();

    /**
     * @return The ID of the invoker. Can be anything as IDs are platform specific.
     */
    String getID();

    /**
     * Verifies if the invoker has permission to continue invocation.
     * @param permission The permission identifier
     * @return <code>true</code> if the invoker had permission; false otherwise.
     */
    boolean hasPermission(String permission);

}
