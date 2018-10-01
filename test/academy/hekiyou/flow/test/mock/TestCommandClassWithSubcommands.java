package academy.hekiyou.flow.test.mock;

import academy.hekiyou.flow.Flow;
import academy.hekiyou.flow.FlowCommand;
import academy.hekiyou.flow.FlowSplitCommand;
import academy.hekiyou.flow.env.Channel;
import academy.hekiyou.flow.env.Invoker;
import academy.hekiyou.flow.test.FlowTest;

// this is a really verbose name...
public class TestCommandClassWithSubcommands {

    public static final String TEST_MESSAGE_1 = FlowTest.randomString();
    public static final String TEST_MESSAGE_2 = FlowTest.randomString();
    public static final String TEST_MESSAGE_3 = FlowTest.randomString();

    @FlowCommand(
            description = "Test subcommand root command",
            usage = {"root", "<child1,child2,child3>"}
    )
    @FlowSplitCommand
    public void root(Invoker invoker, Channel channel, Flow flow){}

    public void root$child1(Invoker invoker, Channel channel, Flow flow){
        invoker.sendMessage(TEST_MESSAGE_1);
    }

    public void root$child2(Invoker invoker, Channel channel, Flow flow){
        invoker.sendMessage(TEST_MESSAGE_2);
    }

    public void root$child3(Invoker invoker, Channel channel, Flow flow){
        invoker.sendMessage(TEST_MESSAGE_3);
    }

}
