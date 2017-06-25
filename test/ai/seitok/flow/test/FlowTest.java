package ai.seitok.flow.test;

import ai.seitok.flow.Faucet;
import ai.seitok.flow.FlowException;
import ai.seitok.flow.StringFlow;
import ai.seitok.flow.env.Channel;
import ai.seitok.flow.env.Invoker;
import ai.seitok.flow.interp.Interpreters;
import ai.seitok.flow.test.mock.*;
import org.junit.*;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Optional;

public class FlowTest {

    private static final int MOCK_INVOKER_SIZE = 16;

    private Invoker[] mockInvokers;
    private Channel mockChannel;

    @Before
    public void setUp(){
        mockInvokers = new Invoker[MOCK_INVOKER_SIZE];
        mockChannel = new TestChannel(mockInvokers);
        Faucet.initialize(null); // setup basic SimpleRegisterer
    }

    @After
    public void tearDown(){
        Faucet.deinitialize();
    }

    @Test
    public void testStockInterpreters(){
        // we don't test float/double as it can cause inaccuracies
        Assert.assertEquals(Interpreters.of(String.class).apply("String"), "String");
        Assert.assertEquals((byte)Interpreters.of(byte.class).apply("127"), 127);
        Assert.assertEquals((short)Interpreters.of(short.class).apply("32767"), 32767);
        Assert.assertEquals((char)Interpreters.of(char.class).apply("\uffff"), 65535);
        Assert.assertEquals((int)Interpreters.of(int.class).apply("2147483647"), 2147483647);
        Assert.assertEquals((long)Interpreters.of(long.class).apply("9223372036854775807"), 9223372036854775807L);
    }

    @Test
    public void testCustomInterpreter(){
        String random = randomString();
        Interpreters.register(TestDummy.class, TestDummy::new);
        TestDummy dummy = Interpreters.of(TestDummy.class).apply(random);
        Assert.assertEquals(random, dummy.contain);
    }

    @Test(expected = FlowException.class)
    public void testInterpreterFail(){
        Interpreters.of(int.class).apply("not an int");
        Assert.fail("Integers are strings now.");
    }

    @Test
    public void testSendingChannelMessage(){
        String str = randomString();
        mockChannel.sendMessage(str);
        String messageRecv;
        for(int i=0; i < MOCK_INVOKER_SIZE; i++){
            messageRecv = ((TestInvoker)mockInvokers[i]).consumeMessage();
            Assert.assertNotNull(messageRecv);
            Assert.assertEquals(str, messageRecv);
        }
    }

    @Test
    public void testClassLoading(){
        Optional<TestCommandClass> loaded = Faucet.loadClass(TestCommandClass.class);
        Assert.assertTrue(loaded.isPresent());
    }

    @Test
    public void testCommandInvocation(){
        Optional<TestCommandClass> loaded = Faucet.loadClass(TestCommandClass.class);
        Assert.assertTrue(loaded.isPresent());

        Faucet.process("test", mockInvokers[0], new StringFlow(new String[0]));
        String messageRecv = ((TestInvoker)mockInvokers[0]).consumeMessage();
        Assert.assertNotNull(messageRecv);
        Assert.assertEquals(messageRecv, TestCommandClass.TEST_MESSAGE);
    }

    @Test
    public void testAliasInvocation(){
        Optional<TestCommandClass> loaded = Faucet.loadClass(TestCommandClass.class);
        Assert.assertTrue(loaded.isPresent());

        Faucet.process("tset", mockInvokers[0], new StringFlow(new String[0]));
        String messageRecv = ((TestInvoker)mockInvokers[0]).consumeMessage();
        Assert.assertNotNull(messageRecv);
        Assert.assertEquals(messageRecv, TestCommandClass.TEST_MESSAGE);
    }

    @Test
    public void testBadFlowArguments(){
        Optional<TestCommandClass> loaded = Faucet.loadClass(TestCommandClass.class);
        Assert.assertTrue(loaded.isPresent());

        Faucet.process("testWithParams", mockInvokers[0], new StringFlow(new String[]{"notAnInt longerThan1Character"}));
        String failMessageRecv = ((TestInvoker)mockInvokers[0]).consumeMessage();
        Assert.assertNotNull(failMessageRecv);
        Assert.assertEquals("Oops! You've made a mistake here:", failMessageRecv);
    }

    @Test
    public void testSubcommandInvocation(){
        Optional<TestCommandClassWithSubcommands> loaded = Faucet.loadClass(TestCommandClassWithSubcommands.class);
        Assert.assertTrue(loaded.isPresent());

        Faucet.process("root", mockInvokers[0], new StringFlow(new String[]{"child1"}));
        Faucet.process("root", mockInvokers[0], new StringFlow(new String[]{"child2"}));
        Faucet.process("root", mockInvokers[0], new StringFlow(new String[]{"child3"}));

        String msg1 = ((TestInvoker)mockInvokers[0]).consumeMessage();
        Assert.assertNotNull(msg1);
        Assert.assertEquals(msg1, TestCommandClassWithSubcommands.TEST_MESSAGE_1);

        String msg2 = ((TestInvoker)mockInvokers[0]).consumeMessage();
        Assert.assertNotNull(msg2);
        Assert.assertEquals(msg2, TestCommandClassWithSubcommands.TEST_MESSAGE_2);

        String msg3 = ((TestInvoker)mockInvokers[0]).consumeMessage();
        Assert.assertNotNull(msg3);
        Assert.assertEquals(msg3, TestCommandClassWithSubcommands.TEST_MESSAGE_3);
    }

    @Test
    public void testUnloadCommands(){
        Optional<TestCommandClass> loaded = Faucet.loadClass(TestCommandClass.class);
        Assert.assertTrue(loaded.isPresent());

        Faucet.process("test", mockInvokers[0], new StringFlow(new String[0]));
        String messageRecv = ((TestInvoker)mockInvokers[0]).consumeMessage();
        Assert.assertNotNull(messageRecv);
        Assert.assertEquals(messageRecv, TestCommandClass.TEST_MESSAGE);

        Faucet.unloadClass(TestCommandClass.class);
        Assert.assertFalse(Faucet.process("test", mockInvokers[0], new StringFlow(new String[0])));
    }

    private static final SecureRandom random = new SecureRandom();
    public static String randomString(){
        return new BigInteger(130, random).toString(32);
    }

}
