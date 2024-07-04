package remote;

import environment.Board;
import game.*;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import environment.Cell;

import static java.awt.event.KeyEvent.*;


/** Remote representation of the game, no local threads involved.
 * Game state will be changed when updated info is received from Srver.
 * Only for part II of the project.
 * @author luismota
 *
 */


public class RemoteBoard extends Board implements Serializable {

	private String direction;
	public RemoteBoard(){
	}

	public String getDirection() {
		return direction;
	}

	public void clearDirection() {
		direction = null;
	}

	@Override
	public void handleKeyPress(int keyCode) {
		switch (keyCode) {
			case VK_UP:
				direction = "UP";
				break;
			case VK_DOWN:
				direction = "DOWN";
				break;
			case VK_LEFT:
				direction = "LEFT";
				break;
			case VK_RIGHT:
				direction = "RIGHT";
				break;
			default:
				break;
		}
	}

	@Override
	public void handleKeyRelease() {
		//TODO
	}

	@Override
	public void init() {
		//TODO
		/*snakes.clear();
		for (Snake snake : snakes) {
			HumanSnake humanSnake = new HumanSnake(snake.getIdentification(), this);
			snakes.add(snake);
		}

		setChanged();
		notifyObservers();*/
	}
}
