package com.versionone.apiclient;

import com.versionone.util.Delegator;
import com.versionone.util.IDelegate;

import java.util.List;

/**
 * Static methods for text manipulation.
 *
 * @author jerry
 *
 */
public class TextBuilder {
	@SuppressWarnings("unchecked")
	private static final Class[] PARAMETERS = new Class[]{Object.class};

	public static final Delegator STRINGIZER_DELEGATE = new Delegator(PARAMETERS,String.class);

	/**
	 * Default Delegate used by TextBuilder.  Calls objects toString method
	 * @author jerry
	 *
	 */
	public static class DefaultStringize {
		/**
		 * Convert object to string by calling toString
		 * @param value - object to convert
		 * @return result of calling value.toString()
		 */
		public static String toString(Object value) {
            return value == null ? "" : value.toString();
        }
	}

	/**
	 * Split a string at the first occurrence of a character.
	 * for example:
	 * foo:bar split at : would return foo as the prefix and bar as the suffix.  The : is not returned.
	 *
	 * Used mainly for parsing tokens
	 *
	 * @param source - string to split
	 * @param separator - character for split
	 * @param prefix - string before the separator
	 * @param suffix - string after the separator
	 */
	public static void splitPrefix(String source, char separator, StringBuffer prefix, StringBuffer suffix) {
		String sep = separator == '.' ? "\\." : String.valueOf(separator);
		String[] parts = source.split(sep, 2);

        if (parts.length == 1) {
			prefix.replace(0, prefix.length(), "");
			suffix.replace(0, suffix.length(), parts[0]);
		} else {
			prefix.replace(0, prefix.length(), parts[0]);
			suffix.replace(0, suffix.length(), parts[1]);
		}
	}

	/**
	 * Join an array of objects using their "toString" method. Each object is separated by the separator character
	 * <br/><br/>Example:<br/><br/>
	 * <code>
	 * String[] input = {"the", "join", "example"};
	 * String result = TextBuilder.join(input, ":");
	 * </code>
	 * <br/><br/>
	 * Result is "the:join:example"
	 * @param pathParts - objects to join
	 * @param seperator - character used to separate objects
	 * @return String
	 */
	public static String join(Object[] pathParts, String seperator) {
		return join(pathParts, seperator, TextBuilder.STRINGIZER_DELEGATE.build(TextBuilder.DefaultStringize.class, "toString"));
	}

	/**
	 * Join an array of objects using a specific method common to all objects
	 *
	 * @param pathParts - objects to join
	 * @param seperator - character to separate object
	 * @param stringize - Method on objects to obtain string. All objects in list must declare this method as public.
	 *
	 * @return String
	 */
	public static String join(Object[] pathParts, String seperator, IDelegate stringize) {
		StringBuffer rc = new StringBuffer();
		boolean useSeperator = false;

        for(Object value : pathParts) {
			if(useSeperator) {
				rc.append(seperator);
            } else {
				useSeperator = true;
            }

			try {
				rc.append(stringize.invoke(value));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return rc.toString();
	}

	/**
	 * Join with a list using "toString" method of each object in the list
	 * @see #join(Object[], String)
	 * @param pathParts - list of objects to join
	 * @param seperator - character used to separate each object
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public static String join(List pathParts, String seperator) {
		return join(pathParts.toArray(), seperator);
	}

	/**
	 * Join with a list using specific method to join
	 *
	 * @see #join(Object[], String, IDelegate)
	 * @param pathParts - List of objects to join
	 * @param seperator - character used to separate each object
	 * @param stringize - method on object.  All objects in list must declare this method as public.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String join(List pathParts, String seperator, IDelegate stringize) {
		return join(pathParts.toArray(), seperator, stringize);
	}
}