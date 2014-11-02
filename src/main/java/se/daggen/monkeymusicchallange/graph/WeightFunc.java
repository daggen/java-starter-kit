package se.daggen.monkeymusicchallange.graph;

import java.util.List;
import java.util.function.Function;

import se.daggen.common.graph.Edge;
import se.daggen.common.map.Coordinate;
import se.daggen.common.map.Path;

public class WeightFunc {

	public static final Function<List<Edge<Coordinate<Integer>, Path<Integer>, Coordinate<Integer>>>, Integer> FUNC = 
			(e -> e.parallelStream()
					.mapToInt(p -> p.getContent().length())
					.sum());
}
