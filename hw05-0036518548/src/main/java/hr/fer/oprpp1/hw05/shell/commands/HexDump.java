package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Klasa koja prima datoteku i prozivodi heksadekadski ispis te datoteke.
 * 
 * @author Andrea
 *
 */
public class HexDump implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments == null) {
			env.writeln("For this command you can have only one argument!");
			return ShellStatus.CONTINUE;
		}
		// upravljaj brojem argumenata
		if (arguments.trim().startsWith("\"")) {
			char[] data = arguments.toCharArray();
			int currentIndex = 1;
			String pom = new String();
			boolean ponovnoNavodnik = false;
			while (currentIndex < data.length && data[currentIndex] != '\"') {
				pom += data[currentIndex++];
			}
			if (currentIndex < data.length && data[currentIndex] == '\"') {
				ponovnoNavodnik = true;
				currentIndex++;
				if ((currentIndex < data.length) && data[currentIndex] != ' ') {
					env.writeln("Syntax error. Incorrect input.");
					return ShellStatus.CONTINUE;
				}
			}

			// ako ne zavrsava s navodnikom greska je!
			if (!ponovnoNavodnik) {
				env.writeln("Syntax error. Incorrect input.");
				return ShellStatus.CONTINUE;
			}

			if (currentIndex < data.length) {
				env.writeln("For this command you can have only one argument!");
				return ShellStatus.CONTINUE;
			}
			arguments = pom;
		}

		else if (arguments.split(" ").length > 1) {
			env.writeln("You can hexdump only one file!");
			return ShellStatus.CONTINUE;
		}
		// pomocno polje koje ce opet procitat i nadopunit ako nema dovoljno
		// provjeri je li file u pitanju
		Path path = Path.of(arguments);
		if (Files.exists(path)) {
			try {
				
				InputStream is = Files.newInputStream(path);
				int size = 0;
				byte[] read = new byte[16];
				List<String> asciiList = new ArrayList<String>();
				String line = new String();
				List<String> ispis = new ArrayList<String>();
				int numberOfbyte = 0;
				byte[] one = new byte[1];
				
				
				while ((size = is.read(read)) > 0) {
					line="";
					// prvi stupac
					String numOfb = Integer.toHexString(numberOfbyte);
					
					if(numOfb.length() < 8) {
						String space = "";
						for(int i = numOfb.length(); i < 8; i++) {
							space += "0";
						}
						numOfb = space + numOfb;
					}
					line += numOfb + ": ";
					
					// drugi stupac
					if (size < 16) {
						while (is.read(one) > 0) {
							read[size++] = one[0];
							if (size == 16)
								break;
						}
					}
					
					int index = 0;
					for (byte b : read) {
						int number = (int) b;
						String hex = "";
						if(index < size) {
							hex = Integer.toHexString(number);
						}
						else if(index >= size) {
							hex = "  ";
						}
						String ascii;
						
						if((number < 32 || number> 127) && index <size) {
							ascii = String.valueOf('.');
						}
						else if(index >= size) {
							ascii = String.valueOf(' ');
						}
						
						else
							ascii = String.valueOf((char) number);
						
						asciiList.add(ascii);
						if (hex.length() == 1) {
							hex = "0" + hex;
						}
						else if(hex.length() > 2) {
							hex = hex.substring(hex.length()-2, hex.length());
						}
						
						if(index == 7) {
							line +=  hex + "|";
						}
						else {
							line += hex + " ";
						}
							
						index ++;
					}
					line += " | ";
					for (String s : asciiList) {
						line += s;
					}
					
					numberOfbyte += 16;
					ispis.add(line);
					asciiList.clear();
					index ++;
				}
				
				
				for(String s : ispis) {
					env.writeln(s);
				}
				
			} catch (IOException e) {
				env.writeln("There is problem when reading file!");
				return ShellStatus.CONTINUE;
			}

		} else {
			env.writeln("This file does not exists.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	public static void main(String[] args) {
		System.out.println(Integer.toHexString(16));
		System.out.println(String.valueOf((char) 65));
		System.out.println(Character.valueOf('a').SIZE);
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		String s = "This command expects a single argument: file name.";
		list.add(s);
		s = "This command produces hex-output of that file";
		list = Collections.unmodifiableList(list);
		return list;
	}

}
