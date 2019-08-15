package com.kolonitsky.komander;

/**
 * Komand Kommand interface.
 *
 * @author Alexey Kolonitsky &lt;alexey.s.kolonitsky@gmail.com&gt;
 * @since 26.04.2018
 */
public interface IKomand {

	/**
	 * Execute command
	 * @param arguments arguments to run command
	 * @throws Exception
	 */
	void run(String[] arguments) throws Exception;

	String getCategory();
	String getShortDescription();
	String getFullDescription();
	String getUsage();
	String getName();
	Input getInput();
	void parseInput(String[] arguments) throws Exception;
	IKomand inputOptionalParameter(String name, String defaultValue, String description, String prefix) throws Exception;
	IKomand inputMandatoryParameter(String name, String defaultValue, String description, String prefix) throws Exception;
	IKomand inputFlag(String flag, String description) throws Exception;
	boolean validateInput(String[] arguments);
}
