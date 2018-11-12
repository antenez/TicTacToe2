package tic.tac.toe.model;

/**
 * @author eno
 * Class representing position on board with coordinates. 
 * Rows are counted from top to bottom and columns are counted from left to right.
 */
public class Position {
	
	int row;
	int column;
	
	public Position(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	@Override
	public String toString() {
		return String.format("[%d,%d]", row, column);
	}

	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

}
