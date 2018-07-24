package org.kolonitsky.komander;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Collection of komand classes.
 *
 * @author Alexey Kolonitsky &lt;alexey.s.kolonitsky@gmailc.com>
 * @date 18.08.2017
 */
public class KomandCollection {

	/**
	 * List of all classes
	 */
	private ArrayList<Class<IKomand>> _komandClasses = new ArrayList<>();
	/**
	 *
	 */
	private HashMap<String, IKomand> _commandByNameMap = new HashMap<String, IKomand>();
	private ArrayList<IKomand> _commands = new ArrayList<>();
	private int _numCommand = 0;

	/**
	 * Register komand definition.
	 * @param classDefinition
	 *
	 * @return null in if komand can't be registered and instance of IKomand class
	 * if Komand has been successfuly registered.
	 */
	public IKomand register(Class classDefinition) {
		IKomand result = null;
		if (classDefinition == null) {
			return result;
		}
		if (_komandClasses.contains(classDefinition)) {
			KomanderOut.KomandDefinitionAlredyUsed(classDefinition);
			return result;
		}
		try {
			result = (IKomand) classDefinition.newInstance();
		} catch (Exception ex) {
			KomanderOut.KomandCantBeCreated(classDefinition);
		}
		String commandName = result.getName();
		if (commandName == null || commandName.isEmpty()) {
			KomanderOut.KomandNameNotFound(classDefinition);
			return null;
		} else if (registered(commandName)) {
			KomanderOut.KomandNameAlredyUsed(commandName);
			return null;
		} else {
			_komandClasses.add(classDefinition);
			_commandByNameMap.put(commandName, result);
			_commands.add(result);
			_numCommand++;
		}
		return result;
	}

	/**
	 * Check if command registered collection
	 * @param commandName
	 * @return
	 */
	public boolean registered(String commandName) {
		return _commandByNameMap.containsKey(commandName);
	}

	/**
	 * Get komand by name
	 * @param name
	 * @return
	 */
	public IKomand getKomandByName(String name) {
		if (registered(name)) {
			return _commandByNameMap.get(name);
		}
		return null;
	}

	/**
	 * Get komand by index in collection
	 * @param index
	 * @return
	 */
	public IKomand getKomandAt(int index) {
		return _commands.get(index);
	}

	/**
	 * Get number of registered commands
	 * @return
	 */
	public int size() {
		return _numCommand;
	}
}
