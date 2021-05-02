package hr.fer.oprpp1.hw05.shell;

import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import hr.fer.oprpp1.hw05.shell.commands.Cat;
import hr.fer.oprpp1.hw05.shell.commands.Charsets;
import hr.fer.oprpp1.hw05.shell.commands.Copy;
import hr.fer.oprpp1.hw05.shell.commands.Exit;
import hr.fer.oprpp1.hw05.shell.commands.Help;
import hr.fer.oprpp1.hw05.shell.commands.HexDump;
import hr.fer.oprpp1.hw05.shell.commands.Ls;
import hr.fer.oprpp1.hw05.shell.commands.Mkdir;
import hr.fer.oprpp1.hw05.shell.commands.ShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.Symbol;
import hr.fer.oprpp1.hw05.shell.commands.Tree;

/**
 * Klasa koja predstavlja Command Prompt.
 * @author Andrea
 *
 */
public class MyShell {

	private static class EnvironmentClass implements Environment {
		Character multiLineSymbol;
		Character promptSymbol;
		Character moreLineSymbol;
		Scanner sc = new Scanner(System.in);

		@Override
		public String readLine() throws ShellIOException {
			try {
				String result = new String();
				this.write(String.valueOf(this.getPromptSymbol()));
				result = sc.nextLine();
				String r = new String();
				if (result.endsWith(String.valueOf(getMorelinesSymbol()))) {

					r = result.substring(0, result.length() - 1);
					boolean end = false;
					while (!end) {
						write(String.valueOf(getMultilineSymbol()));
						result = sc.nextLine();
						if (!result.endsWith(String.valueOf(getMorelinesSymbol()))) {
							end = true;
							r += result;
						} else
							r += result.substring(0, result.length() - 1);
					}
					return r;
				} else {

					return result;
				}

			} catch (Exception e) {
				throw new ShellIOException();
			}
		}

		@Override
		public void write(String text) throws ShellIOException {
			System.out.print(text + " ");
		}

		@Override
		public void writeln(String text) throws ShellIOException {
			System.out.println(text);
		}

		@Override
		public SortedMap<String, ShellCommand> commands() {
			SortedMap<String, ShellCommand> commands = new TreeMap<String, ShellCommand>();
			commands.put("ls", new Ls());
			commands.put("cat", new Cat());
			commands.put("charsets", new Charsets());
			commands.put("copy", new Copy());
			commands.put("help", new Help());
			commands.put("hexdump", new HexDump());
			commands.put("mkdir", new Mkdir());
			commands.put("symbol", new Symbol());
			commands.put("tree", new Tree());
			commands.put("exit", new Exit());
			return commands;
		}

		@Override
		public Character getMultilineSymbol() {
			return multiLineSymbol;
		}

		@Override
		public void setMultilineSymbol(Character symbol) {
			this.multiLineSymbol = symbol;

		}

		@Override
		public Character getPromptSymbol() {
			return promptSymbol;
		}

		@Override
		public void setPromptSymbol(Character symbol) {
			this.promptSymbol = symbol;
		}

		@Override
		public Character getMorelinesSymbol() {
			return moreLineSymbol;
		}

		@Override
		public void setMorelinesSymbol(Character symbol) {
			this.moreLineSymbol = symbol;
		}

	}

	public static void main(String[] args) {
		try {
			EnvironmentClass environment = new EnvironmentClass();
			environment.writeln("Welcome to MyShell v 1.0");
			String l = new String();
			environment.setMultilineSymbol('#');
			environment.setPromptSymbol('>');
			environment.setMorelinesSymbol('\\');
			ShellStatus status = null;
			do {
				l = environment.readLine();
				char[] data = l.trim().toCharArray();
				int currentIndex = 0;
				String commandName = "";
				while (currentIndex < data.length && data[currentIndex] != ' ') {
					commandName += data[currentIndex++];
				}
				currentIndex++;
				String arguments = new String();
				if (currentIndex >= data.length)
					arguments = null;
				else
					arguments = l.trim().substring(currentIndex, l.trim().length());

				if (environment.commands().containsKey(commandName.toLowerCase())) {
					ShellCommand com = environment.commands().get(commandName.toLowerCase());
					status = com.executeCommand(environment, arguments);
				} else {
					environment.writeln(commandName + " is not recognized as an internal or external command, "
							+ "operable program or batch file.");
					status = ShellStatus.CONTINUE;
				}

			} while (status != ShellStatus.TERMINATE);

			environment.sc.close();

		} catch (ShellIOException e) {
			e.printStackTrace();
		}

	}
}
