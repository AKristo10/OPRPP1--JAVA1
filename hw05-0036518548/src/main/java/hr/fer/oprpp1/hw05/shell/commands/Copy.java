package hr.fer.oprpp1.hw05.shell.commands;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
/**
 * Klasa koja predstavlja naredbu copy koja kopira sadrzaj jedne datoteke u drugu.
 * @author Andrea
 *
 */
public class Copy implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments == null) {
			env.writeln("For this command you can have only one argument!");
			return ShellStatus.CONTINUE;
		}
		arguments = arguments.trim();
		String[] arrayArgs = new String[2];
		int currentIndex = 0;
		int i = 0;
		if (arguments.contains("\"")) {

			char[] data = arguments.toCharArray();
			while (currentIndex < data.length) {
				if(i > 1) {
					env.writeln("Number of arguments can be 2 or less than 2");
					return ShellStatus.CONTINUE;
				}
				currentIndex = skipBlanks(currentIndex, data);
				String str = new String();
				if (data[currentIndex] == '\"') {
					currentIndex += 1;
					while ((currentIndex < data.length) && (data[currentIndex] != '\"')) {
						str += String.valueOf(data[currentIndex]);
						currentIndex++;
					}
					currentIndex++;
					arrayArgs[i++] = str;
				} else {
					while ((currentIndex < data.length) && (data[currentIndex] != '\"')
							&& (data[currentIndex] != ' ')) {
						str += String.valueOf(data[currentIndex]);
						currentIndex++;
					}
					currentIndex++;
					arrayArgs[i++] = str;
				}
			}
		} else {
			arrayArgs = arguments.split(" ");
			if(arrayArgs.length != 2) {
				env.writeln("Number of arguments must be 2");
				return ShellStatus.CONTINUE;
			}		
		}
		Path src = Path.of(arrayArgs[0]);
		Path dest = Path.of(arrayArgs[1]);
		InputStream in;
		OutputStream out;
		System.out.println(src.getFileName() + " " + dest.getFileName());
		
		
			if (Files.isRegularFile(src)) {
				try {
					in = Files.newInputStream(src);
					if(Files.isDirectory(dest)) {
						out = Files.newOutputStream(Path.of(arrayArgs[1]  + "/" +  src.getFileName()));
						System.out.println(arrayArgs[1]  + "/" +  src.getFileName());
					}
					else
						out = Files.newOutputStream(dest);

					byte[] buffer = new byte[1024];
					int size;
					while ((size = in.read(buffer)) > 0) {
						out.write(buffer,0, size);
					}
					
				} catch (IOException e) {
					env.writeln("There is problem with copying files!");
					return ShellStatus.CONTINUE;
				}
			}
			else {
				env.writeln("Source file is not FILE");
				return ShellStatus.CONTINUE;
			}
			
		

		return ShellStatus.CONTINUE;
	}

	/**
	 * Metoda koja preskace praznine.
	 * 
	 * @param currentIndex
	 * @param data
	 */
	public int skipBlanks(int currentIndex, char[] data) {
		while (currentIndex < data.length) {
			if (data[currentIndex] == ' ' || data[currentIndex] == '\n' || data[currentIndex] == '\t'
					|| data[currentIndex] == '\r') {
				currentIndex++;
				continue;
			} else
				break;
		}
		return currentIndex;
	}

	public static void main(String[] args) {
		Copy c = new Copy();
		c.executeCommand(null, " C:/tmp/informacije.txt \"C:/Program Files/Program1/info.txt\"");
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("ana");
		list.add("ivana");
		return list;
	}

}
