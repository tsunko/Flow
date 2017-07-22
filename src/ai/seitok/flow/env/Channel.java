package ai.seitok.flow.env;

import java.util.stream.Stream;

/**
 * Represents the channel a command was executed within
 * @param <U> The underlying implementation type
 */
public interface Channel<U> extends Messagable, Wrapper<U> {

    /**
     * @return The name of the channel
     */
    String getName();

    /**
     * @return The ID of the channel. Can be anything as IDs are platform specific.
     */
    String getID();

    /**
     * Get a stream of all possible members of this channel. The Stream returned can desync.
     * @return A stream of members in the current channel.
     */
    Stream<Invoker<?>> getAllMembers();

}
