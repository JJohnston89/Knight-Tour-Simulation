import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.util.Duration;

/**
 * The TourApp Class is the main class that drives the program.
 * A GUI is created to display the chess board, and receive user input by mouse click. 
 * Animation was added for the simulation of the tour moves.
 * Date: 02/15/2019
 * @author Joshua Johnston
 * @version 1.0
 *
 */

public class TourApp extends Application{
	
	private Knight[] tourMoves;
	private ChessCell[][] table = new ChessCell[5][5];
	private Label status = new Label("Click a cell to start...");
	private Timeline time_line;
	
	public static void main(String[] args){
		Application.launch(args);		
		
	}
   
	/**
    * The start method creates the layout of the GUI and adds it to the stage.
    */
	@Override
	public void start(Stage primaryStage){
		
		primaryStage.setTitle("Knight Tour by Joshua Johnston");
		primaryStage.setResizable(false);
		GridPane chessBoardHolding = new GridPane();
		chessBoardHolding.setMinSize(300, 200);
		
		for(int row = 0; row < 5; row++){
			for(int col = 0; col < 5; col++){
				
				ChessCell cell = new ChessCell(row, col);
				table[row][col] = cell;                   //Filling up the table with ChessCells, so the cells can be updated.
				chessBoardHolding.add(cell, col, row);    //Filling up the grid pane with ChessCells, so the cells can be painted.				
			}
		}		
		
		Pane textPane = new Pane();
		textPane.setMinSize(300, 40);		
		textPane.getChildren().add(status);
		
		Button resetButton = new Button("Reset");
		resetButton.setOnMouseClicked( e -> {			
			if(time_line != null){						
				time_line.stop();
				time_line.getKeyFrames().clear();
			}
			
			for(int row = 0; row < 5; row++){
				for(int col = 0; col < 5; col++){						
					
					table[row][col].resetCell();
				}
			}				
			status.setText("Click a cell to start...");			
		});
		
		HBox hBox = new HBox();
		hBox.getChildren().addAll(textPane, resetButton);
		hBox.setSpacing(50);
		hBox.setAlignment(Pos.CENTER);
		
		BorderPane main = new BorderPane();		
		main.setCenter(chessBoardHolding);
		main.setBottom(hBox);		
		
		primaryStage.setScene(new Scene(main, 400, 400));		
		primaryStage.show();		
	}
	
	/**
	 * The turnOffMouseClicked method turns off the mouse event for each clickable cell.
	 */
	
	private void turnOffMouseClicked(){
		
		for(int row = 0; row < table.length; row++){
			for(int col = 0; col < table.length; col++){
				
				table[row][col].setOnMouseClicked(null);
			}
		}
	}	
	
	/**
	 * The buildKeyFrames method builds the frames for the animation time line.
	 * Given the tour moves, frames consists of a cell drawing a knight 
	 * and colors the border of the cell green to show the current knight. 
	 * A frame that consist of turning off the border color is inserted in between the frames above.
	 * 
	 */
	
	private void buildKeyFrames(){		
		
		time_line = new Timeline();
		
		for(int i = 0; i < 25; i++){
			
			int row = tourMoves[i].getRow();
			int col = tourMoves[i].getColumn();			
			
			table[row][col].orderNumber = i + 1;
			
			time_line.getKeyFrames().add(new KeyFrame(Duration.seconds(i), e -> {
				 
				table[row][col].draw();
				table[row][col].setStyle("-fx-border-color: green");				
				
			}));
			
			time_line.getKeyFrames().add(new KeyFrame(Duration.seconds(i + 1), e -> {
				 				
				table[row][col].setStyle(null);					
				
			}));			
					
		}		
	}
	
	
	/**
	 * The ChessCell Class is an inner class of the TourApp Class that handles the cells that are inserted into the grid pane.
	 * It handles setting up the background color based on if the cell's position is even or odd, the size of the cell, 
     * handling mouse clicks, drawing, and resetting the cell.
     * Date: 02/15/2019 
	 * @author Joshua Johnston
	 * @version 1.0
	 *
	 */
	class ChessCell extends StackPane{
		
		private Color cellColor;		
		private int row;
		private int col;
		private int orderNumber;
		
		/**
		 * A constructor that makes a new cell given a row and column index.
		 * The cell size is set, the background color is determined and set to the background.		
		 * @param row : int
		 * @param col : int
		 */
		
		public ChessCell(int row, int col){
			
			this.row = row;
			this.col = col;			
			
			this.setPrefSize(90,  90);
			this.setOnMouseClicked(e -> handleMouseClick());			
			
			if((row + col) % 2 == 0){				
				this.cellColor = Color.CORNSILK;
			}
			else{
				this.cellColor = Color.BURLYWOOD; 
			}
			
			this.setBackground((new Background(new BackgroundFill(cellColor, null, null))));			
		}
		
		/**
		 * The draw method paints a knight onto the cell and also paints a while colored number.
		 */
		
		public void draw(){
			Label number = new Label(Integer.toString(orderNumber));
			number.setTextFill(Color.WHITE);
			Image image = new Image("knight.png");
			ImageView paint = new ImageView(image);
			this.getChildren().addAll(paint, number);			
		}
		
		/**
		 * The handleMouseClick method on click passes the starting cell to depth first search to see if a tour exist or not,
		 * if the tour exists then the key frames are built for the animation, and prints the corresponding status message.  
		 */
		
		private void handleMouseClick(){			
			
			turnOffMouseClicked();        //Only one cell needs to be clicked until the reset button is pushed.
			status.setText("Getting tour...");
			
			KnightTour tour = new KnightTour();
			tourMoves = tour.depthFirstSearch(row, col);
			
			if(tourMoves != null){   //A tour exists
				
				buildKeyFrames();
				status.setText("Running tour...");
				
				time_line.play();     //Playing the animation				
				
				time_line.setOnFinished(e -> {					
					status.setText("The tour is done...");
				});				
			}
			else{
				status.setText("No tour exist");
			}			
		}
		
		/**
		 * The resetCell method clears the knight and number from the cell.
		 * It turns on the mouse clicked event as well.
		 */
		
		private void resetCell(){			
			
			this.getChildren().clear();
			this.setStyle(null);
			this.setOnMouseClicked(e -> handleMouseClick());
		}		
	}
}


