package com.kolonitsky.komander;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Komander facade class which agregate all configuration of the application
 * commands
 *
 *
 * @author Alexey Kolonitsky &lt;alexey.s.kolonitsky@gmail.com&gt;
 */
public class Komander {

	KomandCollection _komands;
	DependencyCollection _dependencies;

	public Komander() {
		_komands = new KomandCollection();
		_dependencies = new DependencyCollection();
		_dependencies.addInstance("commands", _komands);
		_komands.register(HelpKomand.class);
	}


	//--------------------------------------------------------------------------
	// Wrap KomandCollection API
	//--------------------------------------------------------------------------

	public IKomand register(Class classDefinition) {
		return _komands.register(classDefinition);
	}


	//--------------------------------------------------------------------------
	// Wrap DependencyCollection API
	//--------------------------------------------------------------------------

	public void addConfigurator(Configurator configurator) {
		_dependencies.addConfigurator(configurator);
	}

	public void addInstance(String key, Object instance) {
		_dependencies.addInstance(key, instance);
	}

	public boolean hasDependency(String key) {
		return _dependencies.hasDependency(key);
	}

	public DependencyDefinition getDependency(String key) {
		return _dependencies.getDependency(key);
	}



	public void run(String name, String[] arguments) {
		IKomand cmd = null;
		if (_komands.registered(name)) {
			cmd = _komands.getKomandByName(name);
		} else {
			KomanderOut.UnknownKomand(name);
			cmd = _komands.getKomandByName("help");
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

				if (hasDependency(dependencyId)) {
					DependencyDefinition definition = getDependency(dependencyId);
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
