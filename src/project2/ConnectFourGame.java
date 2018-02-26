package project2;
import java.awt.image.IndexColorModel;
import java.sql.ResultSet;
import java.util.Random;

import javax.xml.bind.annotation.XmlElementDecl.GLOBAL;

public class ConnectFourGame {
	private int [][] board;
	private int size;
	private int player;
	private int winner = -1;
	private Random rand = new Random();
	public static final int PLAYER2 = 0;
	public static final int PLAYER1 = 1;
	public static final int COMPUTER = 0;
	public static final int EMPTY = -1;
	
	public static void main(String[] args) {
		
		ConnectFourGame g1 = new ConnectFourGame(5);
		System.out.println("Player: " + g1.player + "| Column:" + g1.selectCol(5));
		g1.switchplayer();
		System.out.println("Player: " + g1.player + "| Column:" + g1.selectCol(0));
		g1.switchplayer();
		System.out.println("Player: " + g1.player + "| Column:" + g1.selectCol(1));
		g1.switchplayer();
		System.out.println("Player: " + g1.player + "| Column:" + g1.selectCol(6));
		System.out.println(g1.winner);
		g1.reset();
		System.out.println(g1.winner);	
	}
	
	public ConnectFourGame(int pSize) {
		this.size = pSize;
		this.board = new int[pSize][pSize];
		for(int w = 0; w< this.size; w++) {
			for(int h = 0; h < this.size; h++) 
				board[w][h] = EMPTY;
		}
//		 an if else statement that allows the winner of the last game
//		 to take the first turn
		if(winner != -1) 
			this.player = this.winner;
		else
			this.player = this.rand.nextInt(2);
		//col = -1;
	}
	
	
	/******************************************************************
	 * Sets the column which to enter the piece
	 * @param col
	 * @return
	 */
	
	//FIXME talk to prof
	public int selectCol(int col) {
		//this.col = col;
		if(col <= this.size && col >= 0)
			return col;
		else
			return -1;
	}
	
	public void reset() {
		this.winner = this.player;
		for(int w = 0; w < size; w++) {
			for(int h = 0; h < size; h++) {
			board[w][h] = -1;
			}
		}
	}
	
	public int switchplayer() {
		player = (player + 1) % 2;
		return player;
	}
	
	public boolean isWinner() {
		if(verticalWinner(this.player)) {
			this.winner = this.player;
			return true;
		}
		
		else if(horizontalWinner(this.player)){
			this.winner = this.player;
			return true;
		}
		
		else if(diagonalWinner(this.player)) {
			this.winner = this.player;
			return true;
		}
		else
			return false;

		
	}
	public boolean verticalWinner(int player) {
		int count = 0;
		
		/**increments through all of the columns*/
		for(int w = 0; w < this.size; w++) {
			count = 0;
			
			/**checks for four in a row in selected column*/
			for(int h = 0; h < this.size; h++) {
				if(board[w][h] == player) {
					count++;
				}
				
				/**breaks streak of chips in a row*/
				else {
					count = 0;
				}
				
				/**returns true if four or more chips are in a row*/
				if(count >= 4) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean diagonalWinner(int player) {
		int count = 0;
		int tempHeight, tempWidth;
		int h = 0;
		int w = 0;
		
		/**loop to check the top left corner of board*/
		for(h = this.size - 1; h > 0; h--) {
			
			/**checks for four in a row in selected diag*/
			for(int incr = 0; incr < this.size; incr++) {
				tempHeight = h + incr;
				tempWidth = w + incr;
				
				/**Makes sure it doesn't go outside board*/
				if(tempHeight >= this.size || tempWidth >= this.size){ 
					break;
				}
				
				if(board[tempWidth][tempHeight] == player) {
					count++;
				}
				
				/**breaks streak of chips in a row*/
				else {
					count = 0;
				}
				
				/**returns true if four or more chips are in a row*/
				if(count >= 4) {
					return true;
				}
			}
		}
		
		
		/**checks for diags from 0,0, up & left*/
		for(w = 0; w < this.size; w++) {
			count = 0;
			
			/**checks for four in a row in selected diag*/
			for(int incr = 0; incr < this.size; incr++) {
				tempHeight = h + incr;
				tempWidth = w + incr;
				
				/**Makes sure it doesn't go outside board*/
				if(tempHeight >= this.size || tempWidth >= this.size){ 
					break;
				}
				
				if(board[tempWidth][tempHeight] == player) {
					count++;
				}
				
				/**breaks streak of chips in a row*/
				else {
					count = 0;
				}
				
				/**returns true if four or more chips are in a row*/
				if(count >= 4) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean horizontalWinner(int player) {
		int count = 0;
		
		/**increments through all of the columns*/
		for(int h = 0; h < this.size; h++) {
			count = 0;
			
			/**checks for four in a row in selected column*/
			for(int w = 0; w < this.size; w++) {
				if(board[w][h] == player) {
					count++;
				}
				
				/**breaks streak of chips in a row*/
				else {
					count = 0;
				}
				
				/**returns true if four or more chips are in a row*/
				if(count >= 4) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public int AIPlay() {
		int column = -1;
		
		/**AI play to win*/
		column = verticalAI(1);
		if(column != -1) {
			return column;
		}
		
		column = horizontalAI(1);
		if(column != -1) {
			return column;
		}
		
		column = diagAI(1);
		if(column != -1) {
			return column;
		}
		
		/**AI play to stop opponent win*/
		column = verticalAI(3);
		if(column != -1) {
			return column;
		}
		
		column = horizontalAI(3);
		if(column != -1) {
			return column;
		}
		
		column = diagAI(3);
		if(column != -1) {
			return column;
		}
		
		/**AI tries to win*/
		for(int goal = 2; goal > 0; goal--) {
			column = verticalAI(goal);
			if(column != -1) {
				return column;
			}
			
			column = horizontalAI(goal);
			if(column != -1) {
				return column;
			}
			
			column = diagAI(goal);
			if(column != -1) {
				return column;
			}
		}
		return rand.nextInt(this.size);
	}
	
	public int verticalAI(int goal, int selPlayer) {
		int count = 0;
		
		/**increments through all of the columns*/
		for(int w = 0; w < this.size; w++) {
			count = 0;
			
			/**checks for four in a row in selected column*/
			for(int h = 0; h < this.size; h++) {
				if(board[w][h] == selPlayer) {
					count++;
				}
				
				/**breaks streak of chips in a row*/
				else {
					count = 0;
				}
				
				/**returns column if there are "goal" chips in a row*/
				if(count >= goal && board[w][this.size] == -1) {
					return w;
				}
			}
		}
		
		return -1;
	}
	
	public int horizontalAI(int goal, int selPlayer) {
		int count = 0;
		
		/**increments through all of the columns*/
		for(int h = 0; h < this.size; h++) {
			count = 0;
			
			/**checks for four in a row in selected column*/
			for(int w = 0; w < this.size; w++) {
				if(board[w][h] == selPlayer) {
					count++;
				}
				
				/**breaks streak of chips in a row*/
				else {
					count = 0;
				}
				
				//FIXME Can be improved to check for piece below it
				/**returns column if "goal" chips are in a row*/
				if(count >= goal && board[w+1][h] == -1 && w+1 < this.size) {
					return w+1;
				}
			}
		}
		
		return -1;
	}
	
	public int diagAI(int goal, int selPlayer) {
		int count = 0;
		int tempHeight, tempWidth;
		int h = 0;
		int w = 0;
		
		/**loop to check the top left corner of board*/
		for(h = this.size - 1; h > 0; h--) {
			
			/**checks for four in a row in selected diag*/
			for(int incr = 0; incr < this.size; incr++) {
				tempHeight = h + incr;
				tempWidth = w + incr;
				
				/**Makes sure it doesn't go outside board*/
				if(tempHeight >= this.size || tempWidth >= this.size){ 
					break;
				}
				
				if(board[tempWidth][tempHeight] == selPlayer) {
					count++;
				}
				
				/**breaks streak of chips in a row*/
				else {
					count = 0;
				}
				
				/**returns true if "goal" or more chips are in a row*/
				if(count >= goal && board[w+1][this.size-1] == -1) {
					return w+1;
				}
			}
		}
		
		
		/**checks for diags from 0,0, up & left*/
		for(w = 0; w < this.size; w++) {
			count = 0;
			
			/**checks for four in a row in selected diag*/
			for(int incr = 0; incr < this.size; incr++) {
				tempHeight = h + incr;
				tempWidth = w + incr;
				
				/**Makes sure it doesn't go outside board*/
				if(tempHeight >= this.size || tempWidth >= this.size){ 
					break;
				}
				
				if(board[tempWidth][tempHeight] == selPlayer) {
					count++;
				}
				
				/**breaks streak of chips in a row*/
				else {
					count = 0;
				}
				
				/**returns true if "goal" or more chips are in a row*/
				if(count >= goal && board[w+1][this.size-1] == -1) {
					return w+1;
				}
			}
		}
		
		return -1;
	}
}


check