package hr.fer.oprpp1.hw05.crypto;

import java.util.Stack;

public class Util {

	/**
	 * Metoda koja pretvara hex-encoded String i vraca prikladno polje byte[]
	 * 
	 * @throws IllegalArgumentException ako je nevaljan argument
	 * @param keyText
	 * @return polje byte[]
	 */
	public static byte[] hextobyte(String keyText) {
		if (keyText.length() % 2 != 0)
			throw new IllegalArgumentException("Neparan broj!");
		// ako je nevaljan znak dodaj
		int sizeOfResult = keyText.length() / 2;
		byte[] result = new byte[sizeOfResult];
		String hexAplhabet = "0123456789abcdef";
		int index = 0;
		keyText = keyText.toLowerCase();
		for (int i = 0; i < sizeOfResult * 2; i += 2) {

			String firstDigit = String.valueOf(keyText.charAt(i));
			String secondDigit = String.valueOf(keyText.charAt(i + 1));
			boolean negativ = false;
			if (isHexLetter(firstDigit) || Integer.valueOf(firstDigit) > 7) {
				negativ = true;
			}
			String number = firstDigit + secondDigit;
			int rez = 0;
			for (int k = 0; k < number.length(); k++) {
				char num = number.charAt(k);
				int n = hexAplhabet.indexOf(num);
				rez = 16 * rez + n;
			}
			if (negativ) {
				number = convertToBinary(rez);
				number = findComplement(number);
				rez = -1 * binarytoDecimal(number);
			}

			result[index++] = (byte) rez;
		}
		return result;
	}

	/**
	 * Metoda koja pretvara binaran broj u dekadski
	 * 
	 * @param bin
	 * @return
	 */
	public static int binarytoDecimal(String bin) {
		int decimal = 0;
		int n = 0;
		int numberBin = Integer.parseInt(bin);
		while (true) {
			if (numberBin == 0) {
				break;
			} else {
				int temp = numberBin % 10;
				decimal += temp * Math.pow(2, n);
				numberBin = numberBin / 10;
				n++;
			}
		}
		return decimal;
	}

	/**
	 * Metoda koja provjerava je li slovo u heksadekadskoj abcedi, odnosno je li a,
	 * b, c, d, e, f.
	 * 
	 * @param letter
	 * @return <code>true</code> ako da, inac <code>false</code>
	 */
	public static boolean isHexLetter(String letter) {
		letter = letter.toLowerCase();
		if (letter.charAt(0) == 'a' || letter.charAt(0) == 'b' || letter.charAt(0) == 'c' || letter.charAt(0) == 'd'
				|| letter.charAt(0) == 'e' || letter.charAt(0) == 'f') {
			return true;
		}
		return false;
	}

	/**
	 * Metoda koja prima polje byte[] i vraca String koji predstavlja hex vrijednost
	 * tog polja u big-endian notaciji
	 * 
	 * @param bytearray
	 * @return hex vrijednost @param u big-endian notaciji, ili prazan string ako je
	 *         prazno polje u pitanju.
	 */
	public static String bytetohex(byte[] bytearray) {
		if (bytearray.length == 0)
			return "";
		String result = new String();
		String dva = "";
		String hexAplhabet = "0123456789abcdef";

		for (byte b : bytearray) {
			int num = (int) b;
			if(num < 0) {
				String s = convertToBinary(-1* num);
				s = findComplement(s);
				num = binarytoDecimal(s);
			}
			dva = "";
			while (num > 0) {
				
				int r = num % 16;
				dva += String.valueOf(hexAplhabet.charAt(r));
				num = num / 16;
			}
			if(dva.length() < 2) {
				result  += 0 + String.valueOf(dva.charAt(0));
				continue;
			}
			else
			result  += String.valueOf(dva.charAt(1)) + String.valueOf(dva.charAt(0));
			
		}
		return result;
	}

	/**
	 * Metoda koja pretvara cijeli decimalni broj u binarni.
	 * 
	 * @param i
	 * @return dekadski broj pretvoren u binarni u String formatu
	 */

	public static String convertToBinary(Integer i) {
		int number = i.intValue();
		Stack<Integer> stack = new Stack<Integer>();

		boolean negativan = false;
		if (number < 0) {
			number = number * -1;
			negativan = true;
		}

		while (number != 0) {
			int ostatak = number % 2;
			stack.push(Integer.valueOf(ostatak));
			number = number / 2;
		}
		int sizeOfStack = stack.size();
		String binaryInString = new String();
		for (int j = 0; j < 8 - sizeOfStack; j++) {
			binaryInString += "0";
		}
		for (int j = 0; j < sizeOfStack; j++) {
			binaryInString += String.valueOf(stack.pop());
		}

		if (negativan) {
			binaryInString = findComplement(binaryInString);
		}

		return binaryInString;
	}

	/**
	 * Metoda koja pretvara pozitivan binaran broj u negativan metodom drugog
	 * komplementa
	 * 
	 * @param binaryInString
	 * @return negativan binarni broj
	 */
	public static String findComplement(String binaryInString) {
		boolean foundFirst = false;
		char[] data = binaryInString.toCharArray();
		for (int k = data.length-1; k >= 0; k--) {
			if (data[k] == '1' && foundFirst == false) {
				foundFirst = true;
				continue;
			}
			if (foundFirst) {
				// komplementiraj
				if (data[k] == '0')
					data[k] = '1';
				else
					data[k] = '0';
			}
		}
		binaryInString = String.valueOf(data);
		return binaryInString;
	}

	/**
	 * Metoda koja pretvara binarni broj zapisan u String formatu u heksadekadski
	 * broj
	 * 
	 * @param binary
	 * @return
	 */
	public static String convertToHex(String binary) {
		String firstPart = binary.substring(0, 4);
		String secondPart = binary.substring(4, 8);
		// System.out.println(firstPart + " " + secondPart);
		String result = new String();
		result += partOfHex(firstPart);
		result += partOfHex(secondPart);
		return result;
	}

	/**
	 * Metoda koja sluzi za pretvaranje 4 binarne znamenke u hekasadekadsku
	 * 
	 * @param number
	 * @return heksadekadska znamenka
	 */
	public static String partOfHex(String number) {
		String result = new String();
		int sum = 0;
		int powerOf2 = 8;
		for (int i = 0; i < 4; i++) {
			sum += Integer.valueOf(number.charAt(i) + "") * powerOf2;
			powerOf2 = powerOf2 / 2;
		}
		if (sum == 10) {
			result = "a";
		} else if (sum == 11) {
			result = "b";
		} else if (sum == 12) {
			result = "c";
		} else if (sum == 13) {
			result = "d";
		} else if (sum == 14) {
			result = "e";
		} else if (sum == 15) {
			result = "f";
		} else if (sum < 10) {
			result = String.valueOf(sum);
		}
		return result;
	}

	public static void main(String[] args) {
		// convertToBinary(17);
		byte[] r = hextobyte("01aE22");
		System.out.println("alo");

	}

}
