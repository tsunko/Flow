package ai.seitok.flow.gunvarrel;

import ai.seitok.flow.Flow;
import ai.seitok.flow.FlowCommand;
import ai.seitok.flow.env.Channel;
import ai.seitok.flow.env.Invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

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
        if(!checkPermission(invoker)) return;

        try {
            method.invoke(ref, invoker, channel, flow); // apply any changes the parent method to the invoker and flow (bad)
        } catch (IllegalAccessException | InvocationTargetException e){
            e.printStackTrace(); // TODO: make this not this
        }

        Method subMethod = submethods.get(flow.next());
        if(subMethod == null){
            invoker.sendMessage("Invalid subcommand. Valid subcommands are:");
            invoker.sendMessage(submethods.keySet().stream().collect(Collectors.joining(", ")));
            return;
        }

        try {
            subMethod.invoke(ref, invoker, channel, flow); // now pass the maybe-modified invoker/flow to the sub-command method
        } catch (IllegalAccessException | InvocationTargetException e){
            e.printStackTrace(); // TODO: make this not this
        }
    }

}
