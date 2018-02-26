package project2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ConnectFourPanel extends JPanel{
	private JLabel[][] board;      /** GUI visual for the game board model */
	private JButton[] selection;   /** buttons to select game columns */
	private JFrame gameFrame;
	private JFrame messageFrame;
	private JMenuItem gameItem;
	private JMenuItem quitItem;
	private ImageIcon iconBlank;
	private ImageIcon iconPlayer1;
	private ImageIcon iconPlayer2;
	private JPanel matrix;
	private JPanel selections;
	private JPanel other;
	
	private int bSize = 10;
	private String strBSize;
//	strBSize= JOptionPane.showInputDialog(null, "Enter board size", bSize);
//	bSize = Integer.parseInt(strBSize);
	public ConnectFourPanel() {
		iconBlank = new ImageIcon ("blank.png");
		iconPlayer1 = new ImageIcon ("player1.png");
		iconPlayer2 = new ImageIcon ("player2.png");
		
		
		
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
		
		ConnectFourGame game = new ConnectFourGame(bSize);
		
		gameFrame = new JFrame("Connect Four");
		gameFrame.setVisible(true);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		selections = new JPanel();
		selections.setBackground(Color.YELLOW);
		
		selection = new JButton[bSize];
		for(int i = 0; i < bSize; i++) {
			selection[i+1] = new JButton ();
		}
		
	}
	
	

}