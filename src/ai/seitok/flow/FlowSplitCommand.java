package ai.seitok.flow;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tags a method as being a "command method" that is split into multiple sub-commands.
 * For instance, a command "/members" can be split into "/members add" and "/members remove".
 * Using FlowSplitCommand, we can define the given commands as:
 * <p>
 * <code>
 *     public void members$add(Invoker i, Channel c, Flow f){
 *         // ... do member adding logic ...
 *     }
 * </code>
 * </p>
 *
 * <p>
 * <code>
 *     public void members$remove(Invoker i, Channel c, Flow f){
 *         // ... do member removing logic ...
 *     }
 * </code>
 * </p>
 * Commands that utilize FlowSplitCommand must:
 * <br>- Declare a <code>@FlowCommand</code> (to create the root command)
 *       along with <code>@FlowSplitCommand</code> (signal to the loader that this command branches)
 * <br>- Separate valid sub-commands into their own methods, using the $ as a token.
 * <br>- Branching/sub-commands must not declare FlowCommand/FlowSplitCommand
 *
 * The root command can perform modifications to the Invoker/Flow prior. However, it is recommended that the
 * Flow remains unchanged as, internally, the first argument (the one after the root command) will be interpreted
 * as the sub-command the user wishes to invoke.
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface FlowSplitCommand {}
