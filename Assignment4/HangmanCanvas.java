
/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

	/** Resets the display so that only the scaffold appears */
	public void reset() {
		/* You fill this in */
		/* Fetch window width */
		int windowWidth = getWidth();

		/* Initial each component origin coordinate. */
		originPole = new GPoint(((windowWidth - UPPER_ARM_LENGTH * 2) / 2.0) - UPPER_ARM_LENGTH, SCAFFOLD_HEIGHT * 0.5);
		originBeam = new GPoint(originPole.getX(), originPole.getY());
		originRope = new GPoint(originBeam.getX() + BEAM_LENGTH, originBeam.getY());
		originHead = new GPoint(originRope.getX() - HEAD_RADIUS, originRope.getY() + ROPE_LENGTH);
		originBody = new GPoint(originRope.getX(), originHead.getY() + HEAD_RADIUS * 2);

		/* Initial scaffold. */
		drawScaffold();

		/* Initial String label. */
		initSecretLabel();
		initIncorrectGuessLabel();
	}

	/**
	 * Updates the word on the screen to correspond to the current state of the
	 * game. The argument string shows what letters have been guessed so far;
	 * unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String word) {
		/* You fill this in */
		secretWord.setLabel(word);
	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user. Calling
	 * this method causes the next body part to appear on the scaffold and adds the
	 * letter to the list of incorrect guesses that appears at the bottom of the
	 * window.
	 */
	public void noteIncorrectGuess(char letter) {
		/* You fill this in */
		incorrectGuess += letter;
		incorrectGuessLabel.setLabel(incorrectGuess);
		drawHangman();
	}

	private void drawHangman() {
		switch (incorrectGuess.length()) {
		case 1:
			drawHead();
			break;
		case 2:
			drawBody();
			break;
		case 3:
			drawArm(true);
			break;
		case 4:
			drawArm(false);
			break;
		case 5:
			drawLeg(true);
			break;
		case 6:
			drawLeg(false);
			break;
		case 7:
			drawFoot(true);
			break;
		case 8:
			drawFoot(false);
			break;
		default:
			GLabel error = new GLabel("draw Hangman error!");
			error.setFont("Helvetica-40");
			add(error, getWidth() / 2.0, getHeight() / 2.0);
			break;
		}
	}

	/*
	 * Draw scaffold in first step.
	 */
	private void drawScaffold() {
		/* Draw scaffold */
		GLine pole = new GLine(originPole.getX(), originPole.getY(), originPole.getX(),
				originPole.getY() + SCAFFOLD_HEIGHT);
		add(pole);
		GLine beam = new GLine(originBeam.getX(), originBeam.getY(), originBeam.getX() + BEAM_LENGTH,
				originBeam.getY());
		add(beam);
		GLine rope = new GLine(originRope.getX(), originRope.getY(), originRope.getX(),
				originRope.getY() + ROPE_LENGTH);
		add(rope);
	}

	/*
	 * Draw head
	 */
	private void drawHead() {
		GOval head = new GOval(originHead.getX(), originHead.getY(), HEAD_RADIUS * 2, HEAD_RADIUS * 2);
		add(head);
	}

	/*
	 * Draw body
	 */
	private void drawBody() {
		GLine body = new GLine(originBody.getX(), originBody.getY(), originBody.getX(),
				originBody.getY() + BODY_LENGTH);
		add(body);
		GLine hip = new GLine(originBody.getX() - HIP_WIDTH / 2.0, originBody.getY() + BODY_LENGTH,
				originBody.getX() + HIP_WIDTH / 2.0, originBody.getY() + BODY_LENGTH);
		add(hip);
	}

	/**
	 * Draw left arm if isLeft is true. otherwise draw right arm.
	 * 
	 * @param isLeft if true draw left arm. otherwise draw right arm.
	 */
	private void drawArm(boolean isLeft) {
		double originX = originBody.getX();
		double originY = originBody.getY() + ARM_OFFSET_FROM_HEAD;
		double endX = originX - UPPER_ARM_LENGTH;
		if (!isLeft)
			endX += UPPER_ARM_LENGTH * 2;
		GLine upperArm = new GLine(originX, originY, endX, originY);
		add(upperArm);
		GLine lowerArm = new GLine(endX, originY, endX, originY + LOWER_ARM_LENGTH);
		add(lowerArm);
	}

	/**
	 * Draw left leg if isLeft is true. otherwise draw right leg.
	 * 
	 * @param isLeft if true draw left leg. otherwise draw right leg.
	 */
	private void drawLeg(boolean isLeft) {
		double originX = originBody.getX() - (HIP_WIDTH / 2.0);
		if (!isLeft)
			originX += HIP_WIDTH / 2.0 * 2;
		double originY = originBody.getY() + BODY_LENGTH;
		GLine leg = new GLine(originX, originY, originX, originY + LEG_LENGTH);
		add(leg);
	}

	/**
	 * Draw left foot if isLeft is true. otherwise draw right foot.
	 * 
	 * @param isLeft if true draw left foot. otherwise draw right foot.
	 */
	private void drawFoot(boolean isLeft) {
		double originX = originBody.getX() - (HIP_WIDTH / 2.0);
		double originY = originBody.getY() + BODY_LENGTH + LEG_LENGTH;
		double endX = originX - FOOT_LENGTH;
		if (!isLeft) {
			originX = originBody.getX() + (HIP_WIDTH / 2.0);
			endX = originX + FOOT_LENGTH;
		}

		GLine foot = new GLine(originX, originY, endX, originY);
		add(foot);
	}

	private void initSecretLabel() {
		double originX = originPole.getX() / 2.0;
		double originY = originPole.getY() + SCAFFOLD_HEIGHT + SCAFFOLD_HEIGHT / 2.0;
		secretWord = new GLabel("", originX, originY);
		secretWord.setFont("Helvetica-40");
		add(secretWord);
	}

	private void initIncorrectGuessLabel() {
		incorrectGuess = "";
		double originX = originPole.getX() / 2.0;
		double originY = originPole.getY() + SCAFFOLD_HEIGHT + SCAFFOLD_HEIGHT / 2.0 + (SCAFFOLD_HEIGHT / 4.0);
		incorrectGuessLabel = new GLabel("", originX, originY);
		incorrectGuessLabel.setFont("Helvetica-30");
		add(incorrectGuessLabel);
	}

	/* Private instance variables */
	private GPoint originPole;
	private GPoint originBeam;
	private GPoint originRope;
	private GPoint originHead;
	private GPoint originBody;
	private GLabel secretWord;
	private String incorrectGuess;
	private GLabel incorrectGuessLabel;

	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

}