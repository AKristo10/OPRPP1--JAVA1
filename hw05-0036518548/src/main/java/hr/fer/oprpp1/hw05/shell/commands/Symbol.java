package hr.fer.oprpp1.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
/**
 * Klasa koja predstavlja naredbu symbol koja mijenja simbole za PROMPT, MORELINES i MULTILINES
 * @author Andrea
 *
 */
public class Symbol implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments == null) {
			env.writeln("For this command you can have only one argument!");
			return ShellStatus.CONTINUE;
		}
		String[] args = arguments.split(" ");
		if (args.length == 1) {
			if (args[0].toUpperCase().equals("PROMPT"))
				env.writeln("Symbol for PROPMT is " + env.getPromptSymbol());
			else if (args[0].toUpperCase().equals("MORELINES"))
				env.writeln("Symbol for MORELINES is " + env.getMorelinesSymbol());
			else if (args[0].toUpperCase().equals("MULTILINES"))
				env.writeln("Symbol for MULTINES is " + env.getMultilineSymbol());
			else {
				env.writeln("That symbol does not exists");
				return ShellStatus.CONTINUE;
			}
		} else if (args.length == 2) {
			if (args[0].toUpperCase().equals("PROMPT")) {
				env.writeln("Symbol for PROPMT is changed from " + env.getPromptSymbol() + " to " + args[1]);
				env.setPromptSymbol(args[1].charAt(0));
				
			} else if (args[0].toUpperCase().equals("MORELINES")) {
				env.writeln("Symbol for MORELINES is changed from " + env.getPromptSymbol() + " to " + args[1]);
				env.setMorelinesSymbol(args[1].charAt(0));
			}
			else if (args[0].toUpperCase().equals("MULTILINES")) {
				env.writeln("Symbol for MULTILINES is changed from " + env.getPromptSymbol() + " to " + args[1]);
				env.setMultilineSymbol(args[1].charAt(0));
			}

		}
		else {
			env.writeln("You can not have 3 arguments for symbol command! ");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		String s = "Change prompt, morelines or multiline symbol!";
		list.add(s);
		s = "Print prompt, morelines or multiline symbol!";
		list = Collections.unmodifiableList(list);
		return list;
	}

}
