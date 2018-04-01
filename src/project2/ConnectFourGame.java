/***********************************************************************
 A Java program that simulates the Connect Four game. The game can be 
 played with 1 player against AI or between 2 players. The player(s)
 select a column to drop a "chip" into, stacking the chips with the goal
 of getting four of their chips to line up ina row: horizontally, 
 vertically, diagonally up, or diagonally down.

 @author(s) Samuel Ventocilla, Marshall Gallt
 @version Winter 2018
 ***********************************************************************/
package project2;

import java.util.Random;

public class ConnectFourGame {

	/** array thesize of selected board and holds the player values
	in selected spaces*/
	private int [][] board;

	/** size of the board, board is square */
	private int size;

	/** value represents which players turn it is */
	private int player;

	/** represents which player won the last game, -1 suggests no winner
	 */
	private int winner = -1;

	/** a random value used to select who plays first */
	private Random rand = new Random();

	/** value that describes PLAYER1 */
	public static final int PLAYER1 = 0;

	/** value that describes PLAYER2 */
	public static final int PLAYER2 = 1; 

	/** value that describes COMPUTER */
	public static final int COMPUTER = 1;

	/** value that describes no player */
	public static final int EMPTY = -1;

	/*******************************************************************
	Get the current players turn in the actionPerformed method used to
	move to next players turn, set the winner, decide chip animation,
	and determine correct values in board.
	@return player 
	 *******************************************************************/
	public int getPlayer() {
		return player;
	}

	/*******************************************************************
	Get the current players turn in the actionPerformed method used to
	move to next players turn, set the winner, decide chip animation,
	and determine correct values in board.
	@return player 
	 *******************************************************************/
	public ConnectFourGame(int pSize) {
		this.size = pSize;
		this.board = new int[pSize][pSize];
		for(int w = 0; w< this.size; w++) 
			for(int h = 0; h < this.size; h++) 
				board[w][h] = EMPTY;

		/** Sets the winner the player that will take the first turn */
		this.player = PLAYER1;
	}

	/*******************************************************************
	Places a chip in the selected column and determines the proper
	vertical placement of the chip in that column by checking amount of
	chips already in that column and adding 1. Ensure chip is as far 
	down as possible and set value of placement in board to current 
	player.
	@param col
	@return h  
	 *******************************************************************/
	public int selectCol(int col) {
		for(int h = 0; h < size; h++) 
			if(board[col][h] == -1) {
				board[col][h] = player;
				return h;
			}
		return -1;
	}

	/*******************************************************************
	Resets the board for another game by setting all values in board to
	EMPTY and selecting the player who placed last this game
	will place first next game.
	@return none
	 *******************************************************************/
	public void reset() {
		this.winner = PLAYER1;
		//		this.player = PLAYER1;
		for(int w = 0; w < size; w++) 
			for(int h = 0; h < size; h++) 
				board[w][h] = -1;
	}

	/*******************************************************************
	Based off of the current player value switches to the other player
	and returns new player.
	@return player 
	 *******************************************************************/
	public int switchPlayer() {
		player = (player + 1) % 2; 
		return player;
	}

	/*******************************************************************
	Uses the verticalWinner, horizontalWinner, and diagonalWinner
	methods to check if the current player has gotten four chips in a 
	row. Based off the results it will either; display a winner, 
	set the winner to the current player, and return true; or return 
	false allowing for the game to continue.
	@return boolean 
	 *******************************************************************/
	public boolean isWinner() {
		if(verticalWinner(this.player)) {
			System.out.println("Vertical Winner");
			this.winner = this.player;
			return true;
		}
		else if(horizontalWinner(this.player)){
			System.out.println("Horizontal Winner");
			this.winner = this.player;
			return true;
		}
		else if(diagonalWinner(this.player)) {
			this.winner = this.player;
			System.out.println("Diagonal Winner");
			return true;
		}
		else
			return false;
	}

	/*******************************************************************
	Checks if current player has won diagonally in either direction. By
	checking for one spot with player chip and either going 1 to the
	left and 1 down, or 1 to the right and 1 up. Checks from all corners
	of the board and stays within bounds of array.
	@param player 
	@return boolean
	 *******************************************************************/
	public boolean diagonalWinner(int player) {
		int count = 0;
		int tempHeight;
		int tempWidth;
		int h = 0;
		int w = 0;

		/**Used to alternate direction of diag*/
		for(int dir = -1; dir < 2; dir++) {   
			if(dir != 0) {

				/**loop to check the top left corner of board*/
				for(h = this.size - 1, w = 0; h > 0; h--) 

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
						if(board[tempWidth][tempHeight] == player) 
							count++;

						/**breaks streak of chips in a row*/
						else 
							count = 0;

						/**returns true if four or more chips are in a row*/
						if(count >= 4) 
							return true;
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
						if(board[tempWidth][tempHeight] == player) 
							count++;

						/**breaks streak of chips in a row*/
						else 
							count = 0;

						/**returns true if four or more chips are in a row*/
						if(count >= 4) 
							return true;
					}
				}

				/**loop to check the top right corner of board*/
				for(h = this.size - 1, w = this.size-1; h > 0; h--) 

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

						if(board[tempWidth][tempHeight] == player) 
							count++;

						/**breaks streak of chips in a row*/
						else 
							count = 0;

						/**returns true if four or more chips are in a row*/
						if(count >= 4) 
							return true;
					}
			}
		}
		return false;
	}

	/*******************************************************************
	Begins in bottom left corner of board and checks for the value of
	that player. If that spot has selected players piece it checks the
	spot above it, continuing until it finds four in a row or a spot
	without that players piece. It will do the same thing for the next
	column to verify the player has won, true, or has not won, false.
	@param player 
	@return boolean
	 *******************************************************************/
	public boolean verticalWinner(int player) {
		int count = 0;

		/**increments through all of the columns*/
		for(int w = 0; w < this.size; w++) {
			count = 0;

			/**checks for four in a row in selected column*/
			for(int h = 0; h < this.size; h++) {
				if(board[w][h] == player) 
					count++;

				/**breaks streak of chips in a row*/
				else 
					count = 0;

				/**returns true if four or more chips are in a row*/
				if(count >= 4) 
					return true;
			}
		}
		return false;
	}

	/*******************************************************************
	Begins in bottom right corner of board and checks for the value of
	that player. If that spot has selected players piece it checks the
	spot to the right of it, continuing until it finds four in a row or
	a spot without that players piece. It will do the same thing for the
	next row to verify the player has won, true, or has not won, false.
	@param player 
	@return boolean
	 *******************************************************************/
	public boolean horizontalWinner(int player) {
		int count = 0;

		/**increments through all of the columns*/
		for(int h = 0; h < this.size; h++) {
			count = 0;

			/**checks for four in a row in selected column*/
			for(int w = 0; w < this.size; w++) {
				/**returns true if four or more chips are in a row*/
				if(count >= 4) 
					return true;

				if(board[w][h] == player) 
					count++;

				/**breaks streak of chips in a row*/
				else 
					count = 0;

				/**returns true if four or more chips are in a row*/
				if(count >= 4) 
					return true;
			}
		}
		return false;
	}

	/*******************************************************************
	Uses verticalAI, horizontalAI, and diagAI to select what column it
	will place a chip. It first checks to see if there are any places
	it can win, checks to see if the player is about to win, then
	attempts to set itself for the next turn. The AI is a simpleton and
	will place a chip randomly if it does not have a chip with exposed 
	perimeter.
	@return column
	 *******************************************************************/
	public int AIPlay() {
		int column = -1;

		/**AI play to win*/
		column = verticalAI(3, COMPUTER);
		if(column != -1) {
			System.out.println("four vertical Off");
			return column;
		}

		column = horizontalAI(3, COMPUTER);
		if(column != -1) {
			System.out.println("four horizontal Off");
			return column;
		}

		column = diagAI(3, COMPUTER);
		if(column != -1) {
			System.out.println("four vertical Off");
			return column;
		}

		/**AI play to stop opponent win*/
		column = verticalAI(3, PLAYER1);
		if(column != -1) {
			System.out.println("four vertical def");
			return column;
		}

		column = horizontalAI(3, PLAYER1);
		if(column != -1) {
			System.out.println("four horizontal def");
			return column;
		}

		column = diagAI(3, PLAYER1);
		if(column != -1) {
			System.out.println("four diag def");
			return column;
		}

		column = horizontalTwo(COMPUTER);
		if(column != -1) {
			System.out.println("get three in a row w/ spaces 2 row");
			return column;
		}

		column = twoSpacedHori(COMPUTER);
		if(column != -1) {
			System.out.println("get three in a row w/ spaces 2 spaced");
			return column;
		}


		column = horizontalTwo(PLAYER1);
		if(column != -1) {
			System.out.println("def three in a row w/ spaces 2 row");
			return column;
		}

		column = twoSpacedHori(PLAYER1);
		if(column != -1) {
			System.out.println("def three in a row w/ spaces 2 spaced");
			return column;
		}

		/**AI tries to set itself up to win*/
		for(int goal = 2; goal > 0; goal--) {
			column = horizontalAI(goal, COMPUTER);
			if(column != -1) 
				return column;

			column = diagAI(goal, COMPUTER);
			if(column != -1) 
				return column;
			
			column = verticalAI(goal, COMPUTER);
			if(column != -1) 
				return column;
		}

		int c = -1;
		int row = -1;

		while(!isValidSpot(c, size-1) && !resultsInLoss(c, row)) {
			c = (int) rand.nextGaussian() + 5;
			for(int check = 0; check < size; check++)
				if(board[c][check] == -1) {
					row = check;
					break;
				}
			if(!resultsInLoss(c, row)) {
				System.out.println("Gaussian choice");
				return c;
			}
			c = -1;
		}

		for(int col = 0; col < size; col++) {
			if(isValidSpot(col, size-1)) {
				System.out.println("Default");
				return col;
			}
		}

		return 0;
	}

	/*******************************************************************
	Beginning in bottom left corner checks for a vertical line of, goal,
	amount chips owned by selPlayer. Similar to the Winner methods but 
	allows for more parameters. If the parameters are satisfied it
	returns the column, w, that would extend the line vertically.
	@param goal
	@param selPlayer
	@return w 
	 *******************************************************************/
	public int verticalAI(int goal, int selPlayer) {
		int count = 0;

		//
		for(int w = 0; w < this.size; w++) {
			count = 0;

			/**checks for four in a row in selected column*/
			for(int h = 0; h < this.size; h++) {
				if(board[w][h] == selPlayer) 
					count++;

				/**breaks streak of chips in a row*/
				else 
					count = 0;

				/**returns column if there are "goal" chips in a row*/
				if(h+1 < size) 
					if(count >= goal && board[w][h+1] == -1) {
						if(goal > 2)
							return w;
						if(!resultsInLoss(w, h+1))
							return w;
					}
			}
		}
		return -1;
	}

	/*******************************************************************
	Beginning in bottom left corner checks for a horizontal line of, 
	goal, amount chips owned by selPlayer. If the parameters are 
	satisfied it returns the column that would extend the line 
	horizontally.
	@param goal
	@param selPlayer
	@return {integer}
	 *******************************************************************/
	//	public int horizontalAI(int goal, int selPlayer) {
	//		int missing = -1;
	//		int count;
	//
	//		for(int r = 0; r < size; r++) {
	//			for(int c = 0; c < size; c++) {
	//				count = 0;
	//				missing = -1;
	//				for(int offset = 0; offset < 4; offset++) {
	//					if(c + offset < size) {
	//						if(board[c + offset][r] == selPlayer) {
	//							count++;
	//						}
	//						if(board[c + offset][r] == -1) {
	//							//making sure there's a piece under it
	//							if(r - 1 < 0)
	//								missing = c + offset;
	//							else if(board[c+offset][r-1] != -1)
	//								missing = c + offset;
	//						}
	//					}
	//				}
	//
	//				if(count >= goal && missing > -1) {
	//					if(goal > 2)
	//						return missing;
	//					if(!resultsInLoss(r,c))
	//						return missing;
	//				}
	//			}
	//		}
	//		return -1;
	//	}

	/*******************************************************************
	Beginning in bottom left corner checks for a horizontal line of, 
	goal, amount chips owned by selPlayer. If the parameters are 
	satisfied it returns the column that would extend the line 
	horizontally.
	@param goal
	@param selPlayer
	@return {integer}
	 *******************************************************************/
	public int horizontalAI(int goal, int selPlayer) {
		int missing = -1;
		int count;

		for(int r = 0; r < size; r++) {
			for(int c = 0; c < size; c++) {
				count = 0;
				missing = -1;
				for(int offset = 0; offset < 4; offset++) {
					if(c + offset < size) {
						if(board[c + offset][r] == selPlayer) {
							count++;
						}
						if(board[c + offset][r] == -1) {
							//making sure there's a piece under it
							if(r - 1 < 0)
								missing = c + offset;
							else if(board[c+offset][r-1] != -1)
								missing = c + offset;
						}
					}
				}

				if(count >= goal && missing > -1) {
					if(goal > 2)
						return missing;
					if(!resultsInLoss(missing, r))
						return missing;
				}
			}
		}
		return -1;
	}

	/*******************************************************************
	Beginning in bottom right corner checks for a diagonal line of, 
	goal, amount chips owned by selPlayer. If the parameters are 
	satisfied it returns the column that would extend the line 
	diagonally.
	@param goal
	@param selPlayer
	@return {integer} 
	 *******************************************************************/
	public int diagAI(int goal, int selPlayer) {
		int count = 0;			//holds the number of pieces in a four pc series
		int missing = -1;
		int tempc = -1;
		int tempr = -1;

		for(int dir = -1; dir < 2; dir++) {
			if(dir != 0) {
				//Starts bottom left, checks up right
				for(int r = 0; r < size; r++) {
					for(int c = 0; c < size; c++){
						count = 0;
						missing = -1;
						tempr = -1;
						tempc = -1;
						for(int offset = 0; offset < 4; offset++) {
							int coffset = offset * dir;
							if(validParam(c + coffset, r + offset)){
								if(board[c + coffset][r + offset] == selPlayer) {
									count++;
								}
								else if(isValidSpot(c + coffset, r + offset)){
									tempc = c + coffset;
									tempr = r + offset;
									missing = c + coffset;
								}
							}
						}

						if(missing > -1 && count >= goal) {
							if(goal > 2)
								return missing;
							if(!resultsInLoss(tempc, tempr))
								return missing;
						}
					}
				}
			}
		}
		return -1;
	}

	public int horizontalTwo(int selPlayer) {
		int count = 0;

		/**increments through all of the columns*/
		for(int r = 0; r < this.size; r++) {
			count = 0;

			/**checks for "goal" in a row in selected row*/
			for(int c = 0; c < this.size; c++) {
				if(board[c][r] == selPlayer) 
					count++;

				/**breaks streak of chips in a row*/
				else 
					count = 0;

				/**returns column if "goal" chips are in a row*/


				if(c+1 < size && c-2 > -1)
					if(count >= 2 && board[c+1][r] == -1 &&	board[c-2][r] == -1) {
						if(r-1 == -1) 
							return c+1;
						if(r - 1 >= 0)
							if(board[c+1][r-1] != -1 && board[c-2][r-1] != -1)
								return c+1;
					}
			}
		}
		return -1;
	}

	/******************************************************************
	 * Catches two in a row with a space in between them, so that the
	 * the player can't get three in a row w/ nobody blocking either
	 * side
	 */
	public int twoSpacedHori(int selPlayer) {
		int count = 0;
		int missed = -1;
		int validSpots = 0;

		/**increments through all of the rows*/
		for(int r = 0; r < this.size; r++) {

			/**checks for "goal" in a row in selected row*/
			for(int c = 0; c < this.size; c++) {
				if(c + 4 < size)
					if(board[c][r] == -1 && isValidSpot(c,r))
						if(board[c+1][r] == selPlayer)
							if(board[c+2][r] == -1 && isValidSpot(c+2,r))
								if(board[c+3][r] == selPlayer)
									if(board[c+4][r] == -1 && isValidSpot(c+4,r)) {
										if(!resultsInLoss(c+2, r))
											return c+2;
										else if(!resultsInLoss(c+4, r))
											return c+4;
										else if(!resultsInLoss(c, r))
											return c;
									}
				
			}
		}
		return -1;
	}
	
//	count = 0;
//	missed = -1;
//	validSpots = 0;
//	for(int inc = 0; inc < 5; inc++) {
//		if(c + inc < size) {
//			if(board[c + inc][r] == selPlayer)
//				count++;
//			else if(isValidSpot(c + inc, r)){
//				missed = c + inc;
//				validSpots++;
//			}
//		}
//	}
//	if(missed > -1 && count >= 2 && validSpots >2) 
//		return missed;


	public int twoSpacedDiag(int selPlayer) {
		int count = 0;			//holds the number of pieces in a four pc series
		int missing = -1;
		int validSpots = 0;

		for(int dir = -1; dir < 2; dir++) {
			if(dir != 0) {
				//Starts bottom left, checks up right
				for(int r = 0; r < size; r++) {
					for(int c = 0; c < size; c++){
						count = 0;
						missing = -1;
						validSpots = 0;
						for(int offset = 0; offset < 5; offset++) {
							int coffset = offset * dir;
							if(validParam(c + coffset, r + offset)){
								if(board[c + coffset][r + offset] == selPlayer) {
									count++;
								}
								else if(isValidSpot(c + coffset, r + offset)) {
									validSpots++;
									missing = c + coffset;
								}
							}
						}

						if(missing > -1 && count >= 2 && validSpots >= 3)
							return missing;
					}
				}
			}
		}
		return -1;
	}

	public boolean catsGame() {
		for(int c = 0; c < size; c++) {
			if(board[c][size-1] == -1)
				return false;
		}

		return true;
	}

	/*****************************************************************
	 * Check to make sure that the spot has a piece underneath it & is
	 * free
	 */
	private boolean isValidSpot(int c, int r) {
		if(c >= size || c < 0)
			return false;
		if(r >= size || r < 0)
			return false;
		if(board[c][r] == -1) {
			if(r - 1 < 0)
				return true;
			else if(board[c][r-1] != -1)
				return true;
		}

		return false;
	}


	/****************************************
	 * Determines if it's a valid parameter to make
	 * sure that it doesn't go outside the board
	 * @param c
	 * @param r
	 * @return
	 */
	private boolean validParam(int c, int r) {
		if(c < 0 || c >= size)
			return false;
		if(r < 0 || r >= size)
			return false;
		return true;
	}

	/***********************************
	 * Simulates the move & determines if it results in win for the other player
	 */
	private boolean resultsInLoss(int c, int r) {
		if(!validParam(c, r))
			return false;

		board[c][r] = COMPUTER;

		if(verticalAI(3, PLAYER1) > -1) {
			board[c][r] = -1;
			return true;
		}

		if(horizontalAI(3, PLAYER1) > -1) {
			board[c][r] = -1;
			return true;
		}

		if(diagAI(3, PLAYER1) > -1) {
			board[c][r] = -1;
			return true;
		}
		board[c][r] = -1;
		return false;
	}


}
