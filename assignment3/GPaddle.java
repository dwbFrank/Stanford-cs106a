
/**
 * This class is a GCompound subclass that creates an object for filled paddle.
 * @author Frank Ding
 */

import java.awt.Color;
import acm.graphics.*;

public class GPaddle extends GCompound {
	/**
	 * Creates a new GPaddle object with the specified width and height of the
	 * paddle.
	 * 
	 * @param paddleWidth
	 *            width of the paddle
	 * @param paddleHeight
	 *            height of the paddle
	 */
	public GPaddle(int paddleWidth, int paddleHeight) {
		this.paddleWidth = paddleWidth;
		this.paddleHeight = paddleHeight;
		paddle = new GRect(paddleWidth, paddleHeight);
		paddle.setColor(paddleColor);
		paddle.setFillColor(paddleColor);
		paddle.setFilled(true);
		add(paddle);
	}

	/**
	 * Creates a new GPaddle object with the specified width, height and origin of
	 * the paddle.
	 * 
	 * @param paddleWidth
	 *            width of the paddle
	 * @param paddleHeight
	 *            height of the paddle
	 * @param gPoint
	 *            origin of the paddle
	 */
	public GPaddle(int paddleWidth, int paddleHeight, GPoint gPoint) {
		this(paddleWidth, paddleHeight);
		setLocation(gPoint);
	}

	/**
	 * Creates a new GPaddle object with the specified width, height and color of
	 * the paddle.
	 * 
	 * @param paddleWidth
	 *            width of the paddle
	 * @param paddleHeight
	 *            height of the paddle
	 * @param paddleColor
	 *            color of the paddle
	 */
	public GPaddle(int paddleWidth, int paddleHeight, Color paddleColor) {
		this(paddleWidth, paddleHeight);
		changeColor(paddleColor);
	}

	/**
	 * Creates a new GPaddle object with the specified width, height, color and
	 * origin of the paddle.
	 * 
	 * @param paddleWidth
	 *            width of the paddle
	 * @param paddleHeight
	 *            height of the paddle
	 * @param paddleColor
	 *            color of the paddle
	 * @param gPoint
	 *            origin of the paddle
	 */
	public GPaddle(int paddleWidth, int paddleHeight, Color paddleColor, GPoint gPoint) {
		this(paddleWidth, paddleHeight, paddleColor);
		setLocation(gPoint);
	}

	/**
	 * Changes color of the paddle.
	 * 
	 * @param paddleColor
	 *            color of the paddle
	 */
	public void changeColor(Color paddleColor) {
		this.paddleColor = paddleColor;
		paddle.setColor(paddleColor);
		paddle.setFillColor(paddleColor);
	}

	/* Private instance variables */
	private int paddleWidth; // width of the paddle
	private int paddleHeight; // height of the paddle
	private Color paddleColor = Color.BLACK; // default color of the paddle
	private GRect paddle; // concrete paddle
}
