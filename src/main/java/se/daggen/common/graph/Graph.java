package se.daggen.common.graph;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Graph<N, E, R> {
	
	private Map<N, Node<N, E, R>> nodes = new HashMap<>();
	private Map<E, Edge<N, E, R>> edges = new HashMap<>();
	private Map<R, Region<N, E, R>> regions = new HashMap<>();
	
	public Optional<LinkedList<Edge<N, E, R>>> getShortestPathWhichVisitAllRegions(N start,
			N stop,
			Function<List<Edge<N, E, R>>, Integer> weightFunc) {
		
		List<Region<N, E, R>> startAndStopRegion = Arrays.asList(
				nodes.get(start).getRegion().get(),
				nodes.get(stop).getRegion().get());
		
		return getShortestPathWhichVisitRegions(nodes.get(start), 
				nodes.get(stop),
				regions.values()
					.parallelStream()
					.filter(region -> !startAndStopRegion.contains(region))
					.collect(Collectors.toList()), 
				weightFunc);
		
	}
	
	private Optional<LinkedList<Edge<N, E, R>>> getShortestPathWhichVisitRegions(Node<N, E, R> start,
			Node<N, E, R> stop,
			Collection<Region<N, E, R>> regionsToVisit,
			Function<List<Edge<N, E, R>>, Integer> weightFunc) {
		
		regionsToVisit.remove(start.getRegion().get());
		
		if (regionsToVisit.isEmpty()) {
			Edge<N, E, R> lastEdge = start.getEdges()
				.parallelStream()
				.filter(edge -> edge.isConnectedToNode(Arrays.asList(stop)))
				.findAny()
				.get();
			return Optional.of(addFirstInList(new LinkedList<>(), lastEdge));
		} else {
			return regionsToVisit.stream()
				.map(region -> region.getEdgesbetweenThisAnd(start))
				.flatMap(edges -> edges.parallelStream())
				.map(edge -> {
					Optional<LinkedList<Edge<N, E, R>>> o = getShortestPathWhichVisitRegions(edge.other(start), 
						stop, 
						new HashSet<>(regionsToVisit), 
						weightFunc);
					if (o.isPresent()){
						o = Optional.of(addFirstInList(new LinkedList<>(o.get()), edge));
					}
					return o;})
				.filter(o -> o.isPresent())
				.map(o -> o.get())
				.reduce((a, b) -> weightFunc.apply(a) < weightFunc.apply(b) ?
						a : b);
		}
	}
	
	
	public Collection<Region<N, E, R>> getRegions() {
		return regions.values();
	}
	public void addMonkeyAndUserToGraph(N node, R region) {
		Node<N, E, R> n = new Node<>(node);
		Region<N, E, R> r = getRegion(region);
		n.setRegion(r);
		r.addNode(n);
		nodes.put(node, n);
	}

	public Collection<Node<N, E, R>> getNodes() {
		return nodes.values();
	}
	
	public Node<N, E, R> getNode(N n) {
		return nodes.get(n);
	}

	public void addEdge(E content, N from, N to) {
		Node<N, E, R> f = nodes.get(from);
		Node<N, E, R> t = nodes.get(to);
		
		Edge<N, E, R> edge = new Edge<>(content, f, t);
		f.addEdge(edge);
		t.addEdge(edge);
		
		edges.put(content, edge);
	}
	
	public Region<N, E, R> getRegion(R content) {
		Region<N, E, R> region = regions.get(content);
		if (region == null) {
			region = new Region<N, E, R>(content);
			this.regions.put(content, region);
		}
		return region;
	}

	public Collection<Edge<N, E, R>> getEdges() {
		return edges.values();
	}
	
	private static <E> LinkedList<E> addFirstInList(LinkedList<E> list, E e) {
		list.addFirst(e);
		return list;
	}
}
