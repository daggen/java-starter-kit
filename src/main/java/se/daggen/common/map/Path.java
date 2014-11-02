package se.daggen.common.map;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Path<C> {
	
	private final Coordinate<C> currentStep;
	private final Optional<Path<C>> previousStep;
	private final int length;
	
	
	public Path(Coordinate<C> step) {
		this(step, Optional.empty());
	}
	
	private Path(Coordinate<C> step, Optional<Path<C>> previousPath) {
		currentStep = step;
		previousStep = previousPath;
		length = 1 + (previousPath.isPresent() ? previousPath.get().length : 0);
	}
			
	public int length() {
		return length;
	}
		
	public List<Coordinate<C>> getSteps() {
		if (previousStep.isPresent()) {
			List<Coordinate<C>> list = previousStep.get().getSteps();
			list.add(list.size(), currentStep);
			return  list;
		} else {
			List<Coordinate<C>> list = new LinkedList<>();
			list.add(currentStep);
			return list;
		}
	}
	
	public Path<C> addLastStep(Coordinate<C> step) {
		return new Path<>(step, Optional.of(this));
	}
	
	@Override
	public String toString() {
		return (previousStep.isPresent() ? previousStep.get().toString() : "") + currentStep;
	}

}
