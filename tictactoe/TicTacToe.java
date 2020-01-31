package tictactoe;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TicTacToe extends JFrame{

	private static final long serialVersionUID = 1L;
	
	//Game Variables
	private static int STARTINGTURN = 1;
	private final String[] PLAYERS = {"O","X"};
	int turnCounter;
	boolean gameOver = false;
	
	//Buttons
	JTextField txt;
	JLabel playerTurn;
	JButton reset;
	JButton[][] board;
	
	
	public TicTacToe () {
		turnCounter = STARTINGTURN;
		
		//frame
		setTitle("Tic Tac Toe");
		setBounds(400, 400, 600, 500);
		setLayout(null);
		
		//text field
		//txt = new JTextField();
		//add(txt);
		//txt.setBounds(400,100,100,50);
		
		//label
		playerTurn = new JLabel("Player 1's turn");
		add(playerTurn);
		playerTurn.setBounds(getWidth()-200, 0, 200, 100);
		
		//create board
		int x = 50, y = 50;
		board = new JButton[3][3];
		for( int i=0; i<3; ++i){
			for( int j=0; j<3; j++){
				board[i][j] = new JButton();
				add(board[i][j]);
				board[i][j].setBounds(x+(x*i), y+(y*j), 50, 50);
				board[i][j].addActionListener(new TTTSquareListener(i,j) );
			}
		}
		
		//reset button
		reset = new JButton();
		add(reset);
		reset.setText("Reset");
		reset.setBounds(getWidth()-150, getHeight()-100, 100, 50);
		reset.addActionListener(new ActionListener() {
		public void actionPerformed( ActionEvent e){
			reset();
			}
		});
		
		setVisible(true);
	}
	
	/*
	 * increments turnCounter and sets playerTurn label to the next person's turn 
	 * ends the game if too many turns elapsed
	 */
	public void incrementTurn() {
		turnCounter++;
		System.out.println(turnCounter);
		playerTurn.setText("Player " + PLAYERS[turnCounter%2] + "'s turn");
		if(turnCounter > 9)
			endGame();
	}
	
	/*
	 * Resets the game board, turn counter, and gameOver flag
	 */
	public void reset() {
		for( int i=0; i<3; ++i){
			for(int j=0; j<3; ++j)
			board[i][j].setText("");
		}
		gameOver=false;
		turnCounter=STARTINGTURN;
	}
	
	/*
	 * Checks if there is a winning combination for user @player
	 * @param player - current player
	 * @param row - row of most recent move
	 * @param col - column of most recent move
	 */
	public boolean checkVictory( String player, int row, int col)
	{
		System.out.print(turnCounter);
		
		int rowTotal = 0, colTotal=0, leftDiagTotal=0, rightDiagTotal=0;
		for(int i=0; i<3; ++i){
			
			if( board[row][i].getText().equals(player))
				rowTotal++;
				
			if(board[i][col].getText().equals(player))
				colTotal++;
			
			if(board[i][i].getText().equals(player))
				leftDiagTotal++;
			
			if(board[i][2-i].getText().equals(player))
				rightDiagTotal++;
		}
		
		if( rowTotal==3 ||colTotal==3 ||leftDiagTotal==3 ||rightDiagTotal==3)
			return true;
			
		return false;
	}
	
	/*
	 * Ends the game by setting the gameOver flag to true displays who wins
	 */
	public void endGame()
	{
		gameOver=true;
		if(turnCounter>9) 
			playerTurn.setText("Nobody wins");
		else 
			playerTurn.setText("Player " + PLAYERS[turnCounter%2] +" wins");
	}
	
	/*
	 * nested actionlistener inner class to to handle moves
	 */
	class TTTSquareListener implements ActionListener {
		private int row;
		private int col;
		
		public TTTSquareListener(int row, int col) {
			this.row=row;
			this.col=col;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
				if(gameOver)
					return;
				
				if(board[row][col].getText().contentEquals("")) {		
					board[row][col].setText(PLAYERS[(turnCounter%2)]); 
					if( checkVictory(PLAYERS[turnCounter%2], row,col) )
						endGame();
					else 
						incrementTurn();
				}
		}
	}
	
	
}

