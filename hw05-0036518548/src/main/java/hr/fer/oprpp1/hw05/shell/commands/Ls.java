package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
/**
 * Klasa koja predstavlja komandu ls koja printa sadrzaj zadanog direktorija.
 * @author Andrea
 *
 */
public class Ls implements ShellCommand {

	public String razmak = new String();

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
			env.writeln("You can list only one directory!");
			return ShellStatus.CONTINUE;
		}
		
		String directoryName = arguments;
		if(!Files.isDirectory(Path.of(directoryName))) {
			env.writeln("That's not directory!");
			return ShellStatus.CONTINUE;
		}
		try {
			List<Path> files = Files.list(Path.of(directoryName)).collect(Collectors.toList());
			for (Path f : files) {
				String firstLine = fillFirstColumn(f);
				String secondLine =String.valueOf(Files.size(f));
				int size = secondLine.length();
				String space = "";
				if(10 - size > 0) {
					
					for(int i = size; i<10; i++)
						space += " ";
				}
				secondLine = space + secondLine;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				BasicFileAttributeView faView = Files.getFileAttributeView(f, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
				BasicFileAttributes attributes = faView.readAttributes();
				FileTime fileTime = attributes.creationTime();
				String thirdLine = sdf.format(new Date(fileTime.toMillis()));
				
				String fourthColumn = f.getFileName().toString();
				System.out.println(firstLine + "  " + secondLine + "  " + thirdLine + " "+ fourthColumn);
			}
		} catch (IOException e) {
			env.writeln("Error occurs when opening files");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Metoda koja popunjava prvi stupac svakog retka kod ispisa direktorija.
	 * @param f
	 * @return String koji predstvalja prvi stupac retka
	 */
	public String fillFirstColumn(Path f) {
		String firstLine = "";
		if (Files.isDirectory(f)) 
			firstLine = "d";
		else
			firstLine = "-";

		if (Files.isReadable(f)) 
			firstLine += "r";
		else
			firstLine += "-";

		if (Files.isWritable(f))
			firstLine += "w";
		else
			firstLine += "-";

		if (Files.isExecutable(f))
			firstLine += "x";
		else
			firstLine += "-";

		return firstLine;
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "ls";
	}

	public static void main(String[] args) {
		Ls ls = new Ls();
		ls.executeCommand(null, "C:\\Users\\Andrea\\Desktop\\PPJ");
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		String s = "Takes a single argument – directory –";
		list.add(s);
		s  = "Writes a directory listing";
		list.add(s);
		list = Collections.unmodifiableList(list);
		return list;
	}

}
