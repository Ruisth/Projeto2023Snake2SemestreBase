package game;

import environment.Board;
import environment.BoardPosition;
import environment.LocalBoard;

import java.io.Serializable;

public class Obstacle extends GameElement implements Serializable {
	
	
	private static final int NUM_MOVES=3;
	static final int OBSTACLE_MOVE_INTERVAL = 400;
	private int remainingMoves=NUM_MOVES;
	Board board;
	private int x;
	private int y;

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Obstacle(Board board) {
		super();
		this.board = board;
	}
	
	public int getRemainingMoves() {
		return remainingMoves;
	}
	public void decrementRemainingMoves() {
		remainingMoves--;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public BoardPosition getPosition() {
		return new BoardPosition(x,y);
	}


	private void setPosition(BoardPosition newPosition) {
		x = newPosition.getX();
		y = newPosition.getY();

		board.getCell(newPosition);
	}
}
