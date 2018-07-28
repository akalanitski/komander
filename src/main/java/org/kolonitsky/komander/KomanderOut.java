package org.kolonitsky.komander;

/**
 * The single class for definition of all Komander output messages. This is the
 * best place to localize Komander.
 *
 * @author Alexey Kolonitsky &lt;alexey.s.kolonitsky@gmail.com>
 * @since 22.07.2018.
 */
public class KomanderOut {

	public static void IncorrectInput(IKomand kom) {
		out("ERROR: Komander. Incorrect input\nPlease see command usage: %s", kom.getUsage());
	}
	public static void FinishedWithException(IKomand kom, Exception exception) {
		out("ERROR: Komander. `%s` failed.\n%s", kom.getName(), exception.getMessage());
		exception.printStackTrace();
	}
	public static void DependencySetException(String dependencyId) {
		out("ERROR: Dependency with id `%s' cant be set. ", dependencyId);
	}
	public static void DependencyNotFound(String dependencyId) {
		out("ERROR: Dependency with id: '%s' not found", dependencyId);
	}
	public static void KomandNameNotFound(Class classDefinition) {
		out("ERROR: Komand '%s' doesn't have name. Please specify name of command.", classDefinition.getCanonicalName());
	}
	public static void KomandDefinitionAlredyUsed(Class classDefinition) {
		out("WARNING: Komand with name '%s' already registered in collection", classDefinition.getCanonicalName());
	}
	public static void KomandNameAlredyUsed(String commandName) {
		out("ERROR: Komand with name '%s' already registered.", commandName);
	}
	public static void KomandCantBeCreated(Class classDefinition) {
		out("ERROR: Komand %s can't instantiate command ", classDefinition.getCanonicalName());
	}
	public static void InvalidArgument(String argument, String ussage) {
		out("ERROR: Wrong argument %s. Ussage:\n ", argument);
		out("USAGE: " + ussage);
	}
	public static void InputFlagDuplicated(String argument) {
		out("WARNING: Flag `%s` mentioned twice. The second one will be ignored.", argument);
	}
	public static void UnknownKomand(String name) {
		out("ERROR: Unknown command %s, please look at usage", name);
	}

	private static void out(String message, Object... args) {
		System.out.println(String.format(message, args));
	}
}
