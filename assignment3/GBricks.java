
/**
 * This class is a GCompound subclass that creates an object have N rows of bricks and each row have M bricks, with each two rows a different color.
 * The color from the top down is red, orange, yellow, green, and cyan.
 * @author Frank Ding
 */

import java.awt.Color;
import acm.graphics.*;

public class GBricks extends GCompound {
	/**
	 * Creates a new GBricks object with the specified nRows, nBricksPerRow,
	 * brickWidth, brickHeight, and brickSep at a default location of (0, 0).
	 * 
	 * @param nRows
	 *            number of rows of bricks.
	 * @param nBricksPerRow
	 *            number of bricks per row.
	 * @param brickWidth
	 *            width of a brick in pixels.
	 * @param brickHeight
	 *            height of a brick in pixels.
	 * @param brickSep
	 *            separation between bricks in pixels.
	 */
	public GBricks(int nRows, int nBricksPerRow, int brickWidth, int brickHeight, int brickSep) {
		this.nRows = nRows;
		this.nBricksPerRow = nBricksPerRow;
		this.brickWidth = brickWidth;
		this.brickHeight = brickHeight;
		this.brickSep = brickSep;
		currentColor = Color.RED;
		addBricks();
	}

	/**
	 * Creates a new GBricks object with the specified nRows, nBricksPerRow,
	 * brickWidth, brickHeight, brickSep and gPoint used to specify the location.
	 * 
	 * @param nRows
	 *            number of rows of bricks.
	 * @param nBricksPerRow
	 *            number of bricks per row.
	 * @param brickWidth
	 *            width of a brick in pixels.
	 * @param brickHeight
	 *            height of a brick in pixels.
	 * @param brickSep
	 *            separation between bricks in pixels.
	 * @param gPoint
	 *            specify location which combines an x and a y coordinate.
	 */
	public GBricks(int nRows, int nBricksPerRow, int brickWidth, int brickHeight, int brickSep, GPoint gPoint) {
		this(nRows, nBricksPerRow, brickWidth, brickHeight, brickSep);
		setLocation(gPoint);
	}

	/**
	 * Sets bricks in each rows with color.
	 */
	private void addBricks() {
		double x = 0;
		double y = 0;
		int rowsPerColor = nRows / 5;
		for (int i = 1; i <= nRows; i++) {
			for (int j = 0; j < nBricksPerRow; j++) {
				GRect brick = new GRect(x, y, brickWidth, brickHeight);
				brick.setFillColor(currentColor);
				brick.setFilled(true);
				add(brick);
				x += (brickWidth + brickSep);
			}
			changeBrickColor((i % rowsPerColor) == 0);
			x = 0;
			y += (brickHeight + brickSep);
		}
	}

	/**
	 * Switch appropriate brick's color for new line.
	 * 
	 * @param currentColor
	 *            The current line used color.
	 * @param isShift
	 *            Indicate whether use next color or not.
	 * @return appropriate color.
	 */
	private void changeBrickColor(boolean isShift) {
		if (isShift) {
			if (currentColor == Color.RED) {
				currentColor = Color.ORANGE;
			} else if (currentColor == Color.ORANGE) {
				currentColor = Color.YELLOW;
			} else if (currentColor == Color.YELLOW) {
				currentColor = Color.GREEN;
			} else if (currentColor == Color.GREEN) {
				currentColor = Color.CYAN;
			} else {
				currentColor = Color.RED;
			}
		}
	}

	/* Private instance variables */
	private Color currentColor; // The brick color
	private int nRows; // The number of rows
	private int nBricksPerRow; // The number of per row
	private int brickWidth; // The brick of Width
	private int brickHeight; // The brick of Height
	private int brickSep; // Separate between bricks
}
