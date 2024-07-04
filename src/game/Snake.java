package game;

import java.io.Serializable;
import java.util.LinkedList;

import environment.LocalBoard;
import gui.SnakeGui;
import environment.Board;
import environment.BoardPosition;
import environment.Cell;

import static environment.BoardPosition.isValid;

public abstract class Snake extends Thread {

	private boolean killed = false ;
	protected LinkedList<Cell> cells = new LinkedList<Cell>();
	protected int size = 5;
	private int id;
	private Board board;

	public Snake(int id,Board board) {
		this.id = id;
		this.board=board;
	}

	
	public void killSnake () { killed = true ; }
	public boolean wasKilled () { return killed == true ;}
	
	public int getSize() {
		return size;
	}

	public int getIdentification() {
		return id;
	}

	public int getCurrentLength() {
		return cells.size();
	}
	
	public LinkedList<Cell> getCells() {
		return cells;
	}

	public void move(Cell newCell) throws InterruptedException {
		//TODO
		try {
			//lock aqui (acho que temos que bloquear todas as celulas ocupadas pela snake)
			System.out.println(this.getCells().getFirst().getGameElement());
			// Check if the new position is valid and available
			if (isValid(newCell.getPosition()) && !newCell.isOcupied()) {
				newCell.request(this);
				// Move the snake to the new cell
				cells.addFirst(newCell);
				if (cells.size() > size) {
					// Remove the tail cell if the snake exceeds its size limit
					Cell tail = cells.removeLast();
					tail.release();
				}
			}
			//Verificar se é goal e cresce a snake que come o goal
			if (newCell.isEqual(new Cell(board.getGoalPosition()))) {
				int value = newCell.getGoal().getValue();
				if (value <= 9) {
					newCell.getGoal().captureGoal();
					board.addGoal();
					size += value;
					board.setGoalValue(newCell.getGoal().getValue());

					//size = getSize() + value;
					System.err.println("Snake " + getIdentification() + " Current Size: " + size + " !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
				}
				if (value == 10) {
					newCell.removeGoal();
				}

			}
			// Update the GUI to reflect the snake's new position
			board.setChanged();
		} finally {
			//unlock aqui
		}
	}
	protected void doInitialPositioning() {
		//TODO
		int posX = 0;
		int posY = (int) (Math.random() * Board.HEIGHT);
		BoardPosition pos = new BoardPosition(posX, posY);

		try{
			Cell cell = board.getCell(pos);

			//Check if the cell is ocupied by another snake
			if(cell.getOcuppyingSnake() == null){
				cell.request(this);
				cells.add(cell);
				System.err.println("Snake "+getIdentification()+" starting at:"+getCells().getLast());
			}else{
				// The cell is occupied, handle accordingly
				System.err.println("Snake "+getIdentification()+" starting at:"+getCells().getLast());
			}
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
		//cells.add(board.getCell(pos));
		//System.err.println("Snake "+getIdentification()+" starting at:"+getCells().getLast());
	}
	
	public Board getBoard() {
		return board;
	}
	
	// Utility method to return cells occupied by snake as a list of BoardPosition
	// Used in GUI. Do not alter
	public synchronized LinkedList<BoardPosition> getPath() {
		LinkedList<BoardPosition> coordinates = new LinkedList<BoardPosition>();
		for (Cell cell : cells) {
			coordinates.add(cell.getPosition());
		}

		return coordinates;
	}	
}
