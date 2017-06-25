package ai.seitok.flow.gunvarrel;

import ai.seitok.flow.Flow;
import ai.seitok.flow.FlowCommand;
import ai.seitok.flow.FlowException;
import ai.seitok.flow.FlowEmptyException;
import ai.seitok.flow.env.Invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Represents a command registered by Gunvarrel
 */
class Command {

    // expose these to to let SplitCommand interact with directly
    private final String name;
    private final FlowCommand metadata;
    final Object ref;
    final Method method;

    public Command(String name, Object ref, Method method, FlowCommand metadata){
        this.name = name;
        this.ref = ref;
        this.metadata = metadata;
        this.method = method;
    }

    public void execute(Invoker invoker, Flow flow){
        if(!checkPermission(invoker)) return;

        try {
            method.invoke(ref, invoker, flow);
        } catch (IllegalAccessException | InvocationTargetException e){
            if(e.getCause() instanceof FlowException || e.getCause() instanceof FlowEmptyException){
                invoker.sendMessage("Oops! You've made a mistake here:");
                invoker.sendMessage(formatError(metadata.usage(), flow.index()));
                return;
            }
            e.printStackTrace(); // TODO: make this not this
        }
    }

    public String getName() {
        return name;
    }

    public FlowCommand getMetadata(){
        return metadata;
    }

    String formatError(String[] usage, int errorIndex){
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < usage.length; i++){
            if(i == errorIndex){
                // TODO: add configurable format
//                builder.append(ChatColor.DARK_RED);
                builder.append(usage[i]);
            } else {
//                builder.append(ChatColor.RED);
                builder.append(usage[i]);
            }

            builder.append(' ');
        }

        return builder.toString();
    }

    boolean checkPermission(Invoker invoker){
        if(!invoker.hasPermission(metadata.permission())){
            invoker.sendMessage("You don't have permission to do that!");
            return false;
        }
        return true;
    }

}
