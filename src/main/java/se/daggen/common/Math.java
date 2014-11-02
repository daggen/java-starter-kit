package se.daggen.common;

import java.util.Arrays;

public final class Math {
	private Math () {}
	
	public static int multiply(Integer... factors) {
		return Arrays.asList(factors)
				.parallelStream()
				.reduce(0, (a, b) -> a * b);
	}

}
