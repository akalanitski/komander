package org.kolonitsky.komander;

/**
 * Sample command, which prints list of all registered commands. It also could
 * print detailed ussage of the selected command, basend in it's input definition.
 * This command available in all applications.
 *
 * @author Alexey Kolonitsky &lt;alexey.s.kolonitsky@gmail.com&gt;
 * @since 20.04.2018
 */
public class HelpKomand extends BaseKomand {

	@Injected
	public KomandCollection commands;

	public HelpKomand() {
		_name = "help";
		_category = "info";
		_shortDescription = "display this list of options";
	}

	@Override
	public void run(String[] arguments) {
		String result = help();
		println(result, null);
	}

	private String help() {
		String result = _applicationTitle;
		result += "\n  Usage: " + _applicationName + " [command] [options]";

		Input input = getInput();
		if (input.hasParameter("command")) {
			String command = input.getParameter("command");
			IKomand cmd = commands.getKomandByName(command);
			return printCommandDetails(cmd);
		} else {
			return printAllCommands();
		}
	}

	private String printCommandDetails(IKomand cmd) {
		String result = "";
		result += cmd.getUsage();
		return result;
	}

	private String printAllCommands() {
		String result = "";
		String category = "";
		for (int i = 0; i < commands.size(); i++) {
			IKomand command = commands.getKomandAt(i);
			boolean isNewCategory = !category.equals(command.getCategory());
			if (isNewCategory) {
				category = command.getCategory();
				result += "\n  " + category;
			}
			result += "\n    " + String.format("%-21s : %s", command.getName(), command.getShortDescription());
		}

		return result;
	}
}
