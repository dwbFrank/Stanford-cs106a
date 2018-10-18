
/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;
import java.util.*;

public class NameSurferEntry implements NameSurferConstants {

	// TODO: Add instance variables

	/**
	 * Constructor: NameSurferEntry(line) Creates a new NameSurferEntry from a data
	 * line as it appears in the data file. Each line begins with the name, which is
	 * followed by integers giving the rank of that name for each decade.
	 */
	public NameSurferEntry(String line) {
		// You fill this in //
		String[] token = line.split(" ");
		this.name = capitalizedName(token[0]);
		ranks = new int[NDECADES];
		for (int i = 1; i < token.length; i++) {
			ranks[i - 1] = Integer.parseInt(token[i]);
		}
	}

	private String capitalizedName(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
	}

	/**
	 * Public Method: getName() Returns the name associated with this entry.
	 */
	public String getName() {
		// You need to turn this stub into a real implementation //
		return name;
	}

	/**
	 * Public Method: getRank(decade) Returns the rank associated with an entry for
	 * a particular decade. The decade value is an integer indicating how many
	 * decades have passed since the first year in the database, which is given by
	 * the constant START_DECADE. If a name does not appear in a decade, the rank
	 * value is 0.
	 */
	public int getRank(int decade) {
		// You need to turn this stub into a real implementation //
		return ranks[decade];
	}

	/**
	 * Public Method: toString() Returns a string that makes it easy to see the
	 * value of a NameSurferEntry.
	 */
	public String toString() {
		// You need to turn this stub into a real implementation //
		String s = name + " [";
		for (int rank : ranks) {
			s += " " + rank;
		}
		s += " ]";
		return s;
	}

	private String name;
	private int[] ranks;
}
