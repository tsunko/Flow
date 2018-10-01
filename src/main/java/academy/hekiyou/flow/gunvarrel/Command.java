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
        if(!checkPermission(invoker)) return;

        try {
            method.invoke(ref, invoker, chan, flow);
        } catch (IllegalAccessException | InvocationTargetException e){
            if(e.getCause() instanceof FlowException || e.getCause() instanceof FlowEmptyException){
                invoker.sendMessage(Faucet.getSettings().usageError);
                invoker.sendMessage(formatError(metadata.usage(), flow.index()));
                return;
            }
            e.printStackTrace();
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

    boolean checkPermission(Invoker invoker){
        if(!invoker.hasPermission(metadata.permission())){
            invoker.sendMessage(Faucet.getSettings().permissionError);
            return false;
        }
        return true;
    }

}
