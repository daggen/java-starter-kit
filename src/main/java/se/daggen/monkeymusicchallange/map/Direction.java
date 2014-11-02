package se.daggen.monkeymusicchallange.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import se.daggen.common.map.Coordinate;

public class Direction {
	
	public static final Function<Coordinate<Integer>, Coordinate<Integer>> MOVE_UP = (coor -> modify(coor, 0, -1)),
			MOVE_DOWN = (coor -> modify(coor, 0, 1)),
			MOVE_RIGHT = (coor -> modify(coor, 1, 1)),
			MOVE_LEFT = (coor -> modify(coor, 1, -1));
		
	
	public static final List<Direction> DIRECTIONS = Arrays.asList(of("up", -1, 0),
			of("down", 1, 0),
			of("left", 0, -1),
			of("right", 0, 1));

	private Coordinate<Integer> dir;
	private String name;
	
	public static Direction getFirstDirection(Coordinate<Integer> firstStep, Coordinate<Integer> secondStep) {
		Coordinate<Integer> dir = secondStep.combine(firstStep, (a, b) -> a - b);
		
		return DIRECTIONS.parallelStream()
				.filter(direction -> direction.dir.equals(dir))
				.findFirst()
				.get();
	}


	public String getName() {
		return name;
	}


	@Override
	public String toString() {
		return name + ": " + dir.toString();
	}
	
	private Direction(String name, Integer... direction) {
		this.dir = Coordinate.of(direction);
		this.name = name;
	}


	private static Direction of(String name, Integer... direction) {
		return new Direction(name, direction);
	}

	private static Coordinate<Integer> modify(Coordinate<Integer> coor, int dimension, int modifier) {
		List<Integer> coordinates = new ArrayList<>(coor.getCoordinates());
		Integer integer = coordinates.get(dimension) + modifier;
		coordinates.set(dimension, integer);
		return Coordinate.of(coordinates);
	}

}
