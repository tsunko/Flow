package ai.seitok.flow.gunvarrel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Loader {

    /**
     * Loads a class and then returns it wrapped inside of an Optional object
     * @param klass The class to load
     * @param <T> The type of the loading class
     * @return An instance of the class inside of an Optional
     */
    <T> Optional<T> loadClass(Class<T> klass);

    /**
     * Unloads a class, unregistering all of its commands.
     * @param klass The class to unload
     * @return A list (possibly empty) containing all commands unregistered.
     */
    List<String> unloadClass(Class<?> klass);

}
