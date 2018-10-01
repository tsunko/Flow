package academy.hekiyou.flow;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tags a method as being a "command method". Methods tagged as "FlowCommand" are loaded
 * via <code>Gunvarrel.load(TenkorePlugin plugin, Class klass)</code>.
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface FlowCommand {

    /**
     * @return The permission used to validate if the user can access this command
     */
    String permission() default "operator.only";

    /**
     * @return A simple description of what the command does
     */
    String description();

    /**
     * @return A usage line to specify how the command is used. Each argument should be an independent element.
     */
    String[] usage();

    /**
     * @return An array of aliases for this command
     */
    String[] alias() default {};

}
