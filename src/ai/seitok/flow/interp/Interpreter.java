package ai.seitok.flow.interp;

import java.lang.FunctionalInterface;
import java.util.function.Function;

/**
  * A (Functional) Interface for lambdas/classes that handle the conversion of a String
  * type to given T type.
  */
@FunctionalInterface
public interface Interpreter<T> extends Function<String, T> {}