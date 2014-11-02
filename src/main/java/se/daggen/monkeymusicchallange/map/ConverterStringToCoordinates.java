package se.daggen.monkeymusicchallange.map;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import se.daggen.common.converter.Converter;
import se.daggen.common.map.Coordinate;

public class ConverterStringToCoordinates implements
		Converter<String, Coordinate<Integer>> {

	@Override
	public Coordinate<Integer> convert(String from) {
		from = from.replaceAll("(\\[|\\])", "");
		String[] splitted = from.split(",");
				
		List<Integer> s = Arrays.asList(splitted)
				.parallelStream()
				.map(text -> Integer.valueOf(text))
				.collect(Collectors.toList());
		
		Coordinate<Integer> coordinates = Coordinate.of(s);
		
		return coordinates;
	}

}
