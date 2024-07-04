package environment;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.*;

public class LocalBoard extends Board implements Serializable {
	
	private static final int NUM_SNAKES = 6;
	private static final int NUM_OBSTACLES = 25;
	private static final int NUM_SIMULTANEOUS_MOVING_OBSTACLES = 3;

	public LocalBoard() {		
		// TODO
		for(int i = 0; i < NUM_SNAKES; i++) {
			AutomaticSnake snake = new AutomaticSnake(i, this);
			snakes.add(snake);
		}
		// place game elements and snakes
		addObstacles(NUM_OBSTACLES);

		Goal goal=addGoal();
		System.err.println("All elements placed");
	}

	// synchronization in cell
	
	public void init() {
		// TODO
		// Start Threads
		// Create an ExecutorService with a fixed number of threads
		ExecutorService pool = Executors.newFixedThreadPool(NUM_SIMULTANEOUS_MOVING_OBSTACLES);

		// Submit tasks for each snake to the ExecutorService
		for (Snake snake : snakes) {
			snake.start();
		}

		// TODO: launch other threads
		for (Obstacle obs : obstacles) {
			ObstacleMover mover = new ObstacleMover(obs, this);
			pool.submit(mover);
		}
		pool.shutdown();
		setChanged();
	}

	
	
	public void removeSnake(BoardPosition position) {
//		TODO
	}

	public void stopSnakes() {
		for (Snake s : snakes) {
			s.interrupt();
			System.out.println("COBRAS PARADAS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
	}

	public void endGame() {
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//Matar threads e terminar jogo
		stopSnakes();

		isFinished = true;
		//O que fazer quando acabar
		System.err.println("GAME FINISHED!");
	}


	// Ignore these methods: only for remote players, which are not present in this project
	@Override
	public void handleKeyPress(int keyCode) {
		// do nothing... No keys relevant in local game
	}

	@Override
	public void handleKeyRelease() {
		// do nothing... No keys relevant in local game
	}



}
