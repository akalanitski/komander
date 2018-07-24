package org.kolonitsky.komander;

/**
 * Created by akalanitski on 18.08.2017.
 */
public class KomanderFactory {

	private KomandCollection _commands;
	private DependencyCollection _dependencies;

	public KomanderFactory(KomandCollection commands, DependencyCollection dependecies) {
		_commands = commands;
		_dependencies = dependecies;
	}

	public IKomand create(String name)  {
		if (_commands.registered(name)) {
			IKomand command = _commands.getKomandByName(name);
			return command;
		}
		else {
			System.out.println("ERROR: unknown command name " + name);
			return null;
		}
	}

}
