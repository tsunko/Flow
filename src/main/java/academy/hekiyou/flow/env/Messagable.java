package academy.hekiyou.flow.env;

public interface Messagable {

    /**
     * Sends a message to the Messagable object
     * @param message The message to send
     */
    void sendMessage(String message);

    /**
     * Sends a formatted message to the Messagable object.
     * This is the same as calling <code>sendMessage(String.format(fmt, args))</code>.
     * @param fmt The format to use
     * @param args The arguments to format with
     */
    default void sendMessage(String fmt, Object... args){
        sendMessage(String.format(fmt, args));
    }

}
