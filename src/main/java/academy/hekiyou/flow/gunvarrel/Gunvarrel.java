package academy.hekiyou.flow.gunvarrel;

import academy.hekiyou.flow.Flow;
import academy.hekiyou.flow.FlowCommand;
import academy.hekiyou.flow.FlowSplitCommand;
import academy.hekiyou.flow.env.Channel;
import academy.hekiyou.flow.env.Invoker;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Gunvarrel is a semi-automatic command loading and management system.
 * Full automation may be possible, however, it would significantly increase start-up time.
 */
public class Gunvarrel implements Loader, Executor {

    private final Registerer reg;

    public Gunvarrel(Registerer reg){
        this.reg = reg;
    }

    @Override
    public <T> Optional<T> loadClass(Class<T> klass) {
        T inst;
        try {
            inst = klass.newInstance();
        } catch (IllegalAccessException | InstantiationException e){
            return Optional.empty();
        }

        Method[] commandMethods =
                Stream.of(klass.getDeclaredMethods())
                    .filter(m -> m.isAnnotationPresent(FlowCommand.class))
                    .toArray(Method[]::new);

        Command newCommand;
        for(Method method : commandMethods){
            FlowCommand meta = method.getAnnotation(FlowCommand.class);
            String cmdName = method.getName();
            
            if(!isValidMethodParams(method.getParameterTypes())){
                throw new IllegalArgumentException("Badly formatted method: " + cmdName +
                        " (" + method.getDeclaringClass().getName() + ")");
            }

            if(reg.isRegistered(cmdName)) {
                throw new IllegalStateException(cmdName + " is already registered!");
            }

            if(method.isAnnotationPresent(FlowSplitCommand.class)){
                newCommand = new SplitCommand(cmdName, inst, method,
                                    Stream.of(klass.getDeclaredMethods())
                                        .filter(m -> {
                                            String[] arr = m.getName().split("\\$");
                                            return arr.length == 2 && arr[0].equals(cmdName);
                                        })
                                        .collect(Collectors.toMap(m ->
                                            m.getName().split("\\$")[1]
                                        , Function.identity())),
                                    meta);
            } else {
                newCommand = new Command(cmdName, inst, method, meta);
            }

            reg.register(newCommand);
        }

        return Optional.of(inst);
    }

    @Override
    public boolean findAndExecute(String commandName, Invoker invoker, Channel chan, Flow flow) {
        Command cmd = reg.getCommand(commandName);
        if(cmd == null)
            return false;
        cmd.execute(invoker, chan, flow);
        return true;
    }

    @Override
    public List<String> unloadClass(Class<?> klass) {
        Command[] toUnreg = reg.getRegistered().keySet().stream()
                .map(reg::getCommand)
                .filter(cmd -> cmd.ref.getClass() == klass)
                .toArray(Command[]::new);

        Stream.of(toUnreg).forEach(reg::unregister);

        return Stream.of(toUnreg).map(Command::getName).collect(Collectors.toList());
    }

    private final boolean isValidMethodParams(Class<?>[] params){
        return params.length == 3 &&
                Invoker.class.isAssignableFrom(params[0]) &&
                Channel.class.isAssignableFrom(params[1]) &&
                Flow.class.isAssignableFrom(params[2]) ;
    }

}
