package org.kolonitsky.komander;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by akalanitski on 07.08.2017.
 */
public class Komander {

	private KomandCollection _commands;
	private DependencyCollection _dependencies;
	private KomanderFactory _factory;

	public Komander(KomandCollection commands, DependencyCollection dependencies) {
		_commands = commands;
		_dependencies = dependencies;
		_factory = new KomanderFactory(commands, dependencies);
	}

	public void run(String name, String[] arguments) {
		IKomand cmd = _factory.create(name);
		if (cmd == null) {
			cmd = _factory.create("help");
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
