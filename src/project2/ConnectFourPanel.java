package project2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ConnectFourPanel extends JPanel implements ActionListener{
	private JLabel[][] board;      /** GUI visual for the game board model */
	private JButton[] selectionButt;   /** buttons to select game columns */
	private ConnectFourGame game;
	private GridLayout grLayout;

	private JFrame messageFrame;
	private JMenuItem gameItem;
	private JMenuItem quitItem;
	private JMenuItem toggleAIItem;
	private ImageIcon iconBlank;
	private ImageIcon iconPlayer1;
	private ImageIcon iconPlayer2;
	public int AI;
	public int column = 0;

	private int bSize = 10;
	private String strBSize;

	public ConnectFourPanel(JMenuItem pGItem, JMenuItem pQuItem,JMenuItem pToggleAIItem) {		
		iconBlank = new ImageIcon ("Pics/blank.png");
		iconPlayer1 = new ImageIcon ("Pics/player1.png");
		iconPlayer2 = new ImageIcon ("Pics/player2.png");

		toggleAIItem = pToggleAIItem;
		quitItem =pQuItem;
		gameItem = pGItem;

		AI = 0;



		strBSize = JOptionPane.showInputDialog(null, "Enter board size", bSize);
		try {
			bSize = Integer.parseInt(strBSize);
		}
		catch(NumberFormatException e) {
			bSize = 0;
		}

		if(bSize <= 3 || bSize >= 30) {
			JOptionPane.showMessageDialog(messageFrame, "You have not entered a board size between  3 and 30.\n" + "The board size will be set to 10.");
			bSize = 10;
		}

		board = new JLabel[bSize][bSize];
		game = new ConnectFourGame(bSize);

		grLayout = new GridLayout(bSize+1, bSize, 10, 10);

		setLayout(grLayout);

		toggleAIItem.addActionListener(this);
		quitItem.addActionListener(this);
		gameItem.addActionListener(this);

		selectionButt = new JButton[bSize];
		for(int i = 0; i < bSize; i++) {
			selectionButt[i] = new JButton ("Select");
			selectionButt[i].addActionListener(this);
			add(selectionButt[i]);
		}

		for(int row = bSize-1; row >= 0; row--) {
			for(int col = 0; col < bSize; col++) {
				board[col][row] = new JLabel(iconBlank);
				add(board[col][row]);
			}
		}

		setPreferredSize(new Dimension(1800,1600));

		setBackground(Color.white);


		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e){
		// TODO Auto-generated method stub
		JComponent src = (JComponent) e.getSource();

		if(src == toggleAIItem) {
			AI = (AI + 1) % 2;
		}

		if(AI == 0) {
			for(int w = 0; w < bSize; w++) {
				if(selectionButt[w] == src) {

					int h = game.selectCol(w);
					if(h == -1) {
						JOptionPane.showMessageDialog(null, "Column is filled");
						game.switchplayer();
					}
					else if(game.getPlayer() == 0){
						board[w][h].setIcon(iconPlayer1);
					}
					else {
						board[w][h].setIcon(iconPlayer2);
					}

					if(game.isWinner()) {
						JOptionPane.showMessageDialog(null, "Player " + game.getPlayer() + " Wins!");
						game.reset();
						for(int wi = 0; wi < bSize; wi++) {
							for(int hi = 0; hi < bSize; hi++) {
								board[wi][hi].setIcon(iconBlank);
							}
						}
					}

					game.switchplayer();
				}
			}
		}

		else if (AI != 0) {
			for(int w = 0; w < bSize; w++) {
				if(selectionButt[w] == src) {

					int h = game.selectCol(w);
					if(h == -1) {
						JOptionPane.showMessageDialog(null, "Column is filled");
						game.switchplayer();
					}
					else if(game.getPlayer() == 0){
						board[w][h].setIcon(iconPlayer1);
						
						if(game.isWinner()) {
							JOptionPane.showMessageDialog(null, "Player " + game.getPlayer() + " Wins!");
							game.reset();
							for(int wi = 0; wi < bSize; wi++) {
								for(int hi = 0; hi < bSize; hi++) {
									board[wi][hi].setIcon(iconBlank);
								}
							}
						}
						
						game.switchplayer();
					}

				}
			}

			if(game.getPlayer() == 1 && AI == 1) {
				int w = game.AIPlay();
				int h = game.selectCol(w);
				board[w][h].setIcon(iconPlayer2);
				
				if(game.isWinner()) {
					JOptionPane.showMessageDialog(null, "Player " + game.getPlayer() + " Wins!");
					game.reset();
					for(int wi = 0; wi < bSize; wi++) {
						for(int hi = 0; hi < bSize; hi++) {
							board[wi][hi].setIcon(iconBlank);
						}
					}
				}
				
				game.switchplayer();
			}
		}

		if(src == quitItem) {
			System.exit(1);
		}

		if(src == gameItem) {
			game.reset();
			for(int w = 0; w < bSize; w++) {
				for(int h = 0; h < bSize; h++) {
					board[w][h].setIcon(iconBlank);
				}
			}
		}
	}



}
