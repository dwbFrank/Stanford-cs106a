
/**
 * This class is a GCompound subclass that creates an object for filled bounce ball.
 * @author frank
 *
 */
import java.awt.Color;

import acm.graphics.*;
import acm.util.RandomGenerator;

public class GBall extends GCompound {
	/**
	 * Create a GBall object with radius.
	 * 
	 * @param radius
	 *            specifies radius of the ball
	 */
	public GBall(int radius) {
		ball = new GOval(radius * 2, radius * 2);
		setBallColor(defaultColor);
		initVelocity();
		add(ball, -radius, -radius);
	}

	/**
	 * Create a GBall object with radius and specified location.
	 * 
	 * @param radius
	 *            specifies radius of the ball
	 * @param gPoint
	 *            specifies location of the ball.
	 */
	public GBall(int radius, GPoint gPoint) {
		this(radius);
		add(ball, gPoint);
	}

	/**
	 * Get velocity of the ball of x coordinate.
	 * 
	 * @return vx
	 */
	public double getVx() {
		return vx;
	}

	/**
	 * Get velocity of the ball of y coordinate.
	 * 
	 * @return vy
	 */
	public double getVy() {
		return vy;
	}

	/**
	 * Set color of the ball.
	 * 
	 * @param color
	 *            specifies color of the ball
	 */
	public void setBallColor(Color color) {
		this.color = color;
		ball.setColor(this.color);
		ball.setFillColor(this.color);
		ball.setFilled(true);
	}

	/**
	 * Initialize a random velocity of the ball.
	 */
	private void initVelocity() {
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5))
			vx = -vx;
		vy = 3.0;
	}

	/* Private instance variables */
	private static final Color defaultColor = Color.BLACK; // Default color of the ball.
	private GOval ball;
	private Color color; // Specifies color of the ball.
	private RandomGenerator rgen = RandomGenerator.getInstance(); // A random-number generator used to generate a random
	private double vx, vy; // Keep track of the velocity of the ball.
}
