package org.kolonitsky.komander;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Komander facade class which agregate all configuration of the application
 * commands
 *
 * @author Alexey Kolonitsky &lt;alexey.s.kolonitsky@gmail.com&gt;
 * @since 22.07.2018.
 */
public class Komander {

	private KomandCollection _commands;
	private DependencyCollection _dependencies;

	public Komander(KomandCollection commands, DependencyCollection dependencies) {
		_commands = commands;
		_dependencies = dependencies;
	}

	public void run(String name, String[] arguments) {
		IKomand cmd = null;
		if (_commands.registered(name)) {
			cmd = _commands.getKomandByName(name);
		} else {
			KomanderOut.UnknownKomand(name);
			cmd = _commands.getKomandByName("help");
		}

		try {
			cmd.parseInput(arguments);
		} catch (Exception ex) {
			KomanderOut.IncorrectInput(cmd);
		}
		injectDependencies(cmd);

		try {
			cmd.run(arguments);
		} catch (Exception exception) {
			KomanderOut.FinishedWithException(cmd, exception);
		}
	}

	public void injectDependencies(IKomand command) {
		Field[] fields = command.getClass().getDeclaredFields();
		if (fields == null || fields.length == 0)
			return;

		for (Field field : fields) {
			if (field.isAnnotationPresent(Injected.class)) {
				Annotation annotation = field.getAnnotation(Injected.class);
				Injected injected = (Injected) annotation;
				String dependencyId  = field.getName();
				if (!injected.config().equals(""))
					dependencyId = injected.config();

				if (_dependencies.hasDependency(dependencyId)) {
					DependencyDefinition definition = _dependencies.getDependency(dependencyId);
					try {
						field.set(command, definition.instance);
					}
					catch (IllegalAccessException ex) {
						KomanderOut.DependencySetException(dependencyId);
					}
 				}
				else {
					KomanderOut.DependencyNotFound(dependencyId);
				}
			}

		}
	}
}
