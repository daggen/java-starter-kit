package se.daggen.common.graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Region<N, E, R> {
	private R content;
	private Set<Node<N, E, R>> nodes;
	
	public Region(R content) {
		this.content = content;
		nodes = new HashSet<Node<N, E, R>>();
	}

	public void addNode(Node<N, E, R> node) {
		nodes.add(node);
	}
	
	public Collection<Node<N, E, R>> getNodes() {
		return nodes;
	}
	public Collection<Edge<N, E, R>> getEdgesbetweenThisAnd(Node<N, E, R> node) {
		return nodes.parallelStream()
				.map(n -> n.getEdges()
						.parallelStream()
						.filter(e -> e.other(n).equals(node))
						.collect(Collectors.toList()))
				.flatMap(list -> list.parallelStream())
				.collect(Collectors.toList());				
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		
		if (!(o instanceof Region<?, ?, ?>))
			return false;
		
		@SuppressWarnings("unchecked")
		Region<N, E, R> other = (Region<N, E, R>) o;
		
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
