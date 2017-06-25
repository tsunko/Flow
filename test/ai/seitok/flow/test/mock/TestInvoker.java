package ai.seitok.flow.test.mock;

import ai.seitok.flow.env.Channel;
import ai.seitok.flow.env.Invoker;
import ai.seitok.flow.test.FlowTest;

import java.util.ArrayList;
import java.util.List;

public class TestInvoker implements Invoker {

    private static int NEXT_ID = 1;

    private int id;
    private TestChannel channel;
    private final TestInvokerUnderlying internal = new TestInvokerUnderlying();
    private List<String> messages = new ArrayList<>();

    public TestInvoker(TestChannel channel){
        this.id = NEXT_ID++;
        this.channel = channel;
    }

    public String consumeMessage(){
        if(messages.isEmpty()) return null;
        return messages.remove(0);
    }

    @Override
    public String getName(){
        return "TestUser#" + id;
    }

    @Override
    public String getID(){
        return "test-user-" + id;
    }

    @Override
    public Channel getChannel(){
        return channel;
    }

    @Override
    public boolean hasPermission(String permission){
        return !permission.equals("test-fail-permission");
    }

    @Override
    public Object getUnderlyingImpl(){
        return internal;
    }

    @Override
    public void sendMessage(String message){
        System.out.println(getID() + " received \"" + message + "\"");
        messages.add(message);
    }

    public static class TestInvokerUnderlying {

        public String testUnderlyingData = FlowTest.randomString();

    }

}
