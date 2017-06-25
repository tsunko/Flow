package ai.seitok.flow.gunvarrel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * A very simple and naive implementation of Registerer backed by a simple HashMap
 */
public class SimpleRegisterer implements Registerer {

    private final Map<String, Command> registered = new HashMap<>();

    @Override
    public void register(Command command) {
        if(isRegistered(command.getName())){
            throw new IllegalStateException("already registered");
        }

        registered.put(command.getName(), command);
        for(String alias : command.getMetadata().alias()){
            if(!isRegistered(alias)) // only register aliases that haven't actually been taken already
                registered.put(alias, command);
        }
    }

    @Override
    public void unregister(Command command) {
        if(!isRegistered(command.getName())){
            // notify of failing to remove; not exception worthy
            return;
        }

        Collection<Command> values = registered.values();
        while(values.remove(command));
    }

    @Override
    public boolean isRegistered(String commandName) {
        return registered.containsKey(commandName);
    }

    @Override
    public Command getCommand(String commandName) {
        return registered.get(commandName);
    }

    @Override
    public Map<String, String> getRegistered() {
        return registered.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().ref.getClass().getName()
                ));
    }

}
