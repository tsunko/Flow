package ai.seitok.flow.test.mock;

import ai.seitok.flow.Flow;
import ai.seitok.flow.FlowCommand;
import ai.seitok.flow.env.Channel;
import ai.seitok.flow.env.Invoker;
import ai.seitok.flow.test.FlowTest;

public class TestCommandClass {

    public static final String TEST_MESSAGE = FlowTest.randomString();

    @FlowCommand(
            permission = "test-pass-permission",
            description = "A test command",
            usage = "/test",
            alias = "tset" // just "test" backwards
    )
    public void test(Invoker invoker, Channel channel, Flow flow){
        invoker.sendMessage("%s", TEST_MESSAGE);
    }

    @FlowCommand(
            permission = "test-pass-permission",
            description = "A test command",
            usage = {"/testWithParams", "<int>", "<char>"}
    )
    public void testWithParams(Invoker invoker, Channel channel, Flow flow){
        int arg1 = flow.next(int.class);
        char arg2 = flow.next(char.class);
        invoker.sendMessage(arg1 + "" + arg2);
    }


}
