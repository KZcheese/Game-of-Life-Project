/*
 * Represents a 2D rectangular board using a 2D array of integers.
 * Written by Kevin Zhan
 * Last updated: 12/10/14
 */
public class Board {
	/*
	 * The board is stored in a rectangular int array called board.
	 * rows and cols are used to represent board.length and board[0].length respectively.
	 * rows and cols are final because the board is a set size that cannot change.
	 */
	private int[][] board;
	private final int rows;
	private final int cols;

	/*
	 * Constructors can take in the following sets of parameters: 
	 * An int row and int col value - makes an empty board with row rows and col columns. An
	 * int size that makes an empty square board with length and width size. A
	 * 2D int array - makes a board with all the values from the array.
	 * Precondition: parameter array must be rectangular.
	 */
	public Board(int rows, int cols) {
		board = new int[rows][cols];
		this.rows = rows;
		this.cols = cols;
	}

	public Board(int size) {
		this(size, size);
	}

	public Board(int[][] board) {
		this.board = copyArray(board);
		rows = board.length;
		cols = board[0].length;
	}

	/*
	 * Returns int the value at location (row, col).
	 */
	public int get(int row, int col) {
		return board[row][col];
	}

	/*
	 * Sets the value at location (row, col) to val and returns the value replaced.
	 */
	public int set(int row, int col, int val) {
		int replaced = get(row, col);
		board[row][col] = val;
		return replaced;
	}

	/*
	 * Sets the value at location (row, col) to 0 and returns the value replaced.
	 */
	public int remove(int row, int col) {
		return set(row, col, 0);
	}

	/*
	 * Returns a copy of the inputed 2D array.
	 */
	private int[][] copyArray(int[][] in) {
		int[][] out = new int[in.length][in[0].length];
		for (int i = 0; i < in.length; i++)
			for (int j = 0; j < in[i].length; j++)
				out[i][j] = in[i][j];
		return out;
	}

	/*
	 * Creates a new Board object with the same attributes as the current one.
	 */
	public Board clone() {
		return new Board(board);
	}

	/*
	 * Checks to see if the point at (row, col), exists and return true if it does.
	 * Returns false if value does not exist.
	 */
	public boolean isValid(int row, int col) {
		try {
			get(row, col);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Prints a grid of integers representing the current Board object.
	 */
	public String toString() {
		String out = "";
		for (int[] array : board) {
			for (int val : array)
				out += val + " ";
			out += "\n";
		}
		return out;
	}

	/*
	 * Returns the rows value from the private data.
	 */
	public int getRows() {
		return rows;
	}

	/*
	 * Returns the cols value from the private data.
	 */
	public int getCols() {
		return cols;
	}

	/*
	 * Checks to see if the board is entirely made of 0 values.
	 */
	public boolean isEmpty() {
		boolean isEmpty = true;
		for (int[] row : board)
			for (int val : row)
				if (val != 0) {
					isEmpty = false;
					break;
				}
		return isEmpty;
	}

	//Tests
	public static void main(String[] args) {
		Board board = new Board(5, 6);
		System.out.println(board);
		board.set(4, 5, 1);
		System.out.println(board.get(4, 5));
		System.out.println(board.isValid(4, 5));
		// System.out.print("Tro");
		// while (true) {
		// System.out.print("lo");
		// try {
		// Thread.sleep(1000 / 60);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
	}
}
