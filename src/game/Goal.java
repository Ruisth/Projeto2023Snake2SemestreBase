package game;

import environment.Board;
import environment.LocalBoard;

import static environment.Board.countDownLatch;

public class Goal extends GameElement  {
	private int value=1;
	private Board board;
	public static final int MAX_VALUE=10;
	public Goal( Board board2) {
		this.board = board2;
	}
	public int getValue() {
		return value;
	}
	public void incrementValue() throws InterruptedException {
		value++;
	}

	public void captureGoal() throws InterruptedException {
		// Increment the goal's value
		incrementValue();
		System.out.println("Novo valor : " + this.value + " !!!!!! ");
		countDownLatch.countDown();
	}

	public void setValue(int value) {
		this.value = value;
	}
}
