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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (currentStatus ? 1231 : 1237);
		result = prime * result + (futureStatus ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (currentStatus != other.currentStatus)
			return false;
		if (futureStatus != other.futureStatus)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cell [currentStatus=" + currentStatus + ", futureStatus="
				+ futureStatus + "]";
	}

}
