package se.daggen.common.graph;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


public class Edge<N, E, R> {
	private final E content;
	private final Node<N, E, R> from, to;
	
	public Edge(E content, Node<N, E, R> from, Node<N, E, R> to) {
		this.content = content;
		this.from = from;
		this.to = to;
	}
	
	public Node<N, E, R> other(Node<N, E, R> o) {
		if (o == from)
			return to;
		else if (o == to)
			return from;
		else
			throw new NoSuchElementException();
	}
	
	public boolean isConnectedToRegion(Collection<Region<N, E, R>> regions) {
		List<Region<N, E, R>> connectedToRegions = 
				Arrays.asList(from.getRegion(), to.getRegion())
					.parallelStream()
					.filter(o -> o.isPresent())
					.map(o -> o.get())
					.collect(Collectors.toList());
		
		return regions.parallelStream()
				.map(region -> connectedToRegions.contains(region))
				.reduce(false, (a, b) -> a || b);
	}
	public boolean isConnectedToNode(Collection<Node<N, E, R>> nodes) {
		return nodes.contains(from)
				|| nodes.contains(to);
	}
	
	public E getContent() {
		return content;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		
		if (!(o instanceof Edge<?, ?, ?>))
			return false;
		
		@SuppressWarnings("unchecked")
		Edge<N, E, R> other = (Edge<N, E, R>) o;
		
		return this.content.equals(other);
	}
	@Override
	public int hashCode() {
		return content.hashCode();
	}
	@Override
	public String toString() {
		return "Between: " + from.toString() + " and " + to.toString();
	}
}
