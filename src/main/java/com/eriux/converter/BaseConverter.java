package com.eriux.converter;

import java.math.BigInteger;

public class BaseConverter {
	private static final BigInteger SIXTEEN = BigInteger.valueOf(16L);
	
	public static String binaryToDecimal(String binary) {
		validateBinary(binary);
		
		String number = binary.trim();
		BigInteger dec = BigInteger.ZERO;
		
		dec = toDecimal(number, BigInteger.TWO);
		
		return dec.toString();
	}

	public static String hexToDecimal(String hex) {
		validateHex(hex);

		String number = new StringBuilder(hex.trim()).delete(0, 2).toString();
		BigInteger dec = BigInteger.ZERO;

		dec = toDecimal(number, SIXTEEN);
		
		return dec.toString();
	}

	private static BigInteger toDecimal(String number, BigInteger radix) {
		BigInteger dec = BigInteger.ZERO;
		
		for (int i = 0; i < number.length(); i++) {
			int digit = Character.digit(
				number.charAt(i), 
				radix.intValueExact()
			);
				
			dec = dec.multiply(radix).add(BigInteger.valueOf(digit));
		}
		
		return dec;
	}

	public static String decimalToBinary(String number) {
		validateDecimal(number);
		
		if (number.trim().matches("0+")) {
			return "00000000";
		}

		BigInteger num = new BigInteger(number.trim());
		StringBuilder binary = new StringBuilder();

		while (num.compareTo(BigInteger.ZERO) == 1) {
			binary.append(num.remainder(BigInteger.TWO));
			num = num.divide(BigInteger.TWO);
		}

		while (binary.length() < 8) {
			binary.append("0");
		}

		return binary.reverse().toString();
	}

	public static String hexToBinary(String hex) {
		validateHex(hex);
		String number = hex.trim().substring(2); // 0xffffff -> ffffff

		StringBuilder binary = new StringBuilder();
		for (int i = 0; i < number.length(); i++) {
			binary.append(binaryValue(number.charAt(i)));
		}

		return binary.toString();
	}

	public static String decimalToHex(String number) {
		validateDecimal(number);
		
		if (number.trim().matches("0+")) {
			return "0x000000";
		}

		BigInteger num = new BigInteger(number.trim());
		StringBuilder hex = new StringBuilder();

		while (num.compareTo(BigInteger.ZERO) == 1) {
			BigInteger remainder = num.remainder(SIXTEEN);
			hex.append(remainder.toString(16));
			num = num.divide(SIXTEEN);
		}

		hex.reverse();
		while (hex.length() < 6) {
			hex.insert(0, "0");
		}
			
		String s = hex.toString().toUpperCase();
		return "0x" + s;
	}

	public static String binaryToHex(String number) {
		validateBinary(number);

		StringBuilder binary = new StringBuilder(number.trim());

		while (binary.length() % 4 != 0) {
			binary.insert(0, "0");
		}

		StringBuilder hex = new StringBuilder();

		for (int i = 0; i < binary.length(); i += 4) {
			String group = binary.substring(i, i+4);

			int decimal = Integer.parseInt(group, 2);

			hex.append(Character.toUpperCase(
				Character.forDigit(decimal, 16)
			));
		}

		while (hex.length() < 6) {
			hex.insert(0, "0");
		}
		
		String s = hex.toString().toUpperCase();

		return "0x" + s;
	}

	private static String binaryValue(char ch) {
		int value = Character.digit(ch, 16);
		String binary = Integer.toBinaryString(value);
		return "0".repeat(4 - binary.length()) + binary;
	}

	private static void validateHex(String hex) {
		if (hex == null || !hex.trim().matches("^0[xX][0-9a-fA-F]+$")) {
			throw new IllegalArgumentException("Invalid hex number");
		}
	}

	private static void validateBinary(String binary) {
		if (binary == null || !binary.trim().matches("[01]+$")) {
			throw new IllegalArgumentException("Invalid binary number");
		}
	}

	private static void validateDecimal(String decimal) {
		if (decimal == null || !decimal.trim().matches("\\d+$")) {
			throw new IllegalArgumentException("Invalid decimal");	
		}
	}
}
