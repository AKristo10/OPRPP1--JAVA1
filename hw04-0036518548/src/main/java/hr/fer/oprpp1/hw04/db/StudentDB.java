package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.Scanner;
/**
 * Klasa koja predstavlja glavnu aplikaciju
 * @author Andrea
 *
 */
public class StudentDB {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		StudentDatabase base = new StudentDatabase(lines);
		
		Scanner sc = new Scanner(System.in);
		
		while (true) {
			String query = sc.nextLine();
			if (query.trim().toUpperCase().equals("EXIT")) {
				System.out.println("goodbye!");
				System.exit(0);
			}
				

			QueryParser parser;
			try {
				if (!query.trim().toLowerCase().startsWith("query"))
					throw new IllegalArgumentException("That's not query!");
				System.out.println("> "+query);
				query = query.trim().substring(5);
				parser = new QueryParser(query);
				
				
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				continue;
			}
			System.out.println("Using index for record retrieval.");

			if (parser.isDirectQuery()) {
				String format = new String();
				StudentRecord r = base.forJMBAG(parser.getQueriedJMBAG());
				format += "+";

				for (int i = 0; i < r.getJmbag().length() + 2; i++) {

					format += "=";
				}

				format += "=";
				for (int i = 0; i < r.getLastName().length() + 2; i++) {

					format += "=";
				}

				format += "+";
				for (int i = 0; i < r.getFirstName().length() + 2; i++) {

					format += "=";
				}

				format += "+===+";
				System.out.println(format);
				System.out.println("| " + r.getJmbag() + " | " + r.getLastName() + " | " + r.getFirstName() + " | "
						+ r.getFinalGrade() + " |");
				System.out.println(format);
				System.out.println("Records selected: 1");

			} else {
				String format = new String();
				ArrayList<StudentRecord> lista = base.filter(new QueryFilter(parser.getList()));
				if(lista.size() == 0) {
					System.out.println("Record selected: 0");
					break;
				}
				OptionalInt theLo =  lista.stream().mapToInt(s-> s.getLastName().length()).max();
				int theLongestLastName = theLo.getAsInt();
				int theLongestFirstName = lista.stream().mapToInt(s -> s.getFirstName().length()).max().getAsInt();
			
				format += "+";
				for (int i = 0; i < 12; i++) {
					format += "=";
				}
				format += "+";
				for (int i = 0; i < theLongestLastName + 2; i++) {
					format += "=";
				}
				format += "+";
				for (int i = 0; i < theLongestFirstName + 2; i++) {
					// System.out.print("=");
					format += "=";
				}
				format += "+===+";
				System.out.println(format);

				for (StudentRecord r : lista) {
					System.out.print("| " + r.getJmbag() + " | ");
					System.out.print(r.getLastName());
					for (int i = 0; i < theLongestLastName - r.getLastName().length(); i++)
						System.out.print(" ");
					System.out.print(" | ");
					System.out.print(r.getFirstName());
					for (int i = 0; i < theLongestLastName - r.getFirstName().length(); i++)
						System.out.print(" ");
					System.out.print(" | " + r.getFinalGrade() + " |");
					System.out.println();
				}
				System.out.println(format);
				System.out.println("Records selected: " + lista.size());
			}
			

		}

	}

}
