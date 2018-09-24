
/**
 * This class is a GCompound subclass. Used to keep track of the score and turns.
 * @author frank
 *
 */

import acm.graphics.*;

public class GBoard extends GCompound {
	public GBoard(String item1, String item2, String value1, String value2) {
		entry1 = new GLabel(item1 + ": " + value1);
		title1 = item1;
		entry2 = new GLabel(item2 + ": " + value2);
		title2 = item2;
		add(entry1);
		double x = entry1.getWidth() + 20.0;
		add(entry2, x, entry1.getX());
	}

	public void updateEntry1(String value) {
		entry1.setLabel(title1 + ": " + value);
	}

	public void updateEntry2(String value) {
		entry2.setLabel(title2 + ": " + value);
	}

	/* Private instance variables */
	private GLabel entry1;
	private GLabel entry2;
	private String title1;
	private String title2;
}
