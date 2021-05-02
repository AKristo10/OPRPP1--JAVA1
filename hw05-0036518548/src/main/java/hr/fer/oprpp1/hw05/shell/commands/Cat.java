package hr.fer.oprpp1.hw05.shell.commands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
/**
 * Klasa koja predstavlja naredbu cat.
 * @author Andrea
 *
 */
public class Cat implements ShellCommand {

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
		String charset = Charset.defaultCharset().name();
		
		if (arguments.contains("\"")) {
			
			char[] data = arguments.toCharArray();
			while (currentIndex < data.length) {
				if(i>1) {
					env.writeln("Number of arguments can be 2 or less than 2");
					return ShellStatus.CONTINUE;
				}
				currentIndex = skipBlanks(currentIndex, data);
				String str = new String();
				if (data[currentIndex] == '\"') {
					currentIndex += 1;
					boolean ponovnoNavodnik = false;
					while ((currentIndex < data.length) && (data[currentIndex] != '\"')) {
						str += String.valueOf(data[currentIndex]);
						currentIndex++;
					}
					if(currentIndex < data.length && data[currentIndex] == '\"') {
						ponovnoNavodnik = true;
						currentIndex++;
						if((currentIndex < data.length) && data[currentIndex] != ' ') {
							env.writeln("Syntax error. Incorrect input.");
							return ShellStatus.CONTINUE;
						}
					}
					if(!ponovnoNavodnik) {
						env.writeln("Syntax error. Incorrect input.");
						return ShellStatus.CONTINUE;
					}
					
					currentIndex++;
					arrayArgs[i++]=str;
				}
				else {
					while ((currentIndex < data.length) && (data[currentIndex] != '\"') && (data[currentIndex] != ' ')) {
						str += String.valueOf(data[currentIndex]);
						currentIndex++;
					}
					arrayArgs[i++] = str;
				}
			}
		} else {
			arrayArgs = arguments.split(" ");
		}
		if(arrayArgs.length > 2) {
			env.writeln("Number of arguments can be 2 or less than 2");
			return ShellStatus.CONTINUE;
		}
		
		if(arrayArgs[1] != null)
			charset = arrayArgs[1];
		
		try {
			String line = new String();
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(arrayArgs[0]), charset));
			while((line = reader.readLine()) != null){
				env.writeln(line);
			}
			reader.close();
		} catch (Exception e) {
			env.writeln("There is problem with reading/writing files.");
			return ShellStatus.CONTINUE;
		} 
			
		return ShellStatus.CONTINUE;
	}
	
	
	/**
	 * Metoda koja preskace praznine.
	 * @param currentIndex
	 * @param data
	 */
	public int skipBlanks(int currentIndex, char[] data) {
		while(currentIndex < data.length) {
			if(data[currentIndex] == ' ' || data[currentIndex]== '\n' || data[currentIndex]=='\t'
					|| data[currentIndex] == '\r') {
				currentIndex++;
				continue;
			}
			else break;
		}
		return currentIndex;
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		String s = "Print file using entered charsets.";
		list.add(s);
		s = "If charsets is not entered use default charsets.";
		list.add(s);
		list = Collections.unmodifiableList(list);
		return list;
	}

}
