
/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import java.util.Hashtable;

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {

	public static void main(String[] args) {
		new Yahtzee().start(args);
	}

	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	private void playGame() {
		/* You fill this in */
		int nTurns = nPlayers * 13;
		initScoreTable();
		for (int i = 0; i < nTurns; i++) {
			printChangeTurnMessage(i);
			int player = i % nPlayers;
			/* Player index is start from 1 not 0 */
			int playerIdx = (i % nPlayers) + 1;
			display.waitForPlayerToClickRoll(playerIdx);
			int[] dice = rollDice(5);

			printSelectDiceMessage();
			display.waitForPlayerToSelectDice();
			reRollDice(dice);

			printSelectDiceMessage();
			display.waitForPlayerToSelectDice();
			reRollDice(dice);

			printSelectCategoryMessage();
			while (true) {
				int category = display.waitForPlayerToSelectCategory();
				if (isFirstUseCategory(player, category)) {
					updateScore(dice, player, category);
					break;
				}
				display.printMessage("Cannot re-use any previous category");
			}
		}

		printWinner();
	}

	private void initScoreTable() {
		scoreTable = new int[nPlayers][SCORE_CATEGORY];
		for (int i = 0; i < scoreTable.length; i++) {
			for (int j = 0; j < scoreTable[0].length; j++) {
				scoreTable[i][j] = -1;
			}
			scoreTable[i][UPPER_SCORE - 1] = 0;
			scoreTable[i][UPPER_BONUS - 1] = 0;
			scoreTable[i][LOWER_SCORE - 1] = 0;
			scoreTable[i][TOTAL - 1] = 0;
			scoreTable[i][REMAINS] = 13;
		}

	}

	/**
	 * Print roll dice message
	 * 
	 * @param player Player's name
	 */
	private void printChangeTurnMessage(int currentTurn) {
		int playerIdx = currentTurn % nPlayers;
		String player = playerNames[playerIdx];
		String message = String.format("%s's turn! Click \"Roll Dice\" button to roll the dice.", player);
		display.printMessage(message);
	}

	/**
	 * Return N's number
	 * 
	 * @param number The number of dice to roll
	 * @return An array contains each outcome of dice
	 */
	private int[] rollDice(int number) {
		int[] dice = null;
		if (number > 0) {
			dice = new int[number];
			for (int i = 0; i < number; i++) {
				dice[i] = rgen.nextInt(1, 6);
			}
		}
		display.displayDice(dice);

		return dice;
	}

	private void printSelectDiceMessage() {
		String message = "Select the dice you wish to re-roll and click \"Roll Again\".";
		display.printMessage(message);
	}

	/**
	 * Re-roll dice.
	 * 
	 * @param dice
	 */
	private void reRollDice(int[] dice) {
		for (int i = 0; i < 5; i++) {
			if (display.isDieSelected(i)) {
				int newNumber = rgen.nextInt(1, 6);
				dice[i] = newNumber;
			}
		}
		display.displayDice(dice);
	}

	private void printSelectCategoryMessage() {
		String message = "Select a category for this roll.";
		display.printMessage(message);
	}

	private boolean isFirstUseCategory(int player, int category) {
		return scoreTable[player][category - 1] == -1;
	}

	private void updateScore(int[] dice, int player, int selectedCategory) {
		boolean isMatch = checkCategory(dice, selectedCategory);
		int score = 0;
		if (isMatch) {
			switch (selectedCategory) {
			case ONES:
			case TWOS:
			case THREES:
			case FOURS:
			case FIVES:
			case SIXES:
				score = howManyDice(dice, selectedCategory) * selectedCategory;
				break;
			case THREE_OF_A_KIND:
			case FOUR_OF_A_KIND:
			case CHANCE:
				score = accumulateDice(dice);
				break;
			case FULL_HOUSE:
				score = 25;
				break;
			case SMALL_STRAIGHT:
				score = 30;
				break;
			case LARGE_STRAIGHT:
				score = 40;
				break;
			case YAHTZEE:
				score = 50;
				break;
			}
		}

		/* To update scoreTable state */
		scoreTable[player][selectedCategory - 1] = score;
		if (selectedCategory <= 6) {
			scoreTable[player][UPPER_SCORE - 1] += score;
			display.updateScorecard(UPPER_SCORE, player + 1, scoreTable[player][UPPER_SCORE - 1]);
			if (scoreTable[player][UPPER_SCORE - 1] >= 63) {
				scoreTable[player][UPPER_BONUS - 1] = 35;
			}
		} else {
			scoreTable[player][LOWER_SCORE - 1] += score;
			display.updateScorecard(LOWER_SCORE, player + 1, scoreTable[player][LOWER_SCORE - 1]);
		}
		scoreTable[player][TOTAL - 1] += score;
		display.updateScorecard(TOTAL, player + 1, scoreTable[player][TOTAL - 1]);
		scoreTable[player][REMAINS]--;

		display.updateScorecard(selectedCategory, player + 1, score);
	}

	/**
	 * To count how many of N's dice have
	 * 
	 * @param dice
	 * @param n
	 * @return
	 */
	private int howManyDice(int[] dice, int n) {
		int count = 0;
		for (int m : dice) {
			if (m == n) {
				count++;
			}
		}

		return count;
	}

	/**
	 * Accumulate each dice
	 * 
	 * @param dice
	 * @return
	 */
	private int accumulateDice(int[] dice) {
		int count = 0;
		for (int m : dice) {
			count += m;
		}

		return count;
	}

	private boolean checkCategory(int[] dice, int category) {
		if (category < 7) {
			return true;
		} else {

			Hashtable diceHaveCategory = diceCount(dice);
			int numberOfCategory = diceHaveCategory.size();
			switch (category) {
			case THREE_OF_A_KIND:
				return diceHaveCategory.contains(3);
			case FOUR_OF_A_KIND:
				return numberOfCategory < 3;
			case YAHTZEE:
				return numberOfCategory == 1;
			case FULL_HOUSE:
				return diceHaveCategory.contains(3) && diceHaveCategory.contains(2);
			case SMALL_STRAIGHT:
				return numberOfCategory == 4;
			case LARGE_STRAIGHT:
				return numberOfCategory == 5;
			}
		}

		return false;
	}

	private Hashtable diceCount(int[] dice) {
		Hashtable<Integer, Integer> countTable = new Hashtable<Integer, Integer>();
		for (int i = 0; i < dice.length; i++) {
			Integer key = new Integer(dice[i]);
			Integer value = countTable.get(key);
			if (value == null) {
				value = new Integer(1);
			} else {
				value = new Integer(1 + value.intValue());
			}
			countTable.put(key, value);
		}

		return countTable;
	}

	private void printWinner() {
		int topScore = -1;
		int winner = -1;
		for (int i = 0; i < scoreTable.length; i++) {
			int score = scoreTable[i][TOTAL - 1];
			if (score > topScore) {
				topScore = score;
				winner = i;
			}
		}

		String playerName = playerNames[winner];
		String message = String.format("Congratulations, %s, you're the winner with a total score of %d !", playerName,
				topScore);
		display.printMessage(message);
	}

	/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();

	private static final int SCORE_CATEGORY = 18;
	private static final int REMAINS = 17;
	private int[][] scoreTable;

}
