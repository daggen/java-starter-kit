package se.daggen.common.graph;

import static org.junit.Assert.*;

import org.junit.Test;

public class RegionTest {

	public Graph<Object, Object, Object> graph = TestDataGraph.graph;
	
	@Test
	public void test() {
		assertEquals(TestDataGraph.NUMBER_OF_REGIONS, graph.getRegions().size());
		graph.getRegions()
			.parallelStream()
			.forEach(region -> assertNotNull(region));
	}
	
	@Test
	public void testGetEdgesbetweenThisAnd() throws Exception {
		Node<Object, Object, Object> from = graph.getNode(TestDataGraph.nodes.get(0));
		Region<Object, Object, Object> toRegion = graph.getRegion(TestDataGraph.regions.get(1));
		
		int numberOf = toRegion.getEdgesbetweenThisAnd(from).size();
		
		assertEquals(3, numberOf);
	}

}
