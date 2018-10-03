package academy.hekiyou.flow.gunvarrel;

import academy.hekiyou.flow.Faucet;
import academy.hekiyou.flow.Flow;
import academy.hekiyou.flow.FlowCommand;
import academy.hekiyou.flow.env.Channel;
import academy.hekiyou.flow.env.Invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

/**
 * Represents a divided command registered by Gunvarrel
 */
class SplitCommand extends Command {

    private final Map<String, Method> submethods;

    public SplitCommand(String name, Object ref, Method parent, Map<String, Method> submethods, FlowCommand metadata){
        super(name, ref, parent, metadata);
        this.submethods = Collections.unmodifiableMap(submethods);
    }

    @Override
    public void execute(Invoker invoker, Channel channel, Flow flow){
        if(!invoker.hasPermission(getMetadata().permission())){
            invoker.sendMessage(Faucet.getSettings().permissionError);
            return;
        }

        try {
            // apply any changes the parent method to the invoker and flow
            // technically, this is probably bad design as the root command probably shouldn't modify the invoker
            // however, in practice, it feels like the root command may need to perform prep with the invoker first
            // and then its subcommand can take over
            invokeMethodWithArguments(method, invoker, channel, flow);
        } catch (IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }

        Method subMethod = submethods.get(flow.next());
        if(subMethod == null){
            invoker.sendMessage(Faucet.getSettings().invalidSubcommandError);
            invoker.sendMessage(String.join(", ",submethods.keySet()));
            return;
        }

        try {
            // now pass the maybe-modified invoker/flow to the sub-command method
            invokeMethodWithArguments(subMethod, invoker, channel, flow);
        } catch (IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
    }

}
