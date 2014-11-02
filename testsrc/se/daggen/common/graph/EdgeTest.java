package se.daggen.common.graph;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class EdgeTest {
	
	private Node<Object, Object, Object> to = new Node<Object, Object, Object>(new Object());
	private Node<Object, Object, Object> from = new Node<Object, Object, Object>(new Object());
	public Edge<Object, Object, Object> edge =  new Edge<Object, Object, Object>(new Object(), from, to);

	@Test
	public void test() {
		boolean b = edge.isConnectedToNode(Arrays.asList(from));
		assertTrue(b);
		
		b = edge.isConnectedToNode(Arrays.asList(to));
		assertTrue(b);
		
		b = edge.isConnectedToNode(Arrays.asList(to, from));
		assertTrue(b);
	}

}
