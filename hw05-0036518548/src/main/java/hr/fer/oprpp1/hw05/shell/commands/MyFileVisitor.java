package hr.fer.oprpp1.hw05.shell.commands;
import java.io.IOException;
import java.nio.*;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
/**
 * Klasa koja predstavlja File Visitor.
 * @author Andrea
 *
 */
public class MyFileVisitor extends SimpleFileVisitor<Path> {

	String razmak = new String();
	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		System.out.println(razmak + dir.getFileName());
		razmak += "  ";
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		System.out.println(razmak +  file.getFileName());
		return FileVisitResult.CONTINUE;
	}
	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		razmak = razmak.substring(0, razmak.length()-2);
		return FileVisitResult.CONTINUE;
	}

}
