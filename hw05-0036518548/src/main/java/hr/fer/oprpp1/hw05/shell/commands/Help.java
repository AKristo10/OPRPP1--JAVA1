package hr.fer.oprpp1.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
/**
 * Klasa koja predstavlja naredbu Help koja korisniku ispisuje sve moguce naredbe i njihov kratki opis
 * @author Andrea
 *
 */
public class Help implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
			ShellCommand[] commands = new ShellCommand[] { new Cat(), new Charsets(), new Copy(),
					new HexDump(), new Ls(), new Mkdir(),
					new Tree(), new Help()};
			List<ShellCommand> com = Arrays.asList(commands);
		if(arguments == null) {
			com.stream().forEach( a -> env.writeln(a.getCommandName()+ " "));
		}
		else {
			List<String> comNames = com.stream().map(c -> c.getCommandName()).collect(Collectors.toList());
			if(comNames.contains(arguments)) {
				int index = comNames.indexOf(arguments);
				
				List<String> description = com.get(index).getCommandDescription();
				description.stream().forEach(a -> env.writeln(a + " "));
			}
			else {
				env.writeln("That is not recognized as a command");
				return ShellStatus.CONTINUE;
			}
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "help";
	}
	public static void main(String[] args) {
		Help h  = new Help();
		h.executeCommand(null,null);
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		String s = "If started with no arguments, it lists names of all supported commands";
		list.add(s);
		s  = "If started with single argument, it must print name and the description of selected command "
				+ "(or print appropriate error message if no such command exists)";
		list.add(s);
		list = Collections.unmodifiableList(list);
		return list;
	}

}
