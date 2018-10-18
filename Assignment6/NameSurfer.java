
/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import acm.util.*;
import java.awt.Color;

public class NameSurfer extends GraphicsProgram implements NameSurferConstants {

	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the top of the window.
	 */
	public void init() {
		// You fill this in, along with any helper methods //
		dataBase = new NameSurferDataBase(NAMES_DATA_FILE);
		textField = new JTextField(TEXT_FIELD_WIDTH);
		textField.setActionCommand("Graph");
		textField.addActionListener(this);
		graphButton = new JButton("Graph");
		clearButton = new JButton("Clear");
		add(textField, NORTH);
		add(graphButton, NORTH);
		add(clearButton, NORTH);
		addActionListeners();
	}

	/**
	 * This class is responsible for detecting when the buttons are clicked, so you
	 * will have to define a method to respond to button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		// You fill this in //
		if (e.getActionCommand().equals("Graph")) {
			String name = textField.getText();
			NameSurferEntry entry = dataBase.findEntry(name);
			if (entry != null && entries.add(entry)) {
				drawEntry(entry);
			}
		} else if (e.getSource() == clearButton) {
			removeAll();
			entries.clear();
			initGrid();
		}
	}

	/**
	 * This class is responsible for detecting when the the canvas is resized. This
	 * method is called on each resize!
	 */
	public void componentResized(ComponentEvent e) {
		redraw();
	}

	/**
	 * A helper method that we *strongly* recommend. Redraw clears the entire
	 * display and repaints it. Consider calling it when you change anything about
	 * the display.
	 */
	private void redraw() {
		// You fill this in //
		removeAll();
		currentColorIdx = 0;
		initGrid();
		drawEntries();
	}

	private void initGrid() {
		grid = new GCompound();
		/* vertical lines */
		GLine topLine = new GLine();
		GLine bottomLine = new GLine();
		topLine.setStartPoint(0, GRAPH_MARGIN_SIZE);
		topLine.setEndPoint(getWidth(), GRAPH_MARGIN_SIZE);
		bottomLine.setStartPoint(0, getHeight() - GRAPH_MARGIN_SIZE);
		bottomLine.setEndPoint(getWidth(), getHeight() - GRAPH_MARGIN_SIZE);
		grid.add(topLine);
		grid.add(bottomLine);

		/* horizontal lines and decade lables */
		double span = getWidth() / NDECADES;
		double x = 0.0;
		int decade = START_DECADE;
		for (int i = 0; i < NDECADES; i++) {
			GLine line = new GLine();
			line.setStartPoint(x, 0);
			line.setEndPoint(x, getHeight());
			GLabel label = new GLabel(decade + "");
			grid.add(line);
			label.setLocation(line.getEndPoint().getX(), getHeight() - DECADE_LABEL_MARGIN_SIZE);
			grid.add(label);
			x += span;
			decade += 10;
		}
		add(grid);
	}

	private void drawEntries() {
		if (entries == null) {
			entries = new ArrayList<NameSurferEntry>();
		}
		for (NameSurferEntry entry : entries) {
			drawEntry(entry);
		}
	}

	private void drawEntry(NameSurferEntry entry) {

		double height = getHeight() - (GRAPH_MARGIN_SIZE * 2);
		double lengthPerRank = height / 1000.0;
		double span = getWidth() / NDECADES;
		double x = 0.0;
		Color color = nextColor();
		for (int i = 0; i < NDECADES; i++) {
			int rank1 = entry.getRank(i);
			double startX = x;
			double startY = GRAPH_MARGIN_SIZE + rank1 * lengthPerRank;
			String rankStr = entry.getName() + rank1;
			if (rank1 == 0) {
				startY = getHeight() - GRAPH_MARGIN_SIZE;
				rankStr = entry.getName() + "*";
			}

			GLabel rankLabel = new GLabel(rankStr);
			rankLabel.setLocation(x, startY);
			rankLabel.setColor(color);
			add(rankLabel);

			if (i < NDECADES - 1) {
				int rank2 = entry.getRank(i + 1);
				double endX = x + span;
				double endY = GRAPH_MARGIN_SIZE + rank2 * lengthPerRank;
				if (rank2 == 0) {
					endY = getHeight() - GRAPH_MARGIN_SIZE;
				}
				GLine line = new GLine();
				line.setStartPoint(startX, startY);
				line.setEndPoint(endX, endY);
				line.setColor(color);
				add(line);

			}
			x += span;
		}
	}

	private Color nextColor() {
		Color color = null;
		switch (currentColorIdx % 4) {
		case 0:
			color = Color.black;
			break;
		case 1:
			color = Color.red;
			break;
		case 2:
			color = Color.blue;
			break;
		case 3:
			color = Color.magenta;
			break;
		default:
			break;
		}
		currentColorIdx++;
		return color;
	}

	private NameSurferDataBase dataBase;
	private JTextField textField;
	private JButton graphButton;
	private JButton clearButton;
	private GCompound grid;
	private ArrayList<NameSurferEntry> entries;
	private int currentColorIdx;
}
