
/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import acm.util.*;

public class HangmanLexicon {
	// This is the HangmanLexicon constructor
	public HangmanLexicon() {
		// your initialization code goes here
		try {
			ArrayList<String> words = new ArrayList<String>();
			BufferedReader rd = new BufferedReader(new FileReader("HangmanLexicon.txt"));
			String word = null;
			while (true) {
				word = rd.readLine();
				if (word == null)
					break;
				words.add(word);
			}
			rd.close();
			lexicon = new String[words.size()];
			for (int i = 0; i < lexicon.length; i++) {
				lexicon[i] = words.get(i);
			}
		} catch (IOException ex) {
			throw new ErrorException(ex);
		}

	}

	/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return lexicon.length;
	}

	/** Returns the word at the specified index. */
	public String getWord(int index) {
		return lexicon[index];
	};

	/* Private Instance variables */
	private String[] lexicon;
}
