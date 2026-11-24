package com.eriux.converter;

import java.math.BigInteger;

import net.jqwik.api.Property;
import net.jqwik.api.ForAll;
import net.jqwik.api.constraints.LongRange;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseConverterPropertyTest {
	@Property(tries = 500)
    void decimalToBinaryAndBack(
        @ForAll 
		@LongRange(min = 0, max = 1_000_000) 
		long number
    ) {
        String binary = BaseConverter.decimalToBinary(
            String.valueOf(number)
        );

        String result = BaseConverter.binaryToDecimal(binary);

        assertEquals(String.valueOf(number), result);
	}

	@Property(tries = 500)
	void decimalToHexAndBack(
		@ForAll
		@LongRange(min = 0, max = 1_000_000)
		long number
	) {
		String hex = BaseConverter.decimalToHex(
			String.valueOf(number)
		);

		String result = BaseConverter.hexToDecimal(hex);

		assertEquals(String.valueOf(number), result);
	}

	@Property(tries = 500)
	void binaryToHexAndBack(
		@ForAll
		@LongRange(min = 0, max = 1_000_000)
		long number
	) {
		String original = BaseConverter.decimalToBinary(
			String.valueOf(number)
		);

		String hex = BaseConverter.binaryToHex(original);

		String result = BaseConverter.hexToBinary(hex);

		assertEquals(
			new BigInteger(original, 2), 
			new BigInteger(result, 2)
		);
	}

	@Property(tries = 500)
	void similarHexAndBin(
		@ForAll
		@LongRange(min = 0, max = 1_000_000)
		long number
	) {
		String hex = BaseConverter.decimalToHex(
			String.valueOf(number)
		);

		String bin = BaseConverter.decimalToBinary(
			String.valueOf(number)
		);

		assertEquals(
			new BigInteger(hex.substring(2), 16),
			new BigInteger(bin, 2)
		);
	}
}