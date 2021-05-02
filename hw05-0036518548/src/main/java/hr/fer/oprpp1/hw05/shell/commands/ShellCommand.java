package hr.fer.oprpp1.hw05.shell.commands;

import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
/**
 * Sucelje koje predstavlja metode odredene komande.
 * @author Andrea
 *
 */
public interface ShellCommand {
	
	/**
	 * Metoda koja izvrsava odredenu komandu.
	 * @param env  
	 * @param arguments argumenti koje dobija 
	 * @return {@link ShellStatus} 
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	
	/**
	 * Metoda koja vraca ime komande.
	 * @return vraca ime komande.
	 */
	String getCommandName();
	
	
	
	/**
	 * Metoda koja vraca listu opisa komande.
	 * @return lista opisa komande.
	 */
	List<String> getCommandDescription();
}
