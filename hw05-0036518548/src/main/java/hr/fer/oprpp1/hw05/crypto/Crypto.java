package hr.fer.oprpp1.hw05.crypto;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Klasa koja kriptira/dekriptira danu datoteku koristeci AES kripto-algoritam i
 * 128 bitni kljuc ili racuna ili provjerava SHA-256 sazetak
 * 
 * @author Andrea
 *
 */
public class Crypto {

	public static void main(String[] args) {

		String method = args[0];
		String file = args[1];
		String destFile = "";

		// U slucaju da je u pitanju enkripcija i dekripcija potrebna nam je odredisna
		// datoteka
		if (!method.equals("checksha"))
			destFile = args[2];

		if (method.equals("checksha"))
			checkSha(file);

		else
			encryptOrDecrypt(method, file, destFile);

	}

	/**
	 * Metoda koja racuna i provjerava SHA-256.
	 * 
	 * @param file datoteka ciji se SHA-256 racuna.
	 * @throws DODAJ!!!
	 */
	public static void checkSha(String file) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please provide expected sha-256 digest for " + file + ":");
		String keyText = sc.nextLine();
		sc.close();
		MessageDigest sha;
		InputStream is;
		try {
			is = Files.newInputStream(Path.of("./" + file));
			sha = MessageDigest.getInstance("SHA-256");

			byte[] pieceOfFile = new byte[4 * 1024];
			int size;
			while ((size = is.read(pieceOfFile)) != -1) {
				// realociranje da ne bismo dobili visak
				byte[] array = new byte[size];
				for (int i = 0; i < size; i++) {
					array[i] = pieceOfFile[i];
				}
				sha.update(array);
			}
			byte[] hash = sha.digest();
			if (Util.bytetohex(hash).equals(keyText)) {
				System.out.println("Digesting completed. Digest of " + file + " matches expected digest.");
			} else {
				System.out.println("Digesting completed. Digest of" + file
						+ "does not match the expected digest. Digest was:" + Util.bytetohex(hash));
			}
			is.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sc.close();

	}

	/**
	 * Metoda koja kriptira ili dekriptira odredeni file u destFile
	 * 
	 * @param method   moze biti enkripcija ili dekripcija
	 * @param file     izvorsna datoteka, datoteka koju enkriptiramo/dekriptiramo
	 * @param destFile odredisna datoteka
	 * @throws DODAJ!!
	 */
	public static void encryptOrDecrypt(String method, String file, String destFile) {

		Scanner sc = new Scanner(System.in);

		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		String keyText = sc.nextLine();

		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		String ivText = sc.nextLine();

		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		try {
			// inicijalizacija Ciphera
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(method.equals("encrypt") ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
			byte[] pieceOfFile = new byte[4 * 1024];
			InputStream is = Files.newInputStream(Path.of("./" + file));
			Files.createFile(Path.of("./" + destFile));
			OutputStream os = Files.newOutputStream(Path.of("./" + destFile));
			int size;
			while ((size = is.read(pieceOfFile)) != -1) {
				// realociranje da ne bismo dobili visak
				byte[] array = new byte[size];
				for (int i = 0; i < size; i++) {
					array[i] = pieceOfFile[i];
				}
				os.write(cipher.update(array));
			}
			os.write(cipher.doFinal());
			is.close();
			os.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(method.equals("encrypt"))
			System.out.println("Encryption completed. Generated file " + destFile + " based on file " + file + ".");
		else
			System.out.println("Decryption completed. Generated file " + destFile + " based on file " + file + ".");
		sc.close();
	}

}
