package mainmenu;

import tictactoe.TicTacToe;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import tictactoe.TicTacToe;

public class MainMenu extends JFrame{
	
	public MainMenu() {
		setFrameAttributes();
		addGameButtons();
	}
	
	public void setFrameAttributes() {
		setTitle("Main Menu");
		setBounds(400, 400, 300, 500);
		setLayout( new GridLayout(0,1) );
		setVisible(true);
	}
	
	public void addGameButtons() {
		addTicTacToeButton();
	}
	
	public void addTicTacToeButton() {
		JButton tictactoebutton = new JButton("Tic Tac Toe");
		tictactoebutton.addActionListener(e-> new TicTacToe());
		add(tictactoebutton);
	}
	
}
