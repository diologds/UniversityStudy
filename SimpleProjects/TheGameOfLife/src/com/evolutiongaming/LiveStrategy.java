package com.evolutiongaming;

public class LiveStrategy implements Strategy {

	@Override
	public boolean nextStatus(int liveNeighbours) {
		return liveNeighbours == 3 || liveNeighbours == 2;
	}

}
