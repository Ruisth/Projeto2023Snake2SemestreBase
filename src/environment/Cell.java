package environment;


import game.GameElement;
import game.Goal;
import game.Killer;
import game.Obstacle;
import game.Snake;
import game.AutomaticSnake;

import java.io.Serializable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cell implements Serializable {
	private BoardPosition position;
	private Snake ocuppyingSnake = null;
	private GameElement gameElement=null;

	public Lock lock = new ReentrantLock();
	public Condition lockCondition = lock.newCondition();

	public GameElement getGameElement() {
		return gameElement;
	}


	public Cell(BoardPosition position) {
		super();
		this.position = position;
	}

	public BoardPosition getPosition() {
		return position;
	}

	// request a cell to be occupied by Snake, If it is occupied by another Snake or Obstacle, wait.
	public  void request(Snake snake)
			throws InterruptedException {
		//TODO coordination and mutual exclusion
		try{
			lock.lock();
			while (ocuppyingSnake!=null){
				lockCondition.await();
			}
			ocuppyingSnake = snake;
		}finally {
			lock.unlock();
		}
		// TODO
	}

	public void release()
		throws InterruptedException {
				//TODO coordination and mutual exclusion
		try {
			lock.lock();
			ocuppyingSnake = null;
			gameElement = null;
			lockCondition.signalAll();
		}finally {
			lock.unlock();
		}
		// TODO
	}

	public boolean isOcupiedBySnake() {
		try{
			lock.lock();
			return ocuppyingSnake!=null;
		}finally {
			lock.unlock();
		}
	}

	@Override
	public String toString() {
		return "" + position;
	}

	public void setGameElement(GameElement element) {
		// TODO
		try{
			lock.lock();
			gameElement = element;
		}finally {
			lock.unlock();
		}
	}

	public boolean isOcupied() {
		// TODO
		return isOcupiedBySnake() || (gameElement!=null && gameElement instanceof Obstacle);
	}



	public Snake getOcuppyingSnake() {
		return ocuppyingSnake;
	}


	public Goal removeGoal() {
		// TODO
		try{
			lock.lock();
			gameElement = null;
			lockCondition.signalAll();
			return null;
		}finally {
			lock.unlock();
		}
	}
	public void removeObstacle() {
		// TODO
		try{
			lock.lock();
			gameElement = null;
			lockCondition.signalAll();
			System.err.println("REMOVI : " + getGameElement());
		}finally {
			lock.unlock();
		}
	}


	public Goal getGoal() {
		return (Goal)gameElement;
	}


	public boolean isOcupiedByGoal() {
		return (gameElement!=null && gameElement instanceof Goal);
	}


	public boolean isOccupiedByKiller() {
		return (gameElement!=null && gameElement instanceof Killer);
	}


	public boolean isOcupiedByObstacle() {
		return (gameElement!=null && gameElement instanceof Obstacle);
	}


	public void removeSnake(Snake snake) {
		// TODO
	}


	public boolean isEqual(Cell newCell) {
        return this.position.x == newCell.position.x && this.position.y == newCell.position.y;
    }

}
