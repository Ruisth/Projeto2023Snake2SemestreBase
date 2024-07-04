package game;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.swing.text.Position;

import environment.LocalBoard;
import gui.SnakeGui;
import environment.Cell;
import environment.Board;
import environment.BoardPosition;

public class AutomaticSnake extends Snake implements Serializable {

	private boolean state;

	public AutomaticSnake(int id, LocalBoard board) {
		super(id,board);
	}

	@Override
	public void run() {
		//TODO
		doInitialPositioning();
		try {
			sleep(10000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		state = true;
		System.err.println("initial size:" + cells.size());
		//TODO: automatic movement
		while (state) {
			try {
				sleep(Board.PLAYER_PLAY_INTERVAL);
				randomMove();
			} catch (InterruptedException e) {
				//System.out.println(currentThread() + ": " + e.toString());
				stopSnake();
			}
		}
	}

	public void randomMove() {
		try {
			// Get the current head cell of the snake
			Cell head = cells.getFirst();
			//head.lock.lock();
			Board board = getBoard();
			// Get available directions
			List<BoardPosition> availableDirections = board.getNeighboringPositions(head);
			double oldDistanceGoal = head.getPosition().distanceTo(board.getGoalPosition());
			double newDistanceGoal;
			Cell newCell = null;

			synchronized (this) {
				for (BoardPosition pos : availableDirections) {
					//Nova distância é igual à diferença entre a posição e o Objetivo
					newDistanceGoal = pos.distanceTo(board.getGoalPosition());

					// Verifica se a distância para onde se quer mover é menor que a de onde estava
					// Verifica que a snake não contém a posição para onde se quer mover
					if ( (!this.getCells().contains(board.getCell(pos))) && (newDistanceGoal <= oldDistanceGoal) ) {
						newCell = board.getCell(pos);
						System.out.println("Snake " + getIdentification() + " [" + getCells().getFirst().getPosition().x + "," + getCells().getFirst().getPosition().y + "]" + " moved to: " + newCell.getPosition());
					}
				}
			}
			//Call method move from Snake
            if (newCell != null) {
				move(newCell);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean isHumanSnake() {
		return false;
	}

	private void stopSnake(){
		state = false;
	}
	

	
}
