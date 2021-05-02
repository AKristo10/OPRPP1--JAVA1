package hr.fer.oprpp1.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
/**
 * Klasa koja predstavlja naredbu exit odnosno izlazak iz terminala.
 * @author Andrea
 *
 */
public class Exit implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments != null) {
			env.writeln("This method has no arguments!");
			return ShellStatus.CONTINUE;
		}
			
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "exit";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		String s = "Terminate shell.";
		list.add(s);
		list = Collections.unmodifiableList(list);
		return list;
	}

}
