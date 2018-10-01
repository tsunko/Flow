package academy.hekiyou.flow;

import academy.hekiyou.flow.env.Channel;
import academy.hekiyou.flow.env.Invoker;
import academy.hekiyou.flow.gunvarrel.Gunvarrel;
import academy.hekiyou.flow.gunvarrel.Registerer;
import academy.hekiyou.flow.gunvarrel.SimpleRegisterer;

import java.util.List;
import java.util.Optional;

/**
 * Controls integration within an application.
 */
public final class Faucet {

    private static final Faucet instance = new Faucet();
    private final Settings settings = new Settings();

    private Gunvarrel loader;

    /**
     * Initializes the Flow library with the given Registerer. If none (null) is provided,
     * an instance of SimpleRegisterer is used.
     * @param registerer The registerer to be used. Pass null for SimpleRegisterer.
     */
    public static void initialize(Registerer registerer){
        if(instance.loader != null) throw new IllegalStateException("already initialized!");
        if(registerer == null){
            registerer = new SimpleRegisterer(); // bad to assign parameters but w/e
        }
        instance.loader = new Gunvarrel(registerer);
    }

    /**
     * Uninitializes the current setup, if any.
     */
    public static void deinitialize(){
        instance.loader = null;
    }

    /**
     * Loads a class and its commands
     * @param klass The class to load
     * @param <T> The class's type
     * @return An <code>Optional</code> object containing either an instance of the loaded class if successful
     *         or null if failed.
     */
    public static <T> Optional<T> loadClass(Class<T> klass){
        checkForInitialization();
        return instance.loader.loadClass(klass);
    }

    /**
     * Unloads a class and its (loaded) commands.
     * @param klass The class to unload
     * @return A list containing the commands' name that were unloaded.
     */
    public static List<String> unloadClass(Class<?> klass){
        checkForInitialization();
        return instance.loader.unloadClass(klass);
    }

    /**
     * The main method to be called for when a command is detected and needs to be invoked.
     * Simply call this in place for where normal command look-up and execution logic would be.
     * @param command The command name to invoke
     * @param invoker An instance/implementation of Invoker
     * @param chan The channel that this command was processed in
     * @param flow An instance/implementation of Flow
     * @return <code>true</code> if the command was found and processed successfully, <code>false</code> otherwise
     */
    public static boolean process(String command, Invoker invoker, Channel chan, Flow flow){
        checkForInitialization();
        return instance.loader.findAndExecute(command, invoker, chan, flow);
    }

    public static Settings getSettings(){
        checkForInitialization();
        return instance.settings;
    }

    private static void checkForInitialization(){
        if(instance == null) throw new ExceptionInInitializerError("Faucet wasn't initialized! Forgot to call \"initialize()\"?");
    }

    public class Settings {

        /**
         * Represents the prefix for errors. Useful for when you're utilizing a character-controlled coloring system.
         */
        public String errorPrefix = "";

        /**
         * Represents the prefix for the actual error. "Actual error" meaning the location in which the error occurred
         * in a command.
         *
         * For instance, if the command given is "/tp x y z", where "x", "y", and "z" are integers and an user inputs
         * uses it incorrectly, executing "/tp 5 2 a", the "a" would be prefixed by actualErrorPrefix, while the rest
         * would be prefixed first by errorPrefix.
         */
        public String actualErrorPrefix = "-->";

        /**
         * Represents the message that is sent when a command errors during argument processing by bad usage.
         */
        public String usageError = "Error in executing command.";

        /**
         * Represents the message that is sent in case of a permission error.
         */
        public String permissionError = "Permission required not granted.";

        /**
         * Represents the message that is sent in the event the user uses an invalid subcommand.
         */
        public String invalidSubcommandError = "Invalid subcommand. Subcommands are: ";

    }

}
