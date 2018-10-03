package academy.hekiyou.flow.gunvarrel;

import academy.hekiyou.flow.*;
import academy.hekiyou.flow.env.Channel;
import academy.hekiyou.flow.env.Invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Represents a command registered by Gunvarrel
 */
class Command {

    private final String name;
    private final FlowCommand metadata;

    // expose these to to let SplitCommand interact with directly
    final Object ref;
    final Method method;

    public Command(String name, Object ref, Method method, FlowCommand metadata){
        this.name = name;
        this.ref = ref;
        this.metadata = metadata;
        this.method = method;
    }

    public void execute(Invoker invoker, Channel chan, Flow flow){
        if(!invoker.hasPermission(metadata.permission())){
            invoker.sendMessage(Faucet.getSettings().permissionError);
            return;
        }

        try {
            // check if the function actually needs channels to be passed to it; drop it here if it doesn't
            // (to match function parameters)
            invokeMethodWithArguments(method, invoker, chan, flow);
        } catch (IllegalAccessException | InvocationTargetException e){
            if(e.getCause() instanceof FlowException || e.getCause() instanceof FlowEmptyException){
                invoker.sendMessage(Faucet.getSettings().usageError);
                invoker.sendMessage(formatError(metadata.usage(), flow.index()));
            } else {
                // non-expected exception occurred; print it out!
                e.printStackTrace();
            }
        }
    }

    public String getName() {
        return name;
    }

    public FlowCommand getMetadata(){
        return metadata;
    }

    private String formatError(String[] usage, int errorIndex){
        StringBuilder builder = new StringBuilder();
        Faucet.Settings settings = Faucet.getSettings();

        for(int i = 0; i < usage.length; i++){
            if(i == errorIndex){
                builder.append(settings.actualErrorPrefix);
                builder.append(usage[i]);
            } else {
                builder.append(settings.errorPrefix);
                builder.append(usage[i]);
            }

            builder.append(' ');
        }

        return builder.toString();
    }

    void invokeMethodWithArguments(Method method, Invoker invoker, Channel chan, Flow flow)
    throws IllegalAccessException, InvocationTargetException {
        if(metadata.requiresChannelSupport()){
            method.invoke(ref, invoker, chan, flow);
        } else {
            method.invoke(ref, invoker, flow);
        }
    }

}
