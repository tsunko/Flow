package ai.seitok.flow.gunvarrel;

import java.util.Map;

public interface Registerer {

    /**
     * Registers a command into the registerer
     * @param command The command to register
     */
    void register(Command command);

    /**
     * Checks if a command is registered or not
     * @param commandName The command name to check
     * @return <code>true</code> if commandName is registered, <code>false</code> otherwise
     */
    boolean isRegistered(String commandName);

    /**
     * @return A map whose entries consist of the command name and class owning said command. Changes to the map
     * are not reflected.
     */
    Map<String, String> getRegistered();

    /**
     * Fetches a command from the registerer
     * @param commandName The command to get
     * @return The command as an object
     */
    Command getCommand(String commandName);

    /**
     * Unregisters the command given
     * @param command The command to unregister
     */
    void unregister(Command command);

}
