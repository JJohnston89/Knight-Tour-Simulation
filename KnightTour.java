import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;


/**
 * The KnightTour Class has the methods to solve the knight tour puzzle on a 5x5 chess board.
 * It solves the puzzle by using a Depth First Search on the chess board.
 * Date: 02/01/2019 
 * @author Joshua Johnston
 * @version 1.0
 *
 */
public class KnightTour {
	
	private Knight[][] game_board;
	private int knightCount;
	private Knight[] tour;
 	
	
	/**
	 * A constructor that makes a new KnightTour with setting up the chess board, and an array to hold the tour.
	 */
	
	public KnightTour(){
		
		game_board = new Knight[5][5];
		tour = new Knight[25];
		knightCount = 0;
		
	}
	
	/**
	 * The depth first search method starts at the given cell on the chess board, 
	 * and tries to find a path of visiting each cell following the knight's constraints.
	 * It will try every possible move until there is no more moves to make.
	 * @param row : int
	 * @param column : int
	 * @return tour : Knight[] or null
	 */
	
	public Knight[] depthFirstSearch(int row, int column){
		
		Stack<Knight> stack = new Stack<Knight>();
		
		Knight first = new Knight(row, column);
		first.setCurrent(true);		
		insert(first);             //inserting the knight onto the chess board
		
		stack.push(first);		
		
		while(!stack.isEmpty()){
			
			/*
			 * Used for debugging
			 * display();
			 * pause();
			 * 				
			*/
			
			if(knightCount == 25){
				
				/*
				 * Used for debugging
				 * System.out.println("Done");
				 * while(!stack.empty()){
				 *		Knight temp = stack.pop();
				 *		temp.display();
				 *  } 
				 */				
				
				int i = tour.length - 1;
				
				while(!stack.empty()){
					
					tour[i--] = stack.pop();  //getting the tour moves
					
				}
				return tour;
			}
			
			Knight next = getNextMove(stack.peek());  //getting the next knight 
			
			if(next != null){
				stack.peek().setCurrent(false);
				next.setCurrent(true);				
				insert(next);
				stack.push(next);
			}
			else{				
				Knight temp;
				temp = stack.pop();
				remove(temp);         //removing the knight from the board since there was no more moves to make
				
				if(!stack.empty())
					stack.peek().setCurrent(true);
			}
		}
		//System.out.println("No tour");
		return null;		
	}
	
	/**
	 * The getNextMove method checks the 8 possible moves a knight can make and by following the rules 
	 * that a move has to be within the bounds of the chess board, the cell has to be empty, 
	 * and the cell has not been visited by the current knight.
	 * @param current : Knight
	 * @return new Knight or null
	 */
	
	private Knight getNextMove(Knight current){
		
		int x_right = current.getColumn() + 2;
		int x_left = current.getColumn() - 2;
		int y_up = current.getRow() - 2;
		int y_down = current.getRow() + 2;
		
		if((x_right > -1 && x_right < 5) && (current.getRow() - 1 > -1 && current.getRow() - 1 < 5) && game_board[current.getRow() - 1][x_right] == null && current.get(current.getRow() - 1, x_right) == 0){
			
			current.insert(current.getRow() - 1, x_right);
			return new Knight(current.getRow() - 1, x_right);
		}
		else if((x_right > -1 && x_right < 5) && (current.getRow() + 1 > -1 &&current.getRow() + 1 < 5) && game_board[current.getRow() + 1][x_right] == null && current.get(current.getRow() + 1, x_right) == 0){
			current.insert(current.getRow() + 1, x_right);
			return new Knight(current.getRow() + 1, x_right);
		}
		else if((x_left > -1 && x_left < 5) && (current.getRow() - 1 > -1 && current.getRow() - 1 < 5) && game_board[current.getRow() - 1][x_left] == null && current.get(current.getRow() - 1, x_left) == 0){
			current.insert(current.getRow() - 1, x_left);
			return new Knight(current.getRow() - 1, x_left);
		}
		else if((x_left > -1 && x_left < 5) && (current.getRow() + 1 > -1 && current.getRow() + 1 < 5) && game_board[current.getRow() + 1][x_left] == null && current.get(current.getRow() + 1, x_left) == 0){
			current.insert(current.getRow() + 1, x_left);
			return new Knight(current.getRow() + 1, x_left);
		}
		else if((y_up > -1 && y_up < 5) && (current.getColumn() - 1 > -1 && current.getColumn() - 1 < 5) && game_board[y_up][current.getColumn() - 1] == null && current.get(y_up, current.getColumn() - 1) == 0){
			current.insert(y_up, current.getColumn() - 1);
			return new Knight(y_up, current.getColumn() - 1);
		}
		else if((y_up > -1 && y_up < 5) && (current.getColumn() + 1 > -1 && current.getColumn() + 1 < 5) && game_board[y_up][current.getColumn() + 1] == null && current.get(y_up, current.getColumn() + 1) == 0){
			current.insert(y_up, current.getColumn() + 1);
			return new Knight(y_up, current.getColumn() + 1);
		}
		else if((y_down > -1 && y_down < 5) && (current.getColumn() - 1 > -1 && current.getColumn() - 1 < 5) && game_board[y_down][current.getColumn() - 1] == null && current.get(y_down, current.getColumn() - 1) == 0){
			current.insert(y_down, current.getColumn() - 1);
			return new Knight(y_down, current.getColumn() - 1);
		}
		else if((y_down > -1 && y_down < 5) && (current.getColumn() + 1 > -1 && current.getColumn() + 1 < 5) && game_board[y_down][current.getColumn() + 1] == null && current.get(y_down, current.getColumn() + 1) == 0){
			current.insert(y_down, current.getColumn() + 1);
			return new Knight(y_down, current.getColumn() + 1);
		}
		else
			return null;		
	}
	
	/**
	 * The insert method adds a knight into the chess board
	 * @param key : Knight
	 */
	
	private void insert(Knight key){
		
		game_board[key.getRow()][key.getColumn()] = key;
		knightCount++;
	}
	
	/**
	 * The remove method removes a given knight from the chess board
	 * @param key : Knight
	 */
	
	private void remove(Knight key){		
		
		game_board[key.getRow()][key.getColumn()] = null;
		knightCount--;
	}
	
	/**
	 * The display method prints the chess board to the screen from its current state.
	 * 'K' is used for knight, 'C' is used for current knight, and '*' is used for empty cell.
	 * This was used to help with debugging. 
	 */
	
	@SuppressWarnings("unused")
	private void display(){
		
		for(int i = 0; i < game_board.length; i++){
			for(int j = 0; j < game_board.length; j++){
				
				if(game_board[i][j] != null && game_board[i][j].isCurrent() == false){
					System.out.print('K' + " ");
				}
				else if(game_board[i][j] != null && game_board[i][j].isCurrent()){
					System.out.print('C' + " ");
				}
				else{
					System.out.print('*' + " ");
				}				
			}
			System.out.println();
		}
	}
	
	/**
	 * The pause method is used to help with debugging.
	 * @throws IOException
	 */
	
	@SuppressWarnings("unused")
	private void pause() throws IOException{
		InputStreamReader input = new InputStreamReader(System.in);
		System.out.println("Enter any key to continue...");
		
		BufferedReader read = new BufferedReader(input);
		read.readLine();
	}
}
