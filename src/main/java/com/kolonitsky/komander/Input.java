package com.kolonitsky.komander;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Input class contains all definitions of the single command input parameters.
 * Process array of program arguments and store prodessing result.
 *
 * @author Alexey Kolonitsky &lt;alexey.s.kolonitsky@gmail.com&gt;
 * @since 02.07.2018
 */
public class Input {

	//--------------------------------------------------------------------------
	// Flags
	//--------------------------------------------------------------------------

	public ArrayList<String> flags;
	public ArrayList<InputFlag> flagsDefinition = new ArrayList<InputFlag>();

	public boolean hasFlag(String name) {
		if (flags == null)
			return false;
		return flags.indexOf(name) != -1;
	}

	public void setFlag(String name) throws KomanderException {
		updateFlag(name, true);
	}

	public void clearFlag(String name) throws KomanderException {
		updateFlag(name, false);
	}

	private void updateFlag(String name, boolean value) throws KomanderException {
		if (hasFlagDefinition(name)) {
			if (flags == null) flags = new ArrayList<>();
			boolean flag = hasFlag(name);
			if (value == false && flag) {
				flags.remove(name);
			} else if (value == true && !flag) {
				flags.add(name);
			}
		} else {
			throw KomanderException.UndefinedFlag(name);
		}
	}

	/**
	 * Add new flag definition
	 * @param name flag name with all required prefix like "/" (for windows) or
	 * "-" for linux systems.
	 * @param description text to show in help command near to flag name
	 * @throws KomanderException if method invoked twice with the same name
	 * paramter
	 */
	public void addFlagDefinition(String name, String description) throws KomanderException {
		if (!hasFlagDefinition(name)) {
			InputFlag flag = new InputFlag();
			flag.key = name;
			flag.description = description;
			flagsDefinition.add(flag);
		} else {
			throw KomanderException.DublicateFlagDefinition(name);
		}
	}

	/**
	 * Check flag definition
	 * @param name flag name
	 * @return true if flag was registered
	 */
	public boolean hasFlagDefinition(String name) {
		return getFlagDefinition(name) != null;
	}

	/**
	 * Find flag definition by id
	 * @param name flag name
	 * @return InputFlag object with flag definition if flag was registered,
	 * otherwise return null
	 */
	public InputFlag getFlagDefinition(String name) {
		if (name != null && !name.isEmpty()) {
			for (int i = 0; i < flagsDefinition.size(); i++) {
				InputFlag flag = flagsDefinition.get(i);
				if (flag != null && flag.key.equals(name))
					return flag;
			}
		}
		return null;
	}


	//--------------------------------------------------------------------------
	// Parameters
	//--------------------------------------------------------------------------

	public ArrayList<InputParameter> parametersDefinition = new ArrayList<>();
	public Map<String, String> parameters;

	public void addParameterDefinition(String name, String defaultValue, String description, String prefix) throws KomanderException {
		if (!hasParameterDefinition(name)) {
			InputParameter param = new InputParameter();
			param.prefix = prefix;
			param.name = name;
			param.defaultValue = defaultValue;
			param.description = description;
			parametersDefinition.add(param);
		} else {
			throw KomanderException.DuplicateParameterDefinition(name);
		}
	}

	public boolean hasParameterDefinition(String name) {
		return getParameterDefinition(name) != null;
	}

	public InputParameter getParameterDefinition(String name) {
		if (name != null && !name.isEmpty()) {
			for (int i = 0; i < parametersDefinition.size(); i++) {
				InputParameter parameter = parametersDefinition.get(i);
				if (parameter != null && parameter.name.equals(name))
					return parameter;
			}
		}
		return null;
	}

	public InputParameter getParameterByPrefix(String prefix) {
		if (prefix != null && !prefix.isEmpty()) {
			for (int i = 0; i < parametersDefinition.size(); i++) {
				InputParameter parameter = parametersDefinition.get(i);
				if (prefix.equals(parameter.prefix)) {
					return parameter;
				}
			}
		}
		return null;
	}

	public boolean hasParameterPrefix(String prefix) {
		return getParameterByPrefix(prefix) != null;
	}

	public boolean hasParameter(String name) {
		if (name != null && !name.isEmpty())
			return parameters.containsKey(name);
		return false;
	}

	public String getParameter(String name) {
		if (hasParameter(name))
			return parameters.get(name);
		InputParameter definition = getParameterDefinition(name);
		if (definition != null)
			return definition.defaultValue;
		return null;
	}


	//--------------------------------------------------------------------------
	// Processing arguments
	//--------------------------------------------------------------------------

	private String[] _arguments = null;

	public void parseArguments(String[] args) throws Exception {
		_arguments = args;
		flags = new ArrayList<>();
		parameters = new HashMap<String, String>();
		for (int i = 0; i < _arguments.length; ) {
			String arg = _arguments[i];
			if (hasFlagDefinition(arg)) {
				if (hasFlag(arg)) {
					KomanderOut.InputFlagDuplicated(arg);
				} else {
					flags.add(arg);
				}
				i += 1;
			} else if (hasParameterPrefix(arg)) {
				InputParameter parameter = getParameterByPrefix(arg);
				parameters.put(parameter.name, _arguments[i + 1]);
				i += 2;
			} else if (parametersDefinition.size() > 0) {
				InputParameter def = getFirstUndefinedParameter();
				if (def == null) {
					KomanderOut.InvalidArgument(args, arg, documentationString());
					return;
				}
				parameters.put(def.name, arg);
				i += 1;
			} else {
				KomanderOut.InvalidArgument(args, arg, documentationString());
				return;
			}
		}
	}

	public InputParameter getFirstUndefinedParameter() {
		for (InputParameter def : parametersDefinition) {
			boolean hasPrefix = def.prefix != null && !def.prefix.isEmpty();
			if (!hasPrefix && !hasParameter(def.name)) {
				return def;
			}
		}
		return null;
	}

	/**
	 * Get processed arguments
	 * @return one line with all arguments passed into the `parsArguments` method.
	 */
	public String rawInputLine() {
		return StringUtils.join(_arguments, " ");
	}

	/**
	 * Get komand input declaration string.
	 * @return one line definitions with parameters names and flags
	 */
	public String definitionString() {
		String result = "";
		for (int i = 0; i < parametersDefinition.size(); i++) {
			InputParameter parameter = parametersDefinition.get(i);
			String strParameter = "";
			if (parameter.prefix != null && !parameter.prefix.isEmpty()) {
				strParameter = parameter.prefix + " ";
			}
			strParameter += "<" + parameter.name + ">";
			if (parameter.defaultValue != null) {
				strParameter = "[" + strParameter + "]";
 			}
			result += strParameter + " ";
		}
		for (int i = 0; i < flagsDefinition.size(); i++) {
			InputFlag flag = flagsDefinition.get(i);
			result += "[" + flag.key + "] ";
		}
		return result;
	}

	/**
	 * Get komand input declaration string.
	 * @return full definition aff input with description near each parameter
	 * and flag.
	 */
	public String documentationString() {
		String result = definitionString();
		result += "\nWHERE:";
		for (int i = 0; i < parametersDefinition.size(); i++) {
			InputParameter parameter = parametersDefinition.get(i);
			String strParameter = "";
			if (parameter.prefix != null && !parameter.prefix.isEmpty()) {
				strParameter = parameter.prefix + " ";
			}
			strParameter += "<" + parameter.name + ">";
			result += String.format("\n\t%16s - ", strParameter);
			result += parameter.defaultValue != null ? String.format("(default value = %s)", parameter.defaultValue) : "";
			result += parameter.description;
		}
		if (flagsDefinition.size() > 0) {
			result += "\nFlags:";
			for (int i = 0; i < flagsDefinition.size(); i++) {
				InputFlag flag = flagsDefinition.get(i);
				result += String.format("\n\t%16s - %s", flag.key, flag.description);
			}
		}
		return result;
	}

}
