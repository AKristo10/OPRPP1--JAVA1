package hr.fer.oprpp1.hw05.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
/**
 * Klasa koja predstavlja naredbu charsets koja ispisuje podrzane charsetse.
 * @author Andrea
 *
 */
public class Charsets implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments != null) {
			env.writeln("This method has no arguments!");
			return ShellStatus.CONTINUE;
		}
		for (Map.Entry<String, Charset> entry : Charset.availableCharsets().entrySet())  
            env.writeln(  entry.getKey() + 
                             " " + entry.getValue());
		
		return ShellStatus.CONTINUE;
    } 
	

	public static void main(String[] args) {
		Charsets cs = new Charsets();
		cs.executeCommand(null, "");
	}
	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		
		String s = "Lists names of supported charsets for your Java platform.";
		list.add(s);
		list = Collections.unmodifiableList(list);
		return list;
	}

}
