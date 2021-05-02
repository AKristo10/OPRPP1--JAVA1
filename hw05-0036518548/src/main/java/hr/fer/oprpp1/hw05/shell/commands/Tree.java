package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
/**
 * Klasa koja predstavlja naredbu tree koja ispisuje stablo nekog direktorija.
 * @author Andrea
 *
 */
public class Tree implements ShellCommand {
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(arguments == null) {
			env.writeln("For this command you can have only one argument!");
			return ShellStatus.CONTINUE;
		}
		
		if (arguments.trim().startsWith("\"")) {
			char[] data = arguments.toCharArray();
			int currentIndex = 1;
			String pom = new String();
			boolean ponovnoNavodnik = false;
			while (currentIndex < data.length && data[currentIndex] != '\"') {
				pom += data[currentIndex++];
			}
			if(currentIndex < data.length && data[currentIndex] == '\"') {
				ponovnoNavodnik = true;
				currentIndex++;
			}
				
			//ako ne zavrsava s navodnikom greska je!
			if(!ponovnoNavodnik) {
				env.writeln("Syntax error. Incorrect input");
				return ShellStatus.CONTINUE;
			}
			
			if(currentIndex < data.length) {
				env.writeln("You can print tree only for one directory!");
				return ShellStatus.CONTINUE;
			}
			arguments = pom;
			
		}
		else if(arguments.split(" ").length > 1) {
			env.writeln("You can print tree only for one directory!");
			return ShellStatus.CONTINUE;
		}
		Path path = Path.of(arguments);
		if(!Files.exists(path)) {
			env.writeln(arguments + " does not exist.");
			return ShellStatus.CONTINUE;
		}
		MyFileVisitor visitor = new MyFileVisitor();
		try {
			Files.walkFileTree(path, visitor);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		Tree tree = new Tree();
		tree.executeCommand(null, "C:\\Users\\Andrea\\Desktop\\PROGI\\Nastava");

	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
