
/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import acm.util.*;
import java.util.*;
import java.io.*;

public class NameSurferDataBase implements NameSurferConstants {

	// TODO: Add instance variables

	/**
	 * Constructor: NameSurferDataBase(filename) Creates a new NameSurferDataBase
	 * and initializes it using the data in the specified file. The constructor
	 * throws an error exception if the requested file does not exist or if an error
	 * occurs as the file is being read.
	 */
	public NameSurferDataBase(String filename) {
		// You fill this in //
		database = new HashMap<String, NameSurferEntry>();
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
			while (true) {
				String line = bufferedReader.readLine();
				if (line == null) {
					break;
				}
				NameSurferEntry entry = new NameSurferEntry(line);
				database.put(entry.getName(), entry);
			}
			bufferedReader.close();
		} catch (IOException exception) {
			throw new ErrorException(exception);
		}
	}

	/**
	 * Public Method: findEntry(name) Returns the NameSurferEntry associated with
	 * this name, if one exists. If the name does not appear in the database, this
	 * method returns null.
	 */
	public NameSurferEntry findEntry(String name) {
		// You need to turn this stub into a real implementation //
		name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
		return database.get(name);
	}

	private HashMap<String, NameSurferEntry> database;
}