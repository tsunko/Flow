package academy.hekiyou.flow.gunvarrel;

import academy.hekiyou.flow.Flow;
import academy.hekiyou.flow.env.Channel;
import academy.hekiyou.flow.env.Invoker;

public interface Executor {

    /**
     * Finds the appropriate command (if any) and executes the it with the given Invoker and flow.
     * @param commandName The command to invoke
     * @param invoker The invoker for the command
     * @param chan The channel the command was from
     * @param flow The flow of arguments
     * @return <code>true</code> if the command was found and (was attempted to be) executed, <code>false</code> otherwise
     */
    boolean findAndExecute(String commandName, Invoker invoker, Channel chan, Flow flow);

}
