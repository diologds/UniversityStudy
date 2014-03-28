package com.evolutiongaming;

public class Cell {

	private boolean currentStatus;
	private boolean futureStatus;

	public Cell() {
		setCurrentStatus(false);
	}

	public Cell(boolean status) {
		setCurrentStatus(status);
	}

	public boolean getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(boolean currentStatus) {
		this.currentStatus = currentStatus;
	}

	public boolean getFutureStatus() {
		return futureStatus;
	}

	public void setFutureStatus(boolean futureStatus) {
		this.futureStatus = futureStatus;
	}
}
