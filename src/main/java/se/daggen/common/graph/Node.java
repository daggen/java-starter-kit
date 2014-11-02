package se.daggen.common.graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class Node<N, E, R> {
	private N content;
	private Optional<Region<N, E, R>> region;
	private Set<Edge<N, E, R>> edges;
	
	public Node(N content) {
		this.content = content;
		this.region = Optional.empty();
		this.edges = new HashSet<Edge<N, E, R>>();
	}

	public N get() {
		return content;
	}

	public void addEdge(Edge<N, E, R> edge) {
		this.edges.add(edge);
	}
	
	public boolean isConnectedTo(Node<N, E, R> node) {
		return edges.parallelStream()
				.map(edge -> edge.other(this))
				.filter(other -> other.equals(node))
				.findAny()
				.isPresent();
	}
	public Optional<Region<N, E, R>> getRegion() {
		return region;
	}
	
	public Node<N, E, R> setRegion(Region<N, E, R> region) {
		this.region = Optional.of(region);
		return this;
	}
	
	public Collection<Edge<N, E, R>> getEdges() {
		return edges;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		
		if (!(o instanceof Node<?, ?, ?>))
			return false;
		
		@SuppressWarnings("unchecked")
		Node<N, E, R> other = (Node<N, E, R>) o;
		
		return this.content.equals(other);
	}
	@Override
	public int hashCode() {
		return content.hashCode();
	}	
	@Override
	public String toString() {
		return content.toString();
	}
}
