package ai.seitok.flow.gunvarrel;

import ai.seitok.flow.Flow;
import ai.seitok.flow.env.Invoker;

public interface Executor {

    /**
     * Finds the appropriate command (if any) and executes the it with the given Invoker and flow.
     * @param commandName The command to invoke
     * @param invoker The invoker for the command
     * @param flow The flow of arguments
     * @return <code>true</code> if the command was found and (was attempted to be) executed, <code>false</code> otherwise
     */
    boolean findAndExecute(String commandName, Invoker invoker, Flow flow);

}
