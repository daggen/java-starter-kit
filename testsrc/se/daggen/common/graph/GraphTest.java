package se.daggen.common.graph;

import static org.junit.Assert.*;

import org.junit.Test;

public class GraphTest {
	
	Graph<Object, Object, Object> graph = TestDataGraph.graph;

	@Test
	public void test() {
		assertEquals(graph.getNodes().size(), TestDataGraph.NUMBER_OF_NODES);
		assertEquals(graph.getRegions().size(), TestDataGraph.NUMBER_OF_REGIONS);
		assertEquals(graph.getEdges().size(), TestDataGraph.NUMBER_OF_EDGES);
	}
	
	@Test
	public void testNode() {

	}

}
