/*
 * A set of rules for The Game of Life on the Board class 
 * Written by Kevin Zhan
 * Last updated: 12/10/14
 */
import java.util.ArrayList;

public class GameOfLife {
	/*
	 * Contains a board currentGen that represents the current generation.
	 * int genNum represents the number of generations that have passed. Starts at 0.
	 * gens contains all previous Board states in the game.
	 * Alive cells are represented as 1s, and dead cells are represented as 0s. Any value above 1 is considered alive.
	 */
	private Board currentGen;
	private int genNum = 0;
	private ArrayList<Board> gens = new ArrayList<>();

	/*
	 * Constructor can take the following parameters:
	 * An int row and int col - creates an empty generation 0 Board with dimensions row and col.
	 * An int size - creates an empty square generation 0 Board with side length of size.
	 * A 2D Array of ints - creates a generation 0 Board with all the values from the array.
	 * A Board object - creates a generation 0 Board with all the values from the Board object.
	 */
	public GameOfLife(int rows, int cols) {
		currentGen = new Board(rows, cols);
		gens.add(currentGen);
	}

	public GameOfLife(int size) {
		this(size, size);
	}

	public GameOfLife(int[][] in) {
		currentGen = new Board(in);
		gens.add(currentGen);
	}

	public GameOfLife(Board in) {
		currentGen = in.clone();
		gens.add(currentGen);
	}

	/*
	 * Checks to see if currentGen has a value greater than 0 at location (row, col) and returns a boolean.
	 * If the location is not valid returns false;
	 */
	public boolean isAlive(int row, int col) {
		// System.out.println(row + ", " + col);
		if (currentGen.isValid(row, col) && currentGen.get(row, col) > 0)
			return true;
		return false;
	}

	/*
	 * Counts the number of neighbors around location (row, col) on currentGen and returns it as an int.
	 */
	public int countNeighbors(int row, int col) {
		int count = 0;
		for (int i = -1; i < 2; i++)
			for (int j = -1; j < 2; j++)
				if (isAlive(row + i, col + j) && !(i == 0 && j == 0))
					count++;
		return count;
	}

	/*
	 * Decides whether the location (row, col) would be alive in the nextGeneration based on the number of neighbors.
	 * Returns a boolean true in alive, and false if dead.
	 */
	public boolean willLive(int row, int col) {
		int neighbors = countNeighbors(row, col);
		if (neighbors == 3 || neighbors == 2 && isAlive(row, col))
			return true;
		return false;
	}

	/*
	 * Replaces currentGen with a new Board object and sets it to the next generation of the game.
	 * Stores the previous currentGen into gens.
	 * Increments genNum by one.
	 * If the board is empty, the method does nothing.
	 */
	public void nextGeneration() {
		if (!currentGen.isEmpty()) {
			Board nextGen = new Board(currentGen.getRows(),
					currentGen.getCols());
			for (int i = 0; i < nextGen.getRows(); i++)
				for (int j = 0; j < nextGen.getCols(); j++)
					set(i, j, willLive(i, j), nextGen);
			genNum++;
			gens.add(nextGen);
			currentGen = nextGen;
		}
	}

	/*
	 * Reverts the currentGen back to the previous generation.
	 * Removes the now currentGen board from gens.
	 * Decrements genNum by one.
	 */
	public void previousGeneration() {
		if (genNum > 0) {
			gens.remove(gens.size() - 1);
			currentGen = gens.get(gens.size() - 1);
			genNum--;
		}
	}

	/*
	 * Sets the location (row, col) on currentGen to 1 if isAlive is true, and 0 if isAlive is false;
	 */
	public void set(int row, int col, boolean isAlive) {
		if (isAlive)
			currentGen.set(row, col, 1);
		else
			currentGen.set(row, col, 0);
	}

	/*
	 * Sets the location (row, col) on board to 1 if isAlive is true, and 0 if isAlive is false;
	 */
	public void set(int row, int col, boolean isAlive, Board board) {
		if (isAlive)
			board.set(row, col, 1);
		else
			board.set(row, col, 0);
	}

	/*
	 * Returns genNum from private data.
	 */
	public int getGenNum() {
		return genNum;
	}

	/*
	 * Returns the toString() of currentGen, along with labeled counters for the number of alive, dead, and total cells in a single String.
	 */
	public String toString() {
		String out = "";
		int numAlive = 0;
		int numDead = 0;
		int numTotal = 0;
		out += currentGen.toString() + "\nGeneration: " + genNum;
		for (int i = 0; i < currentGen.getRows(); i++)
			for (int j = 0; j < currentGen.getCols(); j++) {
				if (currentGen.get(i, j) == 0)
					numDead++;
				else
					numAlive++;
				numTotal++;
			}
		out += ("\nNumber of Alive Cells: " + numAlive
				+ "\nNumber of Dead Cells: " + numDead
				+ "\nNumber of Total Cells: " + numTotal);
		return out;
	}

	// Tests
	public static void main(String[] args) {
		// test
		GameOfLife test = new GameOfLife(new int[][] { { 2, 0, 9, 0 },
				{ 0, 0, 1, 0 }, { 3, 0, 0, 0 } });
		System.out.println(test.toString());
		// test.set(2, 3, false);
		// System.out.println(test.toString());
		// test.countNeighbors(0, 0);
		test.nextGeneration();
		System.out.println(test.toString());
		test.previousGeneration();
		System.out.println(test.toString());
	}
}
