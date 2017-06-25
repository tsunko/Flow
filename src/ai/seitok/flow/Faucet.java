package ai.seitok.flow;

import ai.seitok.flow.env.Invoker;
import ai.seitok.flow.gunvarrel.Gunvarrel;
import ai.seitok.flow.gunvarrel.Registerer;
import ai.seitok.flow.gunvarrel.SimpleRegisterer;

import java.util.List;
import java.util.Optional;

/**
 * Controls integration within an application.
 */
public final class Faucet {

    private static final Faucet instance = new Faucet();

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
        return instance.loader.loadClass(klass);
    }

    /**
     * Unloads a class and its (loaded) commands.
     * @param klass The class to unload
     * @return A list containing the commands' name that were unloaded.
     */
    public static List<String> unloadClass(Class<?> klass){
        return instance.loader.unloadClass(klass);
    }

    /**
     * The main method to be called for when a command is detected and needs to be invoked.
     * Simply call this in place for where normal command look-up and execution logic would be.
     * @param command The command name to invoke
     * @param invoker An instance/implementation of Invoker
     * @param flow An instance/implementation of Flow
     * @return <code>true</code> if the command was found and processed successfully, <code>false</code> otherwise
     */
    public static boolean process(String command, Invoker invoker, Flow flow){
        return instance.loader.findAndExecute(command, invoker, flow);
    }

}
