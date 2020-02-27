package hangman;

import java.awt.Color;
import java.awt.Component;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

public class Hangman extends JFrame {
	private Map<String, Component> componentMap;
	private List<URL> hangmanImages;
	private int guessCounter;
	private String guessWord;
	private String playerGuessWord;
	private String usedLetters;

	
	public Hangman() {
		componentMap = new HashMap<String, Component>();
		guessCounter = -1;
		loadHangmanImages();
		setMainFrameAttributes();
		addGameComponents();
	}

	
	private void setMainFrameAttributes() {
		setTitle("Hangman");
		setBounds(400, 400, 600, 500);
		setLayout(null);
		getContentPane().setBackground(Color.darkGray);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	
	private void loadHangmanImages() {
		try {
			hangmanImages = Arrays.asList(Hangman.class.getResource("/resources/1hangmanbase.png"),
					Hangman.class.getResource("/resources/2hangmanhead.png"),
					Hangman.class.getResource("/resources/3hangmanbody.png"),
					Hangman.class.getResource("/resources/4hangmanarm1.png"),
					Hangman.class.getResource("/resources/5hangmanarm2.png"),
					Hangman.class.getResource("/resources/6hangmanleg1.png"),
					Hangman.class.getResource("/resources/7hangmanleg2.png"));
		} catch (Exception e) {
			System.out.println("Initializing hangman images");
			e.printStackTrace();
		}
	}

	
	private void addGameComponents() {
		addHangedmanLabel();
		addWordLabel();
		addGuessTextField();
		addGuessButton();
		addGameStatusLabel();
	}

	
	private void addToComponentMap(Component component) {
		componentMap.put(component.getName(), component);
	}

	
	private Component getComponentFromMap(String name) {
		return componentMap.get(name);
	}

	
	/**
	 * Add a JLabel to display a image representing the current number of guesses
	 */
	private void addHangedmanLabel() {
		JLabel hangmanImage = new JLabel();
		hangmanImage.setName("HangmanLabel");
		add(hangmanImage);
		addToComponentMap(hangmanImage);

		try {
			// hangmanImage.setIcon(new ImageIcon(hangmanImages.get(0)) );
			incrementHangmanCounter();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Adding hangman window");
		}

		hangmanImage.setBounds(5, 5, hangmanImage.getIcon().getIconWidth(), hangmanImage.getIcon().getIconHeight());
	}

	
	private void incrementHangmanCounter() {
		guessCounter++;
		
		if (guessCounter > hangmanImages.size()-1) {
			lose();
		}

		JLabel label = (JLabel) getComponentFromMap("HangmanLabel");
		label.setIcon(new ImageIcon(hangmanImages.get(guessCounter)));
	}

	
	/**
	 * Add a JLabel that displays the word for guessing
	 */
	private void addWordLabel() {
		JLabel wordLabel = new JLabel();
		wordLabel.setName("WordLabel");
		add(wordLabel);
		addToComponentMap(wordLabel);
		wordLabel.setOpaque(true);
		wordLabel.setBackground(Color.WHITE);

		setGuessWord();
		JLabel hangmanImage = (JLabel) getComponentFromMap("HangmanLabel");
		wordLabel.setBounds(hangmanImage.getIcon().getIconWidth() + 25, 25, playerGuessWord.length() * 9, 50);
		setWordLabel();

		wordLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	private void setWordLabel() {
		JLabel wordLabel = (JLabel)getComponentFromMap("WordLabel");
		wordLabel.setText(playerGuessWord);
	}

	
	private void setGuessWord() {
		String[] words = {"Java", "Test", "Important"};
		guessWord = words[new Random().nextInt((words.length))];
		guessWord = guessWord.toUpperCase();
		setPlayerGuessString();
	}

	
	private void setPlayerGuessString() {
		playerGuessWord = "";
		for (int i = 0; i < guessWord.length(); ++i) {
			playerGuessWord += "_";
		}
		resizeWordLabel();
	}
	
	
	private void resizeWordLabel() {
		JLabel wordLabel = (JLabel)getComponentFromMap("WordLabel");
		JLabel hangmanImage = (JLabel) getComponentFromMap("HangmanLabel");
		wordLabel.setBounds(hangmanImage.getIcon().getIconWidth() + 25, 25, playerGuessWord.length() * 9, 50);
	}

	
	/**
	 * Add a JTextField used to get input for what character to guess
	 */
	private void addGuessTextField() {
		JTextField guessTextField = new JTextField();
		guessTextField.setName("GuessTextField");
		add(guessTextField);
		addToComponentMap(guessTextField);
		((AbstractDocument) guessTextField.getDocument()).setDocumentFilter(new TextLimitDocumentFilter(1));

		Component wordLabel = getComponentFromMap("WordLabel");
		guessTextField.setBounds(wordLabel.getX(), wordLabel.getY() + 100, 25, 25);
	}
	
	
	private void clearGuessTextField() {
		JTextField guessTextField = (JTextField)getComponentFromMap("GuessTextField");
		guessTextField.setText("");
	}

	
	/**
	 * Add a JButton to call Guess on click
	 */
	private void addGuessButton() {
		JButton guessButton = new JButton();
		guessButton.setName("GuessButton");
		add(guessButton);
		addToComponentMap(guessButton);
		guessButton.setText("Guess");

		JTextField guessTextField = (JTextField) getComponentFromMap("GuessTextField");
		guessButton.setBounds(guessTextField.getX() + 50, guessTextField.getY(), 75, 25);

		guessButton.addActionListener(x -> {
			if (guessTextField.getText().equals(""))
				return;
			
			guess(guessTextField.getText().toUpperCase().charAt(0));
			setWordLabel();
			if(checkWin()) {
				win();
				System.out.println("YOU HAVE WONNERED");
			}
		} );

	}
	
	
	/**
	 * Checks if the character is in the guessWord, increments the hangman if it isn't
	 * and updates playerGuessWord if it is.
	 * @param guess character to be checked
	 */
	private void guess(char guess) {
		int nextIndex = guessWord.indexOf(guess, 0);
		
		if(nextIndex == -1) {
			incrementHangmanCounter();
			return;
		}
		
		while (nextIndex != -1) {
			StringBuilder newString = new StringBuilder(playerGuessWord);
			newString.setCharAt(nextIndex, guessWord.charAt(nextIndex));

			playerGuessWord = newString.toString();
			nextIndex = guessWord.indexOf(guess, nextIndex+1);
		}
	}
	
	
	/**
	 * JLabel that displays text about the current game
	 */
	private void addGameStatusLabel() {
		JLabel gameStatus = new JLabel();
		gameStatus.setName("GameStatusLabel");
		add(gameStatus);
		addToComponentMap(gameStatus);
		gameStatus.setText("");
		gameStatus.setOpaque(true);
		gameStatus.setBackground(Color.WHITE);
		
		Component guessTextField = getComponentFromMap("GuessTextField");
		gameStatus.setBounds(guessTextField.getX(), guessTextField.getY() + 50, 150, 25);
	}
	
	
	private void setGameStatus(String status) {
		JLabel gameStatus = (JLabel)getComponentFromMap("GameStatusLabel");
		gameStatus.setText(status);
	}
	
	
	private boolean checkWin() {
		if(guessWord.compareTo(playerGuessWord) == 0)
			return true;
		return false;
	}
	
	
	private void win() {
		setGameStatus("You have won!");
		reset();
		clearGuessTextField();
	}
	
	
	private void lose() {
		setGameStatus("You have lost!");
		reset();
		clearGuessTextField();
	}

	
	private void reset() {
		setGuessWord();
		guessCounter=-1;
		incrementHangmanCounter();
		setGuessWord();
		setWordLabel();
	}
	
}
