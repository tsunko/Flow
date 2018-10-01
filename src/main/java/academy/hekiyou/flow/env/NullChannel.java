package academy.hekiyou.flow.env;


import java.util.stream.Stream;

public class NullChannel implements Channel<Void> {

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
