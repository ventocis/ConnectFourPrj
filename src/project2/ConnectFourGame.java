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
	public static final int USER = 0;
	public static final int PLAYER2 = 1;
	public static final int COMPUTER = 1;
	public static final int EMPTY = -1;


	public int getPlayer() {
		return player;
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
	}


	/******************************************************************
	 * Sets the column which to enter the piece
	 * @param col
	 * @return
	 */

	public int selectCol(int col) {
		for(int h = 0; h < size; h++) {
			if(board[col][h] == -1) {
				board[col][h] = player;
				return h;
			}
		}
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
			System.out.println("Vertical Winner");
			this.winner = this.player;
			return true;
		}

		else if(horizontalWinner(this.player)){
			System.out.println("horizontal Winner");
			this.winner = this.player;
			return true;
		}

		else if(diagonalWinner(this.player)) {
			this.winner = this.player;
			System.out.println("Diag Winner");
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

		/**Used to alternate direction of diag*/
		for(int dir = -1; dir < 2; dir++) {   

			if(dir != 0) {

				/**loop to check the top left corner of board*/
				for(h = this.size - 1, w = 0; h > 0; h--) {

					/**checks for four in a row in selected diag*/
					for(int incr = 0; incr < this.size; incr++) {
						tempHeight = h + incr;
						tempWidth = w + dir * incr;

						/**Makes sure it doesn't go outside board*/
						if(tempHeight >= this.size || tempHeight < 0){ 
							count = 0;
							break;
						}

						if(tempWidth >= this.size || tempWidth  < 0){ 
							count = 0;
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


				/**checks for diags from 0,0*/
				for(w = 0, h = 0; w < this.size; w++) {
					count = 0;

					/**checks for four in a row in selected diag*/
					for(int incr = 0; incr < this.size; incr++) {
						tempHeight = h + incr;
						tempWidth = w + dir * incr;

						/**Makes sure it doesn't go outside board*/
						if(tempHeight >= this.size || tempHeight < 0){ 
							count = 0;
							break;
						}

						if(tempWidth >= this.size || tempWidth < 0){ 
							count = 0;
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

				/**loop to check the top right corner of board*/
				for(h = this.size - 1, w = this.size-1; h > 0; h--) {

					/**checks for four in a row in selected diag*/
					for(int incr = 0; incr < this.size; incr++) {
						tempHeight = h + incr;
						tempWidth = w + dir * incr;

						/**Makes sure it doesn't go outside board*/
						if(tempHeight >= this.size || tempHeight < 0){ 
							count = 0;
							break;
						}

						if(tempWidth >= this.size || tempWidth  < 0){ 
							count = 0;
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
		column = verticalAI(3, COMPUTER);
		if(column != -1) {
			return column;
		}

		column = horizontalAI(3, COMPUTER);
		if(column != -1) {
			return column;
		}

		column = diagAI(3, COMPUTER);
		if(column != -1) {
			return column;
		}

		/**AI play to stop opponent win*/
		column = verticalAI(3, USER);
		if(column != -1) {
			return column;
		}

		column = horizontalAI(3, USER);
		if(column != -1) {
			return column;
		}

		column = diagAI(3, USER);
		if(column != -1) {
			return column;
		}

		/**AI tries to set itself up to win*/
		for(int goal = 2; goal > 0; goal--) {
			column = verticalAI(goal, COMPUTER);
			if(column != -1) {
				return column;
			}

			column = horizontalAI(goal, COMPUTER);
			if(column != -1) {
				return column;
			}

			column = diagAI(goal, COMPUTER);
			if(column != -1) {
				return column;
			}
		}

		int i = rand.nextInt(this.size);
		while(board[i][this.size-1] != -1) {
			i = rand.nextInt(this.size);
		}
		return i;
	}

	/***goal is the desired number of chips in a row, for "selPlayer"*/
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
				if(h+1 < size) {
					if(count >= goal && board[w][h+1] == -1) {
						return w;
					}
				}
			}
		}

		return -1;
	}

	/***goal is the desired number of chips in a row, for "selPlayer"*/
	public int horizontalAI(int goal, int selPlayer) {
		int count = 0;

		/**increments through all of the columns*/
		for(int h = 0; h < this.size; h++) {
			count = 0;

			/**checks for "goal" in a row in selected column*/
			for(int w = 0; w < this.size; w++) {
				if(board[w][h] == selPlayer) {
					count++;
				}

				/**breaks streak of chips in a row*/
				else {
					count = 0;
				}

				/**returns column if "goal" chips are in a row*/
				if(w+1 >= size)
					break;
				if(count >= goal && board[w+1][h] == -1 && w+1 < this.size) {
					return w+1;
				}
			}

			/**checks for "goal" in a row in selected column*/
			for(int w = this.size-1; w >=0; w--) {
				if(board[w][h] == selPlayer) {
					count++;
				}

				/**breaks streak of chips in a row*/
				else {
					count = 0;
				}

				/**returns column if "goal" chips are in a row*/
				if(w-1 < 0)
					break;
				if(count >= goal && board[w-1][h] == -1 && w-1 < this.size) {
					return w-1;
				}
			}
		}

		return -1;
	}


	/***goal is the desired number of chips in a row, for "selPlayer"*/
	public int diagAI(int goal, int selPlayer) {
		int count = 0;
		int tempHeight, tempWidth;
		int h = 0;
		int w = 0;

		/**Used to alternate direction of diag*/
		for(int dir = -1; dir < 2; dir++) {   

			if(dir != 0) {

				/**loop to check the top left corner of board*/
				for(h = this.size - 1, w = 0; h > 0; h--) {

					/**checks for four in a row in selected diag*/
					for(int incr = 0; incr < this.size; incr++) {
						tempHeight = h + incr;
						tempWidth = w + dir * incr;

						/**Makes sure it doesn't go outside board*/
						if(tempHeight >= this.size || tempHeight < 0){ 
							count = 0;
							break;
						}

						if(tempWidth >= this.size || tempWidth  < 0){ 
							count = 0;
							break;
						}

						if(board[tempWidth][tempHeight] == COMPUTER) {
							count++;
						}

						/**breaks streak of chips in a row*/
						else {
							count = 0;
						}

						/**returns column if four or more chips are in a row*/
						if(count >= goal) {
							if(tempWidth + dir >= 0 && tempWidth + dir < size) {
								return tempWidth + dir;
							}
						}
					}
				}


				/**checks for diags from 0,0*/
				for(w = 0, h = 0; w < this.size; w++) {
					count = 0;

					/**checks for four in a row in selected diag*/
					for(int incr = 0; incr < this.size; incr++) {
						tempHeight = h + incr;
						tempWidth = w + dir * incr;

						/**Makes sure it doesn't go outside board*/
						if(tempHeight >= this.size || tempHeight < 0){ 
							count = 0;
							break;
						}

						if(tempWidth >= this.size || tempWidth < 0){ 
							count = 0;
							break;
						}

						if(board[tempWidth][tempHeight] == COMPUTER) {
							count++;
						}

						/**breaks streak of chips in a row*/
						else {
							count = 0;
						}

						/**returns true if four or more chips are in a row*/
						if(count >= goal) {
							if(tempWidth + dir >= 0 && tempWidth + dir < size) {
								return tempWidth + dir;
							}
						}
					}
				}

				/**loop to check the top right corner of board*/
				for(h = this.size - 1, w = this.size-1; h > 0; h--) {

					/**checks for four in a row in selected diag*/
					for(int incr = 0; incr < this.size; incr++) {
						tempHeight = h + incr;
						tempWidth = w + dir * incr;

						/**Makes sure it doesn't go outside board*/
						if(tempHeight >= this.size || tempHeight < 0){ 
							count = 0;
							break;
						}

						if(tempWidth >= this.size || tempWidth  < 0){ 
							count = 0;
							break;
						}

						if(board[tempWidth][tempHeight] == COMPUTER) {
							count++;
						}

						/**breaks streak of chips in a row*/
						else {
							count = 0;
						}

						/**returns true if four or more chips are in a row*/
						if(count >= goal) {
							if(tempWidth + dir >= 0 && tempWidth + dir < size) {
								return tempWidth + dir;
							}
						}
					}
				}
			}
		}

		return -1;
	}

	//	public int checkDiag(int player, int numChips) {
	//		int count = 0;
	//		int tempHeight, tempWidth;
	//		int h = 0;
	//		int w = 0;
	//
	//		for(int dir = -1; dir < 2; dir++) {
	//			if(dir != 0) {
	//				/**Checks for up and right diags*/
	//				/**loop to check the top left corner of board*/
	//				for(h = this.size - 1; h > 0; h--) {
	//	
	//					/**checks for four in a row in selected diag*/
	//					for(int incr = 0; incr < this.size; incr++) {
	//						tempHeight = h + dir * incr;
	//						tempWidth = w + dir * incr;
	//	
	//						/**Makes sure it doesn't go outside board height*/
	//						if(tempHeight >= this.size || tempHeight < 0){ 
	//							break;
	//						}
	//	
	//						/**Makes sure it doesn't go outside board width*/
	//						if(tempWidth >= this.size || tempWidth < 0) {
	//							break;
	//						}
	//	
	//						if(board[tempWidth][tempHeight] == player) {
	//							count++;
	//						}
	//	
	//						/**breaks streak of chips in a row*/
	//						else {
	//							count = 0;
	//						}
	//	
	//						/**returns true if four or more chips are in a row*/
	//						if(count >= numChips) {
	//							return count;
	//						}
	//					}
	//				}
	//	
	//	
	//				/**checks for diags from 0,0, up & right*/
	//				for(w = 0, h = 0; w < this.size; w++) {
	//					count = 0;
	//	
	//					/**checks for four in a row in selected diag*/
	//					for(int incr = 0; incr < this.size; incr++) {
	//						tempHeight = h + dir * incr;
	//						tempWidth = w + dir * incr;
	//	
	//						/**Makes sure it doesn't go outside board height*/
	//						if(tempHeight >= this.size || tempHeight < 0){ 
	//							break;
	//						}
	//	
	//						/**Makes sure it doesn't go outside board width*/
	//						if(tempWidth >= this.size || tempWidth < 0) {
	//							break;
	//						}
	//	
	//						if(board[tempWidth][tempHeight] == player) {
	//							count++;
	//						}
	//	
	//						/**breaks streak of chips in a row*/
	//						else {
	//							count = 0;
	//						}
	//	
	//						/**returns true if four or more chips are in a row*/
	//						if(count >= numChips) {
	//							return count;
	//						}
	//					}
	//				}
	//			}
	//		}
	//		
	//		return -1;
	//	}
	//	
	//	public int checkVertical(int player, int numChips) {
	//		int count = 0;
	//
	//		/**increments through all of the columns*/
	//		for(int w = 0; w < this.size; w++) {
	//			count = 0;
	//
	//			/**checks for four in a row in selected column*/
	//			for(int h = 0; h < this.size; h++) {
	//				if(board[w][h] == player) {
	//					count++;
	//				}
	//
	//				/**breaks streak of chips in a row*/
	//				else {
	//					count = 0;
	//				}
	//
	//				/**returns true if four or more chips are in a row*/
	//				if(count >= numChips) {
	//					return count;
	//				}
	//			}
	//		}
	//
	//		return -1;
	//	}
}