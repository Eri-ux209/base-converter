
package com.eriux.converter;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BaseConverterTest {

    @ParameterizedTest
    @CsvSource({
        "1111, 15",
        "1110, 14",
        "1101, 13",
        "1100, 12",
        "100, 4",
        "0000, 0",
        "11111111, 255"
    })
    void binaryToDecimal(String binary, String expected) {
        assertEquals(expected, BaseConverter.binaryToDecimal(binary));
    }

    @ParameterizedTest
    @CsvSource({
        "15, 00001111",
        "14, 00001110",
        "13, 00001101",
        "12, 00001100",
        "0, 00000000",
        "255, 11111111",
        "256, 100000000",
        "4294967295, 11111111111111111111111111111111"
    })
    void decimalToBinary(String decimal, String expected) {
        assertEquals(expected, BaseConverter.decimalToBinary(decimal));
    }

    @ParameterizedTest
    @CsvSource({
        "2620, 0x000A3C",
        "457, 0x0001C9",
        "103, 0x000067",
        "1004, 0x0003EC",
        "3017, 0x000BC9",
        "0, 0x000000"
    })
    void decimalToHex(String decimal, String expected) {
        assertEquals(expected, BaseConverter.decimalToHex(decimal));
    }

    @ParameterizedTest
    @CsvSource({
        "0x000A3C, 2620",
        "0x0001C9, 457",
        "0x000067, 103",
        "0x0003EC, 1004",
        "0x000BC9, 3017",
        "0x000000, 0"
    })
    void hexToDecimal(String hex, String expected) {
        assertEquals(expected, BaseConverter.hexToDecimal(hex));
    }

    @ParameterizedTest
    @CsvSource({
        "00011110, 0x00001E",
        "10001010, 0x00008A",
        "00001110, 0x00000E",
        "01111010, 0x00007A",
        "00000000, 0x000000"
    })
    void binaryToHex(String binary, String expected) {
        assertEquals(expected, BaseConverter.binaryToHex(binary));
    }

    @ParameterizedTest
    @CsvSource({
        "0x00001E, 000000000000000000011110",
        "0x00008A, 000000000000000010001010",
        "0x00000E, 000000000000000000001110",
        "0x00007A, 000000000000000001111010",
        "0x0000DA, 000000000000000011011010",
        "0x000000, 000000000000000000000000"
    })
    void hexToBinary(String hex, String expected) {
        assertEquals(expected, BaseConverter.hexToBinary(hex));
    }

    @Test
    void invalidBinary() {
        assertThrows(IllegalArgumentException.class,
            () -> BaseConverter.binaryToDecimal("10201"));

        assertThrows(IllegalArgumentException.class,
            () -> BaseConverter.binaryToHex("ABC"));

        assertThrows(IllegalArgumentException.class,
            () -> BaseConverter.binaryToDecimal(""));
        
		assertThrows(IllegalArgumentException.class,
            () -> BaseConverter.binaryToDecimal(" "));

        assertThrows(IllegalArgumentException.class,
            () -> BaseConverter.binaryToHex(null));
    }

    @Test
    void invalidDecimal() {
        assertThrows(IllegalArgumentException.class,
            () -> BaseConverter.decimalToBinary("12A"));

        assertThrows(IllegalArgumentException.class,
            () -> BaseConverter.decimalToHex("-10"));

		assertThrows(IllegalArgumentException.class,
			() -> BaseConverter.decimalToBinary(""));
		
		assertThrows(IllegalArgumentException.class,
			() -> BaseConverter.decimalToBinary(" "));

        assertThrows(IllegalArgumentException.class,
            () -> BaseConverter.decimalToHex(null));
    }

    @Test
    void invalidHex() {
        assertThrows(IllegalArgumentException.class,
            () -> BaseConverter.hexToDecimal("123ABC"));

        assertThrows(IllegalArgumentException.class,
            () -> BaseConverter.hexToBinary("0xXYZ"));

        assertThrows(IllegalArgumentException.class,
            () -> BaseConverter.hexToDecimal("0x"));
        
		assertThrows(IllegalArgumentException.class,
            () -> BaseConverter.hexToDecimal(""));
        
		assertThrows(IllegalArgumentException.class,
            () -> BaseConverter.hexToDecimal(" "));

        assertThrows(IllegalArgumentException.class,
            () -> BaseConverter.hexToBinary(null));

        assertThrows(IllegalArgumentException.class,
            () -> BaseConverter.hexToDecimal("0x123XYZ"));
    }

    @Test
    void lowercaseHex() {
        assertEquals("2620",
            BaseConverter.hexToDecimal("0x000a3c"));

        assertEquals("000000000000000000011110",
            BaseConverter.hexToBinary("0x00001e"));
    }

    @Test
    void whitespaceInputs() {
        assertEquals("15",
            BaseConverter.binaryToDecimal(" 1111 "));

        assertEquals("15",
            BaseConverter.hexToDecimal(" 0xF "));

        assertEquals("00001111",
            BaseConverter.decimalToBinary(" 15 "));

        assertEquals("0x00000F",
            BaseConverter.decimalToHex(" 15 "));
    }

    @Test
    void largeNumbers() {
        String number = "123456789012345678901234567890";

        String binary = BaseConverter.decimalToBinary(number);
        String hex = BaseConverter.decimalToHex(number);

        assertEquals(number,
            BaseConverter.binaryToDecimal(binary));

        assertEquals(number,
            BaseConverter.hexToDecimal(hex));
    }

    @Test
    void conversionRoundTrip() {
        String binary = "1010101011110000";

        String decimal = BaseConverter.binaryToDecimal(binary);
        String hex = BaseConverter.decimalToHex(decimal);
        String result = BaseConverter.hexToBinary(hex);

        assertEquals(
			new BigInteger(binary, 2), 
			new BigInteger(result, 2)
		);
    }
		
	@Test
	void shouldHandleLeadingZeros() {
	    assertEquals("5", BaseConverter.binaryToDecimal("00000101"));
	    assertEquals("15", BaseConverter.hexToDecimal("0x00000F"));
	    assertEquals("0x00000A", BaseConverter.binaryToHex("00001010"));
	}
	
	@Test
	void shouldHandleLargeNumbers() {
	    assertEquals(
	        "2147483648",
	        BaseConverter.binaryToDecimal(
	            "10000000000000000000000000000000"
	        )
	    );
	
	    assertEquals(
	        "0xFFFFFFFF",
	        BaseConverter.decimalToHex("4294967295")
	    );
	}
	
	@Test
	void shouldHandleHexadecimalCaseInsensitive() {
	    assertEquals("10", BaseConverter.hexToDecimal("0x00000a"));
	    assertEquals("255", BaseConverter.hexToDecimal("0x0000ff"));
	    assertEquals("175", BaseConverter.hexToDecimal("0x0000aF"));
	}
	
	@Test
	void shouldHandleZero() {
	    assertEquals("0", BaseConverter.binaryToDecimal("0"));
	    assertEquals("0x000000", BaseConverter.decimalToHex("0"));
	    assertEquals("000000000000000000000000", BaseConverter.hexToBinary("0x000000"));
	}

}
