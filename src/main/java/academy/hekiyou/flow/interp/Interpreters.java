package academy.hekiyou.flow.interp;

import academy.hekiyou.flow.FlowException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
  * A class to maintain the registry and use of Interpreters.
  * Applications can call <code>Interpreters.register(Class, Interpreter)</code> to register
  * their own interpreters.
  */
public class Interpreters {

	private static final Map<Class<?>, Interpreter<?>> registered = new ConcurrentHashMap<>();

	private Interpreters(){
		throw new UnsupportedOperationException();
	}

	static { registerDefaults(); }

	/**
	 * Get the interpreter of the given class.
	 * @param klass The class to match
	 * @param <T> The return type of the interpreter
	 * @return The interpreter, possibly null.
	 */
	public static <T> Interpreter<T> of(Class<T> klass){
		return (Interpreter<T>)registered.get(klass);
	}

	/**
	 * Registers the class to the interpreter
	 * @param klass The class to register
	 * @param interpreter The matching interpreter
	 * @param <T> The type of <code>klass</code>.
	 */
	public static <T> void register(Class<T> klass, Interpreter<T> interpreter){
		if(registered.containsKey(klass)){
			throw new IllegalStateException("Cannot register " + klass + " (registered to " + interpreter + ")");
		}

		registered.put(klass, interpreter);
	}

	private static void registerDefaults(){
		register(String.class, s -> s);
		register(byte.class, FlowDefaults::toByte);
		register(short.class, FlowDefaults::toShort);
		register(char.class, FlowDefaults::toChar);
		register(int.class, FlowDefaults::toInt);
		register(float.class, FlowDefaults::toFloat);
		register(long.class, FlowDefaults::toLong);
		register(double.class, FlowDefaults::toDouble);
	}

	static class FlowDefaults {

	    private static final String NON_NUMERIC_INPUT_MESSAGE = "Non-numeric input: \"%s\"";

		private FlowDefaults(){
			throw new UnsupportedOperationException();
		}

		static byte toByte(String strByte){
	        try {
	            return Byte.parseByte(strByte);
	        } catch (NumberFormatException exc){
	            throw new FlowException(String.format(NON_NUMERIC_INPUT_MESSAGE, strByte), exc);
	        }
	    }

	    static short toShort(String strShort){
	        try {
	            return Short.parseShort(strShort);
	        } catch (NumberFormatException exc){
	            throw new FlowException(String.format(NON_NUMERIC_INPUT_MESSAGE, strShort), exc);
	        }
	    }

	    static char toChar(String strChar){
	        if(strChar.length() <= 0 || strChar.length() > 1){
	            throw new FlowException(String.format("Non-single-character input: \"%s\"", strChar));
	        }

	        return strChar.charAt(0);
	    }

	    static int toInt(String strInt){
	        try {
	            return Integer.parseInt(strInt);
	        } catch (NumberFormatException exc){
	            throw new FlowException(String.format(NON_NUMERIC_INPUT_MESSAGE, strInt), exc);
	        }
	    }

	    static float toFloat(String strFloat){
	        try {
	            return Float.parseFloat(strFloat);
	        } catch (NumberFormatException exc){
	            throw new FlowException(String.format(NON_NUMERIC_INPUT_MESSAGE, strFloat), exc);
	        }
	    }

	    static long toLong(String strLong){
	        try {
	            return Long.parseLong(strLong);
	        } catch (NumberFormatException exc){
	            throw new FlowException(String.format(NON_NUMERIC_INPUT_MESSAGE, strLong), exc);
	        }
	    }

	    static double toDouble(String strDouble){
	        try {
	            return Double.parseDouble(strDouble);
	        } catch (NumberFormatException exc){
	            throw new FlowException(String.format(NON_NUMERIC_INPUT_MESSAGE, strDouble), exc);
	        }
	    }
	}

}