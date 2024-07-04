package game;

import environment.Board;
import environment.LocalBoard;

public class ObstacleMover extends Thread {
	private Obstacle obstacle ;
	private LocalBoard board ;

	public ObstacleMover(Obstacle obstacle, LocalBoard board) {
		super();
		this.obstacle = obstacle;
		this.board = board;
	}
	
	@Override
	public void run() {
		//TODO
		try {
				while (obstacle.getRemainingMoves() > 0) {
					int moves = obstacle.getRemainingMoves();
					board.getCell(obstacle.getPosition()).removeObstacle();
					//board.getObstacles().remove();
					obstacle.decrementRemainingMoves();
					board.addGameElement(obstacle);
					board.setChanged();
					sleep(Obstacle.OBSTACLE_MOVE_INTERVAL);
				}

				// Aguarda o próximo intervalo

			} catch(InterruptedException e){
				e.printStackTrace();
			}
	}
}
