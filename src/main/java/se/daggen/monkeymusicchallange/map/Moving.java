package se.daggen.monkeymusicchallange.map;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import se.daggen.common.map.Coordinate;

import static se.daggen.monkeymusicchallange.map.Direction.*;

public class Moving {

	public static final Function<Coordinate<Integer>, List<Coordinate<Integer>>> FUNC = 
			(coor -> Arrays.asList(MOVE_UP.apply(coor), 
					MOVE_DOWN.apply(coor), 
					MOVE_RIGHT.apply(coor), 
					MOVE_LEFT.apply(coor)));

}
