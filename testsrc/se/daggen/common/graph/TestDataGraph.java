package se.daggen.common.graph;

import java.util.LinkedList;
import java.util.List;

public class TestDataGraph {
	
	public static final int NUMBER_OF_NODES = 9;

	public static final int NUMBER_OF_REGIONS = 3;

	public static final int NUMBER_OF_EDGES = 3;
	
	public static Graph<Object, Object, Object> graph;
	public static List<Object> nodes = new LinkedList<>();
	public static List<Object> regions = new LinkedList<>();

	static {
		graph = new Graph<>();
		
		for (int i = 0; i < NUMBER_OF_NODES; i++)
			nodes.add(new Object());
		
		for (int i = 0; i < NUMBER_OF_REGIONS; i++)
			regions.add(new Object());
	
		graph.addMonkeyAndUserToGraph(nodes.get(0), regions.get(0));
		graph.addMonkeyAndUserToGraph(nodes.get(3), regions.get(0));
		graph.addMonkeyAndUserToGraph(nodes.get(4), regions.get(0));
		
		graph.addMonkeyAndUserToGraph(nodes.get(1), regions.get(1));
		graph.addMonkeyAndUserToGraph(nodes.get(5), regions.get(1));
		graph.addMonkeyAndUserToGraph(nodes.get(6), regions.get(1));
		
		graph.addMonkeyAndUserToGraph(nodes.get(2), regions.get(2));
		graph.addMonkeyAndUserToGraph(nodes.get(7), regions.get(2));
		graph.addMonkeyAndUserToGraph(nodes.get(8), regions.get(2));
		
		graph.addEdge(new Object(), nodes.get(0), nodes.get(1));
		graph.addEdge(new Object(), nodes.get(0), nodes.get(5));
		graph.addEdge(new Object(), nodes.get(0), nodes.get(6));
		

		graph.addEdge(new Object(), nodes.get(1), nodes.get(2));

	}

}
