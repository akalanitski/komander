package com.kolonitsky.komander;

import java.text.MessageFormat;

/**
 * Basic implementation of IKomand interface.
 *
 * @author Alexey Kolonitsky &lt;alexey.s.kolonitsky@gmail.com&gt;
 * @since 26.04.2018
 */
public class BaseKomand implements IKomand {

	protected String _applicationName = "";
	protected String _applicationTitle = "";

	//---------------------------------
	// category
	//---------------------------------

	protected String _category = "";

	@Override
	public String getCategory() {
		return _category;
	}


	//---------------------------------
	// short description
	//---------------------------------

	protected String _shortDescription;

	@Override
	public String getShortDescription() {
		return _shortDescription;
	}


	//---------------------------------
	// short description
	//---------------------------------

	protected String _fullDescription;

	@Override
	public String getFullDescription() {
		return _fullDescription;
	}


	//---------------------------------
	// name
	//---------------------------------

	protected String _name;

	public String getName() {
		return _name;
	}


	//---------------------------------
	// usage
	//---------------------------------

	protected String _usage;

	@Override
	public String getUsage() {
		return _input.definitionString();
	}


	//---------------------------------
	// output
	//---------------------------------

	protected String _output;

	public String getOutput() {
		return _output;
	}


	//---------------------------------
	// Constructor
	//---------------------------------

	public BaseKomand() {
		_output = "";
		_input = new Input();
		_usage = _applicationName + " " + _name;
	}


	//---------------------------------
	// Input
	//---------------------------------
	protected Input _input;

	public Input getInput() {
		return _input;
	}

	@Override
	public IKomand inputOptionalParameter(String name, String value, String description, String prefix) throws Exception {
		_input.addParameterDefinition(name, value, description, prefix);
		return this;
	}

	@Override
	public IKomand inputMandatoryParameter(String name, String value, String description, String prefix) throws Exception {
		_input.addParameterDefinition(name, value, description, prefix);
		return this;
	}

	@Override
	public IKomand inputFlag(String flag, String description) throws Exception {
		_input.addFlagDefinition(flag, description);
		return this;
	}

	@Override
	public void parseInput(String[] arguments) throws Exception {
		_input.parseArguments(arguments);
	}

	@Override
	public boolean validateInput(String[] arguments) {
//		_input.parseArguments(arguments);
		return false;
	}

	@Override
	public void run(String[] arguments) throws Exception {
		println("{0}: Internal error. Command ''{1}'' is not implemented", _applicationName, _name);
	}


	public void internalError(String message, Exception exception) {
		String str = "Internal error: " + message + exception.toString();
		_output += str + "\n";
		println(str, null);
	}

	public void wrongFormatError() {
		String message = MessageFormat.format("{0} {1} Wrong format: ", _applicationName, _name);
		_output += message + "\n";
		System.out.println(message);
	}

	public void println(String message, Object... params) {
		MessageFormat temp = new MessageFormat(message);
		message = temp.format(params);
		_output += message + "\n";
		System.out.println(message);
	}
}
