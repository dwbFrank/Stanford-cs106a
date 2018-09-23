
/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	/** Default speed of the ball */
	private static final int BALL_SPEED = 10;

	/* Method: run() */
	/** Runs the Breakout program. */
	public void run() {
		/* You fill this in, along with any subsidiary methods */
		setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		setupGame();

		addMouseListeners();

		add(ball, ballOrigin());

		while (!isGameOver()) {
			moveBall();
			if (isBouncedSide()) {
				bounceHor();
			}
			if (isBouncedTop()) {
				bounceVer();
			}
			checkCollisions();
			pause(ballSpeed);
		}

	}

	/**
	 * Initialize game with bricks framework, paddle and bounce ball.
	 */
	private void setupGame() {
		// Initialize bricks
		GPoint bricksOrigin = bricksOrigin();
		bricks = new GBricks(NBRICK_ROWS, NBRICKS_PER_ROW, BRICK_WIDTH, BRICK_HEIGHT, BRICK_SEP, bricksOrigin);
		add(bricks);

		// Initialize paddle
		GPoint paddleOrigin = paddleOrigin();
		paddle = new GPaddle(PADDLE_WIDTH, PADDLE_HEIGHT, Color.GREEN, paddleOrigin);
		add(paddle);

		// Initialize ball
		GPoint ballOrigin = paddleOrigin();
		ball = makeBall(ballOrigin);
		ballSpeed = BALL_SPEED;
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5))
			vx = -vx;
		vy = 3.0;
		add(ball);

	}

	private GOval makeBall(GPoint origin) {
		GOval ball = new GOval(BALL_RADIUS * 2, BALL_RADIUS * 2);
		ball.setFillColor(Color.BLACK);
		ball.setFilled(true);
		ball.setLocation(origin);
		return ball;
	}

	/**
	 * Creates a GPoint object of central location for bricks framework.
	 * 
	 * @return Central location for bricks framework
	 */
	private GPoint bricksOrigin() {
		int brickRowWidth = NBRICKS_PER_ROW * BRICK_WIDTH + (NBRICKS_PER_ROW - 1) * BRICK_SEP;

		return new GPoint((APPLICATION_WIDTH - brickRowWidth) / 2, BRICK_Y_OFFSET);
	}

	/**
	 * Creates a GPoint object of central location for paddle.
	 * 
	 * @return Central location for paddle.
	 */
	private GPoint paddleOrigin() {
		int x = (APPLICATION_WIDTH - PADDLE_WIDTH) / 2;
		int y = APPLICATION_HEIGHT - (PADDLE_HEIGHT + PADDLE_Y_OFFSET);

		return new GPoint(x, y);
	}

	/**
	 * Creates a GPoint object of central location for bounce ball.
	 */
	private GPoint ballOrigin() {
		double x = (APPLICATION_WIDTH - BALL_RADIUS * 2) / 2.0;
		double y = (APPLICATION_HEIGHT - BALL_RADIUS * 2) / 2.0;
		return new GPoint(x, y);
	}

	/**
	 * Add mouse event for paddle. If mouse point is out of window width just set it
	 * to window edge.
	 */
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		if (x > (APPLICATION_WIDTH - PADDLE_WIDTH)) {
			x = APPLICATION_WIDTH - PADDLE_WIDTH;
		}

		paddle.setLocation(x, paddle.getY());
	}

	/**
	 * If bounced object is paddle, than bounce the ball to upward. If bounced
	 * object is brick, than remove it.
	 */
	private void checkCollisions() {
		GObject collider = getCollidingObject();
		if (collider == paddle) {
			bounceVer();
		} else if (collider == bricks) {
			remove(bricks);
		}
	}

	/**
	 * Retrieve the graphical object at bounced location.
	 * 
	 * @return
	 */
	private GObject getCollidingObject() {
		GPoint leftTop = new GPoint(ball.getX(), ball.getY());
		GPoint leftBot = new GPoint(ball.getX(), ball.getY() + BALL_RADIUS * 2);
		GPoint rightTop = new GPoint(ball.getX() + BALL_RADIUS * 2, ball.getY());
		GPoint rightBot = new GPoint(ball.getX() + BALL_RADIUS * 2, ball.getY() + BALL_RADIUS * 2);

		if (getElementAt(leftTop) != null) {
			return getElementAt(leftTop);
		} else if (getElementAt(leftBot) != null) {
			return getElementAt(leftBot);
		} else if (getElementAt(rightTop) != null) {
			return getElementAt(rightTop);
		} else if (getElementAt(rightBot) != null) {
			return getElementAt(rightBot);
		} else {
			return null;
		}
	}

	/**
	 * Move the ball to next location.
	 */
	private void moveBall() {
		double x = vx;
		double y = vy;
		ball.move(x, y);
	}

	/**
	 * If turns is minus 1 game is over.
	 * 
	 * @return determine whether game is over.
	 */
	private boolean isGameOver() {
		return turns == -1 || isEndOfTurn();
	}

	/**
	 * If the ball bounced off the bottom wall, this turn is over.
	 * 
	 * @return determine whether turn is over or not.
	 */
	private boolean isEndOfTurn() {
		return ball.getY() > paddle.getY();
	}

	/**
	 * To see whether the coordinate of the ball of the right edge has become
	 * greater than the width of the window. Or the left edge of the ball less than
	 * or equals 0.
	 * 
	 * @return determine whether bounced off side edge or not.
	 */
	private boolean isBouncedSide() {
		double x = ball.getX();

		return (x <= 0) || (x + BALL_RADIUS * 2 >= APPLICATION_WIDTH);
	}

	private void bounceHor() {
		vx = -vx;
	}

	/**
	 * To see whether the coordinate of the ball of the bottom edge has become less
	 * than or equals 0.
	 * 
	 * @return determine whether bounced off top edge or not.
	 */
	private boolean isBouncedTop() {
		double y = ball.getY();

		return y <= 0;
	}

	private void bounceVer() {
		vy = -vy;
	}

	/**
	 * Speed up the speed of the ball.
	 */
	private void speedUp() {
		ballSpeed *= 1.01;
	}

	/* Private instance variables */
	private GBricks bricks;
	private GPaddle paddle;
	private GOval ball;
	/* Random number generator used to specifies origin point */
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private double vx, vy; // The velocity of the ball
	private double ballSpeed; // The speed of the bounce ball
	private int turns = 3; // Turns of the game
}
