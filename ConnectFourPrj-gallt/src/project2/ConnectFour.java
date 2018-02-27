package project2;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ConnectFour{

	public static void main(String[] args) {

		JMenuBar menuBar;
		JMenu fMenu;
		JMenuItem quItem;
		JMenuItem gItem;
		JMenuItem toggleAIItem;

		JFrame gameFrame = new JFrame("Connect Four");
		gameFrame.setVisible(true);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		fMenu = new JMenu("File");
		quItem = new JMenuItem("Quit Game");
		gItem = new JMenuItem("Start New Game");
		toggleAIItem = new JMenuItem("Toggle AI");
		
		fMenu.add(quItem);
		fMenu.add(gItem);
		fMenu.add(toggleAIItem);
		menuBar = new JMenuBar();
		gameFrame.setJMenuBar(menuBar);
		menuBar.add(fMenu);
		
		ConnectFourPanel panel1 = new ConnectFourPanel(gItem, quItem, toggleAIItem);
		
		gameFrame.getContentPane().add(panel1);
		gameFrame.pack();
		gameFrame.setVisible(true);
		
	}

}
