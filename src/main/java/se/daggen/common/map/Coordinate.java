package se.daggen.common.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

public final class Coordinate<E> {
	
	private final List<E> coordinates;

	public static <T> Coordinate<T> ofArgs(@SuppressWarnings("unchecked") T... coordinates) {
		return new Coordinate<T>(coordinates);
	}
	
	public static <T> Coordinate<T> of(T[] coordinates) {
		return new Coordinate<T>(coordinates);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Coordinate<T> of(List<T> s) {
		return of(((T[]) s.toArray()));
	}

	@SafeVarargs
	public Coordinate(E... cooridates) {
		this.coordinates = Collections.unmodifiableList(Arrays.asList(cooridates));
	}
	public Coordinate(List<E> cooridates) {
		this.coordinates = Collections.unmodifiableList(cooridates);
	}
	
	public List<E> getCoordinates() {
		return coordinates;
	}
	
	public Coordinate<E> combine(Coordinate<E> with, 
			BiFunction<E, E, E> combiner) {
		if (coordinates.size() != with.coordinates.size())
			throw new IllegalArgumentException();
		
		List<E> combined = new ArrayList<>(coordinates);
		
		for (int i = 0; i < coordinates.size(); i++) {
			combined.set(i,combiner.apply(this.coordinates.get(i), with.coordinates.get(i))); 
		}
		
		return new Coordinate<>(combined);
	}
	
	@Override
	public int hashCode() {
		return coordinates.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		
		if (!(o instanceof Coordinate<?>))
			return false;
		
		Coordinate<?> other = (Coordinate<?>) o;
		
		return this.coordinates.equals(other.coordinates);
		
	}

	@Override
	public String toString() {
		return coordinates.toString();
	}
}
