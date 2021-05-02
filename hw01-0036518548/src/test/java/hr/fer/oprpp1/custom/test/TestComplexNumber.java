package hr.fer.oprpp1.custom.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.hw01.ComplexNumber;

public class TestComplexNumber {

	@Test
	public void testConstructor() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		assertEquals(1, c1.getReal());
		assertEquals(1, c1.getImaginary());
	}
	@Test
	public void testMethodFromReal() {
		ComplexNumber c1 = ComplexNumber.fromReal(1);
		assertEquals(1, c1.getReal());
		assertEquals(0, c1.getImaginary());
	}
	@Test
	public void testMethodFromImaginary() {
		ComplexNumber c = ComplexNumber.fromImaginary(1);
		assertEquals(0, c.getReal());
		assertEquals(1, c.getImaginary());
	}
	@Test
	public void testMethodfromMagnAndAngle() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(2, 1.57);
		assertEquals(1.9999993658636692, c.getImaginary());
		assertEquals(0.0015926534214665267, c.getReal());
	}
	@Test
	public void testGetReal() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		assertEquals(1, c1.getReal());
	}
	@Test
	public void testGetImaginary() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		assertEquals(1, c1.getImaginary());
	}
	@Test
	public void testGetMagnitude() {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		assertEquals(Math.sqrt(13), c1.getMagnitude());
	}
	@Test
	public void testGetAngle() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		assertEquals(Math.PI/4, c1.getAngle());
	}
	@Test
	public void  testAdd() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		ComplexNumber c2 = c1.add(new ComplexNumber(2, 3));
		assertEquals(3.0, c2.getReal());
		assertEquals(4.0, c2.getImaginary());
	}
	@Test
	public void testSub() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		ComplexNumber c2 = c1.sub(new ComplexNumber(2, 3));
		assertEquals(-1.0, c2.getReal());
		assertEquals(-2.0, c2.getImaginary());
		
	}
	@Test
	public void testMul() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		ComplexNumber c2 = new ComplexNumber(2, 5);
		ComplexNumber c3 = c2.mul(c1);
		assertEquals(-3.0, c3.getReal());
		assertEquals(7.0, c3.getImaginary());	
	}
	@Test
	public void testDiv() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		ComplexNumber c2 = new ComplexNumber(2, 5);
		ComplexNumber c3 = c2.div(c1);
		assertEquals(3.5, c3.getReal());
		assertEquals(1.5, c3.getImaginary());	
	}
	@Test 
	public void testParser() {
		String s = "3+2i";
		ComplexNumber c = ComplexNumber.parse(s);
		assertEquals(3, c.getReal());
		assertEquals(2, c.getImaginary());
		
		String s2 = "3";
		ComplexNumber c2 = ComplexNumber.parse(s2);
		assertEquals(3.0, c2.getReal());
		assertEquals(0.0, c2.getImaginary());
		
		String s3 = "3i";
		ComplexNumber c3 = ComplexNumber.parse(s3);
		assertEquals(0.0, c3.getReal());
		assertEquals(3.0, c3.getImaginary());
	}
	
	@Test 
	public void testPower() {
		ComplexNumber c = new ComplexNumber(1, 2);
		ComplexNumber c1 = c.power(2);
		assertEquals(-3.0, c1.getReal());
		double twoDoubleSub  = c1.getImaginary() - 4;
		boolean statement = twoDoubleSub < 1E-6;
		assertTrue(statement);
		ComplexNumber c2 = new  ComplexNumber(2,0);
		ComplexNumber c3 = c2.power(2);
		assertEquals(4.0, c3.getReal());
		assertEquals(0.0, c3.getImaginary());
	}
	
	@Test
	public void testRoot() {
		ComplexNumber c[] = new ComplexNumber[2];
		ComplexNumber c2 = new ComplexNumber(-3, 4);
		c = c2.root(2);
		assertTrue(c[0].getReal() - 1 < 1E-6);
		assertEquals(2.0, c[0].getImaginary());
		
		ComplexNumber c1 = new  ComplexNumber(4,0);
		ComplexNumber c3 = c1.root(2)[0];
		assertEquals(2.0, c3.getReal());
		assertEquals(0.0, c3.getImaginary());
	}
	
}
