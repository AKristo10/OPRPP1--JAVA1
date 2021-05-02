package hr.fer.oprpp1.hw05.shell;

import java.util.SortedMap;

import hr.fer.oprpp1.hw05.shell.commands.ShellCommand;
/**
 * Sucelje koje predstvalja interkaciju s korisnikom.
 * @author Andrea
 *
 */
public interface Environment {
	/**
	 * Metoda koja cita jednu liniju korisnikovog inputa.
	 * @return procitana linija.
	 * @throws ShellIOException
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Metoda koja predstavlja korisnikov upis bez oznake za kraj retka
	 * @param text
	 * @throws ShellIOException
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Metoda koja predstavlja korisnikov upis i prelazi u novi red
	 * @param text
	 * @throws ShellIOException
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Metoda koja vraca popis svih komandi.
	 * @return mapa svih komandi gdje je kljuc ime komande a vrijednost sama komanda.
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Metoda koja vraca MULTILINE simbol.
	 * @return MULTILINE simbol.
	 */
	Character getMultilineSymbol();
	
	/**
	 * Metoda koja postavlja MULTILINE simbol.
	 * @param symbol
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Metoda koja vraca PROMPT simbol.
	 * @return PROMPT simbol.
	 */
	Character getPromptSymbol();
	
	/**
	 * Metoda koja postavlja PROMPT simbol.
	 * @param symbol
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Metoda koja vraca MORELINES simbol.
	 * @return MORELINES simbol.
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Metoda koja postavlja MORELINES simbol.
	 * @param symbol
	 */
	void setMorelinesSymbol(Character symbol);
}
