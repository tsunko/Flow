package ai.seitok.flow.test.mock;

import ai.seitok.flow.Flow;
import ai.seitok.flow.FlowCommand;
import ai.seitok.flow.FlowSplitCommand;
import ai.seitok.flow.env.Invoker;
import ai.seitok.flow.test.FlowTest;

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
    public void root(Invoker invoker, Flow flow){}

    public void root$child1(Invoker invoker, Flow flow){
        invoker.sendMessage(TEST_MESSAGE_1);
    }

    public void root$child2(Invoker invoker, Flow flow){
        invoker.sendMessage(TEST_MESSAGE_2);
    }

    public void root$child3(Invoker invoker, Flow flow){
        invoker.sendMessage(TEST_MESSAGE_3);
    }

}
