 package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
/**
 * Klasa koja predstavlja naredbu mkdir koja pravi novi direktorij.
 * @author Andrea
 *
 */
public class Mkdir implements ShellCommand {

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
				if((currentIndex < data.length) && data[currentIndex] != ' ') {
					env.writeln("Syntax error. Incorrect input.");
					return ShellStatus.CONTINUE;
				}
			}
				
			//ako ne zavrsava s navodnikom greska je!
			if(!ponovnoNavodnik) {
				env.writeln("Syntax error. Incorrect input.");
				return ShellStatus.CONTINUE;
			}
			
			if(currentIndex < data.length) {
				env.writeln("For this command you can have only one argument!");
				return ShellStatus.CONTINUE;
			}
			arguments = pom;
			
		}
		else if(arguments.split(" ").length > 1) {
			env.writeln("You can create only one directory!");
			return ShellStatus.CONTINUE;
		}
		try {
			if(!Files.exists(Path.of(arguments)))
				Files.createDirectory(Path.of(arguments));
			else {
				env.writeln("This file already exists!");
			}
				
		} catch (IOException e) {
			env.writeln("An error occurs during creating file!");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		String s = "Creates a directory";
		list.add(s);
		list = Collections.unmodifiableList(list);
		return list;
	}

}
