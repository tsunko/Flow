package ai.seitok.flow.env;

/**
 * Classes implementing Wrapper, obviously, wrap around an object.
 * @param <U>
 */
public interface Wrapper<U> {

    /**
     * Gets the object that this object wraps around
     * @return the wrapped object
     */
    public U getRaw();

}
