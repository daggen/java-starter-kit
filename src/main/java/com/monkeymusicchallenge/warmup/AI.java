package com.monkeymusicchallenge.warmup;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONArray;
import org.json.JSONObject;

import se.daggen.common.converter.Converter;
import se.daggen.common.graph.Edge;
import se.daggen.common.graph.Graph;
import se.daggen.common.map.Coordinate;
import se.daggen.common.map.Map;
import se.daggen.common.map.Path;
import se.daggen.monkeymusicchallange.graph.ConverterMapToGraph;
import se.daggen.monkeymusicchallange.graph.WeightFunc;
import se.daggen.monkeymusicchallange.map.ConverterJSONtoMap;
import se.daggen.monkeymusicchallange.map.ConverterStringToCoordinates;
import se.daggen.monkeymusicchallange.map.Direction;
import se.daggen.monkeymusicchallange.map.MapItem;
import se.daggen.monkeymusicchallange.map.Moving;

public class AI {

	private Optional<Map<MapItem, Integer>> map = Optional.empty();
	private Converter<String, Coordinate<Integer>> converterStringToCoordinate = new ConverterStringToCoordinates();
	private Optional<Graph<Coordinate<Integer>, Path<Integer>, Coordinate<Integer>>> graph = Optional
			.empty();
	private LinkedList<Edge<Coordinate<Integer>, Path<Integer>, Coordinate<Integer>>> edges;
	private Optional<Edge<Coordinate<Integer>, Path<Integer>, Coordinate<Integer>>> currentEdge = Optional
			.empty();
	private List<Coordinate<Integer>> currentPath;
	private JSONObject gameState;

	public String move(final JSONObject gameState) {
		this.gameState = gameState;

		if (!map.isPresent())
			loadMapAndGraph();

		String move = move();
		System.out.println(getMonkeysPosition());
		System.out.println(move);
		return move;
	}

	private void loadMapAndGraph() {
		final JSONArray currentLevelLayout = gameState.getJSONArray("layout");
		map = Optional.of(new ConverterJSONtoMap().convert(currentLevelLayout));
		graph = Optional.of(new ConverterMapToGraph().convert(map.get()));

		Optional<LinkedList<Edge<Coordinate<Integer>, Path<Integer>, Coordinate<Integer>>>> e = graph
				.get().getShortestPathWhichVisitAllRegions(
						getMonkeysPosition(), Coordinate.ofArgs(0, 0),
						WeightFunc.FUNC);
		edges = e.get();
	}

	private Coordinate<Integer> getMonkeysPosition() {
		final JSONArray currentPositionOfMonkey = gameState
				.getJSONArray("position");
		return converterStringToCoordinate.convert(currentPositionOfMonkey
				.toString());
	}

	private String move() {

		Optional<Coordinate<Integer>> closest = pickClosestItem();
		if (closest.isPresent()) {
			map.get().set(MapItem.EMPTY, closest.get());
			System.out.println("Picking points!");
			return Direction.getFirstDirection(getMonkeysPosition(),
					closest.get()).getName();
		}

		if (!currentEdge.isPresent()) {
			loadNextEdage();
		}

		if (currentPath.size() > 1) {
			Direction direction = getNextDirection();
			return direction.getName();
		} else
			return randomDirection();
	}

	private Optional<Coordinate<Integer>> pickClosestItem() {
		List<Coordinate<Integer>> possiblePossitions = Moving.FUNC.apply(getMonkeysPosition());

		return map.get().getAll(MapItem.NICE_ITEMS).parallelStream()
				.filter(item -> possiblePossitions.contains(item)).findAny();
	}

	private void loadNextEdage() {
		if (!edges.isEmpty())
			currentEdge = Optional.of(edges.removeFirst());
		else 
			currentEdge = Optional.empty();
		
		if (currentEdge.isPresent())
			loadNextPath();
	}

	private void loadNextPath() {
		currentPath = currentEdge.get().getContent().getSteps();
		Coordinate<Integer> monkeyPos = getMonkeysPosition();

		if (currentPath.get(currentPath.size() - 1).equals(monkeyPos)) {
			Collections.reverse(currentPath);
		}

		assert currentPath.get(0).equals(monkeyPos);
	}

	private Direction getNextDirection() {
		Direction nextDirection = Direction.getFirstDirection(
				currentPath.get(0), currentPath.get(1));
		currentPath.remove(0);
		if (currentPath.size() < 2)
			currentEdge = Optional.empty();

		return nextDirection;
	}

	private String randomDirection() {
		System.out.print("Random: ");
		return new String[] { "up", "down", "left", "right" }[ThreadLocalRandom
				.current().nextInt(1)];
	}
}
