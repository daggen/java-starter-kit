package se.daggen.monkeymusicchallange.graph;

import java.util.Arrays;
import java.util.Optional;

import se.daggen.common.converter.Converter;
import se.daggen.common.graph.Graph;
import se.daggen.common.graph.Node;
import se.daggen.common.map.Coordinate;
import se.daggen.common.map.Map;
import se.daggen.common.map.Path;
import se.daggen.monkeymusicchallange.map.MapItem;
import se.daggen.monkeymusicchallange.map.Moving;
import static se.daggen.monkeymusicchallange.map.MapItem.*;

public class ConverterMapToGraph implements Converter<Map<MapItem, Integer>, Graph<Coordinate<Integer>, Path<Integer>, Coordinate<Integer>>>{
	
	@Override
	public Graph<Coordinate<Integer>, Path<Integer>, Coordinate<Integer>> convert(
			Map<MapItem, Integer> map) {
		Graph<Coordinate<Integer>, Path<Integer>, Coordinate<Integer>> graph = new Graph<>();
		
		map.getAll(MapItem.NICE_ITEMS)
			.parallelStream()
			.forEach(region -> addNiceItemsToGraph(region, graph, map));
		
		map.getAll(Arrays.asList(USER, MONKEY))
		.parallelStream()
		.forEach(node -> graph.addMonkeyAndUserToGraph(node, node));
		
		graph.getNodes()
			.parallelStream()
			.forEach(from -> addEdgesBetweenAllNodes(from, graph, map));
		
		return graph;
	}
	


	private void addEdgesBetweenAllNodes(Node<Coordinate<Integer>, Path<Integer>, Coordinate<Integer>> from, 
			Graph<Coordinate<Integer>, Path<Integer>, Coordinate<Integer>> graph, 
			Map<MapItem, Integer> map) {
		
		graph.getNodes()
			.parallelStream()
			.forEach(to -> addEdgeBetween(from, to, graph, map));
	}

	private void addEdgeBetween(Node<Coordinate<Integer>, Path<Integer>, Coordinate<Integer>> from,
			Node<Coordinate<Integer>, Path<Integer>, Coordinate<Integer>> to,
			Graph<Coordinate<Integer>, Path<Integer>, Coordinate<Integer>> graph, 
			Map<MapItem, Integer> map) {
		
		Optional<Path<Integer>> path = map.getShortestPath(from.get(), to.get(), IMPOSSIBLE_TERRAIN, Moving.FUNC);
		
		if (path.isPresent()) {
			graph.addEdge(path.get(), from.get(), to.get());
		}
	}



	private void addNiceItemsToGraph(Coordinate<Integer> region,
			Graph<Coordinate<Integer>, Path<Integer>, Coordinate<Integer>> graph,
			Map<MapItem, Integer> map) {
		
		map.getNeighbours(Arrays.asList(WALL, USER),region, Moving.FUNC)
		.parallelStream()
		.forEach(node -> graph.addMonkeyAndUserToGraph(node, region));
	}

}
