package com.evolutiongaming;

public class DeadStrategy implements Strategy {

	@Override
	public boolean nextStatus(int liveNeighbours) {
		return liveNeighbours == 3;
	}

}
