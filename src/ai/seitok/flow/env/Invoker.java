package ai.seitok.flow.env;

public interface Invoker extends Messagable {

    /**
     * @return The name of the invoker
     */
    String getName();

    /**
     * @return The ID of the invoker. Can be anything as IDs are platform specific.
     */
    String getID();

    /**
     * @return The channel (if any) that the Invoker is currently in.
     */
    Channel getChannel();

    /**
     * Verifies if the invoker has permission to continue invocation.
     * @param permission The permission identifier
     * @return <code>true</code> if the invoker had permission; false otherwise.
     */
    boolean hasPermission(String permission);

    /**
     * Provide a way to access the underlying implementation of this Invoker
     * @return The underlying invoker. Take care of it.
     */
    Object getUnderlyingImpl();

}
