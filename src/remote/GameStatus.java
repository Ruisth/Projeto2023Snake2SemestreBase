package remote;

import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import environment.LocalBoard;
import game.Obstacle;
import game.Snake;
import gui.SnakeGui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameStatus implements Serializable {
    private Cell[][] cells;
    private LinkedList<Snake> snakes;
    private LinkedList<Obstacle> obstacles;
    private Board board;

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public Board getBoard(){
        return board;
    }
    public void setBoard(Board board){
        this.board = board;
    }

    public LinkedList<Snake> getSnakes() {
        return snakes;
    }

    public void setSnakes(LinkedList<Snake> snakes) {
        this.snakes = snakes;
    }

    public LinkedList<Obstacle> getObstacles() {
        return obstacles;
    }

    public void setObstacles(LinkedList<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    public BoardPosition getGoalPosition() {
        return goalPosition;
    }

    public void setGoalPosition(BoardPosition goalPosition) {
        this.goalPosition = goalPosition;
    }

    private BoardPosition goalPosition;

    public GameStatus(Board board, Cell[][] cells, LinkedList<Snake> snakes, LinkedList<Obstacle> obstacles, BoardPosition goalPosition) {
        this.board = board;
        this.cells = cells;
        this.snakes = snakes;
        this.obstacles = obstacles;
        this.goalPosition = goalPosition;
        board.setChanged();
    }
}
