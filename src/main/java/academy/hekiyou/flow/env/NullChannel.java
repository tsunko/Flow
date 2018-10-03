package academy.hekiyou.flow.env;


import java.util.stream.Stream;

/**
 * Represents a Channel instance for any command that does not utilize channels.
 */
public class NullChannel implements Channel<Void> {

    public static final NullChannel NULL = new NullChannel();

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getID() {
        return "";
    }

    @Override
    public void sendMessage(String message){}

    @Override
    public void sendMessage(String fmt, Object... args){}

    @Override
    public Stream<Invoker<?>> getAllMembers() {
        return Stream.empty();
    }

    @Override
    public Void getRaw() {
        return null;
    }

}
