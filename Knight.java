
/**
 * The Knight Class holds the information of a knight piece on a 5x5 chess board.
 * It holds the position of the knight on the chess board, if it's the current knight, and keep track of moves;
 * Date: 02/01/2019 
 * @author Joshua Johnston
 * @version 1.0
 *
 */
public class Knight {
	
	private int row;
	private int column;	
	private boolean current;	
	private int[][] moves;
	
	/**
	 * A constructor to build a new knight with its position on the chess board.
	 * @param row : int
	 * @param column : int
	 */
	
	public Knight(int row, int column){
		this.row = row;
		this.column = column;		
		this.current = false;
		this.moves = new int[5][5];
	}
	
	/**
	 * A getter method for row.
	 * @return row : int
	 */
	
	public int getRow(){
		return row;
	}
	
	/**
	 * A getter method for column.
	 * @return column : int
	 */
	
	public int getColumn(){
		return column;
	}	
	
	/**
	 * A getter method for current
	 * @return current : boolean
	 */
	
	public boolean isCurrent(){
		return current;
	}	
	
	/**
	 * A setter method to set current
	 * @param flag : boolean
	 */
	
	public void setCurrent(boolean flag){
		this.current = flag;
	}
	
	/**
	 * The insert method adds a 1 to the matrix moves, showing that this move has been visited.
	 * @param row : int
	 * @param column : int
	 */
	
	public void insert(int row, int column){
		
		moves[row][column] = 1;
	}
	
	/**
	 * The get method returns a 0 (not visited) or a 1 (visited).
	 * @param row : int
	 * @param column : int
	 * @return 0 or 1 : int
	 */
	
	public int get(int row, int column){
		return moves[row][column];
	}
	
	/**
	 * The display method prints to the screen the position of this knight on the board. 
	 * This was used to help with debugging.  
	 */
	
	public void display(){
		
		System.out.println("( " + row + " , " + column + " )");
	}

}
