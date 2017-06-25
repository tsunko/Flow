package ai.seitok.flow.env;

import java.util.stream.Stream;

public interface Channel extends Messagable {

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
    Stream<Invoker> getAllMembers();

    /**
     * Provide a way to access the underlying implementation of this Channel
     * @return The underlying channel. Take care of it.
     */
    Object getUnderlyingImpl();

}
