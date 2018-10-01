package academy.hekiyou.flow.test.mock;

import academy.hekiyou.flow.env.Channel;
import academy.hekiyou.flow.env.Invoker;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.stream.Stream;

public class TestChannel implements Channel<TestChannel.TestChannelUnderlying> {

    private final TestChannelUnderlying internal = new TestChannelUnderlying();
    private final Invoker[] members;

    public TestChannel(Invoker[] members){
        for(int i=0; i < members.length; i++){
            members[i] = new TestInvoker();
        }
        this.members = members;
    }

    @Override
    public String getName(){
        return "Test";
    }

    @Override
    public String getID(){
        return "test-chan-123";
    }

    @Override
    public Stream<Invoker<?>> getAllMembers(){
        return Stream.of(members);
    }

    @Override
    public TestChannel.TestChannelUnderlying getRaw(){
        return internal;
    }

    @Override
    public void sendMessage(String message) {
        Stream.of(members).forEach(m -> m.sendMessage(message));
    }

    public static class TestChannelUnderlying {

        private static final SecureRandom random = new SecureRandom();

        public String testUnderlyingData = new BigInteger(130, random).toString(32);

    }

}
