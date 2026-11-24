package com.eriux.converter;

public class Main {
	public static void main(String[] args) {
		String binary = "1111111111111111111111111111111111111111111111111111111111111111111111111111111";
		System.out.println("Binary: " + binary);

		String decimal = BaseConverter.binaryToDecimal(binary);
		System.out.println("Decimal: " + decimal);
		
		String hex = BaseConverter.decimalToHex(decimal);
		System.out.println("Hexadecimal: " + hex);
			
		String result = BaseConverter.hexToBinary(hex);
		System.out.println("Binary: " + result);
	}
}