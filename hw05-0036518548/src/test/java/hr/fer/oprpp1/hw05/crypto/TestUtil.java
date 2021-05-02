package hr.fer.oprpp1.hw05.crypto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestUtil {

	@Test
	public  void testHextobyte() {
	 byte[] result = Util.hextobyte("01aE22");
	 byte[] expected = new byte[] {1, -82, 34};
	 assertEquals(expected[0], result[0]);
	 assertEquals(expected[1], result[1]);
	 assertEquals(expected[2], result[2]);
	}
	
	@Test 
	public void testByteToHex() {
		String expected = "01ae22";
		byte[] polje = new byte[] {1, -82, 34};
		String result = Util.bytetohex(polje);
		assertEquals(expected, result);
	}
}
