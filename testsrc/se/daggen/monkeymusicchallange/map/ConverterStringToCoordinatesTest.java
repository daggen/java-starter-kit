package se.daggen.monkeymusicchallange.map;

import static org.junit.Assert.*;

import org.junit.Test;

import se.daggen.common.map.Coordinate;

public class ConverterStringToCoordinatesTest {

	private ConverterStringToCoordinates testObject = new ConverterStringToCoordinates();
	
	@Test
	public void test() {
		Coordinate<Integer> actual = testObject.convert("[5,0]");
		
		Coordinate<Integer> expected = Coordinate.ofArgs(5, 0);
		
		assertEquals(expected, actual);
		
	}

}
