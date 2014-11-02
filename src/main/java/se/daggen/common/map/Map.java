package se.daggen.common.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Set;

public class Map<E, C> {
	
	public static final int MAX_PATH_LENGTH = 130;
	
	private final java.util.Map<Coordinate<C>, E> map;
	
	public Map (Integer... sizeInDimension) {
		int totalSize = se.daggen.common.Math.multiply(sizeInDimension);
		map = new HashMap<>(totalSize);
	}
	
	public E getPosition(Coordinate<E> position) {
		return map.get(position);
	}

	public void set(E item, Coordinate<C> posititon) {
		map.put(posititon, item);
	}
	
	public Optional<Path<C>> getShortestPath(Coordinate<C> start, Coordinate<C> stop,
			List<E> exclude,
			Function<Coordinate<C>, List<Coordinate<C>>> possibleDirections) {
		return getShortestPath(start, stop, exclude, possibleDirections, new HashSet<Coordinate<C>>(), new AtomicInteger(MAX_PATH_LENGTH), new Path<>(start));
	}
	
	private Optional<Path<C>> getShortestPath(final Coordinate<C> start, 
			final Coordinate<C> stop,
			final List<E> exclude,
			final Function<Coordinate<C>, List<Coordinate<C>>> possibleDirections,
			Set<Coordinate<C>> visitedCoordinates, 
			AtomicInteger maxLength,
			Path<C> pathSoFar) {
		
		if (start.equals(stop)) {
			maxLength.set(pathSoFar.length());
			return Optional.of(pathSoFar);
		} else if (pathSoFar.length() > maxLength.get())  {
			return Optional.empty();
		} else {
			visitedCoordinates.add(start);
			
			return getNeighbours(exclude, start, possibleDirections)
				.parallelStream()
				.filter(node -> !visitedCoordinates.contains(node))
				.map(neighbour -> 
					getShortestPath(neighbour, 
							stop,
							exclude,
							possibleDirections,
							new HashSet<>(visitedCoordinates),
							maxLength,
							pathSoFar.addLastStep(neighbour)))
				.filter(path -> path.isPresent())
				.map(op -> op.get())
				.reduce((a, b) -> a.length() < b.length() ? a : b);
		}		
	}
	
	public List<Coordinate<C>> getAll(E item) {
		return getAll(Collections.singletonList(item));
	}
	
	public List<Coordinate<C>> getAll(List<E> items) {
		return map.entrySet()
				.parallelStream()
				.filter(entry -> items.contains(entry.getValue()))
				.map(entry -> entry.getKey())
				.collect(Collectors.toList());
	}
	
	public List<Coordinate<C>> getNeighbours(List<E> exclude, Coordinate<C> around, Function<Coordinate<C>, List<Coordinate<C>>> directions) {
		List<Coordinate<C>> neighbours = directions.apply(around);
		
		return map.entrySet()
			.parallelStream()
			.filter(entry -> neighbours.contains(entry.getKey()))
			.filter(entry -> !exclude.contains(entry.getValue()))
			.map(entry -> entry.getKey())
			.collect(Collectors.toList());
	}

}
