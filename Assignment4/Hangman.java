
/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {

	/*
	 * (non-Javadoc)
	 * 
	 * @see acm.program.ConsoleProgram#init()
	 * 
	 * add a graphic window next to the console.
	 */
	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
	}

	public void run() {
		/* You fill this in */
		println("Welcome to Hangman!");
		initGame();
		println(word);
		while (true) {
			if (isWin()) {
				winPrint();
				break;
			}
			if (isLose()) {
				losePrint();
				break;
			}
			printState();
			String guess = readLine("Your guess: ").toUpperCase();
			if (isValidGuess(guess)) {
				gameUpdate(guess.charAt(0));
			} else {
				println("Invalid input");
			}
		}

	}

	/*
	 * Initial the game
	 */
	private void initGame() {
		// Reset canvas
		canvas.reset();
		// A random integer generator.
		RandomGenerator rg = RandomGenerator.getInstance();
		word = lexicon.getWord(rg.nextInt(lexicon.getWordCount()));
		secretWord = initDashLine(word);
		numberOfWrongGuess = 0;
	}

	private void printState() {
		println("The word now looks like this: " + secretWord);
		int remainChance = NUMBER_OF_CHANCE - numberOfWrongGuess;
		println("You have " + remainChance + " guesses left.");
	}

	/*
	 * Initial the default dash line.
	 */
	private String initDashLine(String word) {
		String dash = "";
		for (int i = 0; i < word.length(); i++) {
			dash += "-";
		}
		return dash;
	}

	/*
	 * Check whether the input is a character or not
	 */
	private boolean isValidGuess(String guess) {
		return guess.length() == 1 && Character.isLetter(guess.charAt(0));
	}

	/*
	 * To update game state.
	 */
	private void gameUpdate(char guess) {
		int indexOfChar = word.indexOf(guess);
		if (indexOfChar != -1) {
			for (int i = 0; i < word.length(); i++) {
				if (word.charAt(i) == guess) {
					secretWord = secretWord.substring(0, i) + guess + secretWord.substring(i + 1);
				}
			}
			canvas.displayWord(secretWord);
			println("That guess is correct.");
		} else {
			println("There are no " + guess + "'s in the word.");
			numberOfWrongGuess++;
			canvas.noteIncorrectGuess(guess);
		}
	}

	private boolean isWin() {
		return secretWord == word;
	}

	private boolean isLose() {
		return numberOfWrongGuess == NUMBER_OF_CHANCE;
	}

	private void winPrint() {
		println("You guessed the word: " + word);
		println("You win.");
	}

	private void losePrint() {
		println("You're completely hung.");
		println("The word was: " + word);
		println("You lose.");
	}

	/* Private instance variables */
	private HangmanLexicon lexicon = new HangmanLexicon();
	private String secretWord;
	private String word;
	private int numberOfWrongGuess;
	private static final int NUMBER_OF_CHANCE = 8;

	// A canvas object used to draw a graphic
	private HangmanCanvas canvas;
}
