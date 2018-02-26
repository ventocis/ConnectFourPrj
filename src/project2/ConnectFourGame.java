package project2;

import java.sql.ResultSet;
import java.util.Random;

public class ConnectFourGame {
	private int [][] board;
	private int size;
	private int player;
	private int winner = -1;
	private Random rand = new Random();
	public static final int PLAYER2 = 0;
	public static final int PLAYER1 = 1;
	public static final int EMPTY = -1;
	
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
		if(checkDiagonal(this.player)) {
			this.winner = this.player;
			return true;
		}
		else
			return false;

		
	}
	public boolean checkDiagonal(int player) {
		int count = 0;
		boolean cont = true;
		boolean next = false;
		while(cont) {
			for(int w = 0; w < this.size; w++) {
				for(int h = 0; h < this.size; h++) {
					if(board[w][h] == player) {
						count++;
						next = true;
						
					}
				}
			}
		}
		if(count >= 4) 
				return true;
		else
			return false;
	}
	
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
}
