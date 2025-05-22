// A04005777 Noah Moore

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.util.Random;

/**
 * This class demonstrates an animation of multiple bouncing balls on the screen.
 * Each ball bounces off the borders of the window and moves according to its velocity.
 */
public class MultipleBouncingBallsAnimation {

    private static final int X_BOUND = 800;
    private static final int Y_BOUND = 600;

    private static Ball[] generateBalls(int[] ballSizes) {
        Random rand = new Random();
        Ball [] balls = new Ball[ballSizes.length];
        for (int i = 0; i < ballSizes.length; i++) {
            int radius = ballSizes[i];
            // Ensure the ball is placed within the window bounds
            int x = rand.nextInt(X_BOUND - (radius * 2)) + radius;
            int y = rand.nextInt(Y_BOUND - (radius * 2)) + radius;
            // Create a new Ball object with a random position and color
            balls[i] = new Ball(new Point(x, y), radius, java.awt.Color.BLACK);
            // Set random velocity for larger balls or fixed velocity for smaller ones
            if (radius > 50) {
                balls[i].setVelocity(new Velocity(rand.nextInt(5) + 1, rand.nextInt(5) + 1)); // Random velocity for large balls
            } else {
                balls[i].setVelocity(new Velocity(60, 60)); // Fixed velocity for smaller balls
            }
        }
        return balls;
    }

    /**
     * Draws and animates multiple bouncing balls.
     * Each ball is initialized with a random position and velocity.
     * The balls bounce off the borders of the screen and are drawn at each step.
     *
     * @param balls an array of ball objects, where each ball has a random position and velocity.
     */
    private static void drawAnimation(Ball[] balls) {
        // Create a GUI window for the animation
        GUI gui = new GUI("Multiple Bouncing Balls Animation", X_BOUND, Y_BOUND);
        Sleeper sleeper = new Sleeper();
        // Create an array of Ball objects based on the provided sizes

        Rectangle outerBound = new Rectangle(new Point(0, 0), X_BOUND, Y_BOUND);

        Rectangle[] bounds = {outerBound};
        // Main animation loop
        while (true) {
            // Get the drawing surface
            DrawSurface d = gui.getDrawSurface();
            // Move each ball one step and draw it
            for (int i = 0; i < balls.length; i++) {
                balls[i].moveOneStep(bounds);
                balls[i].drawOn(d);
            }
            // Show the updated surface with all the balls
            gui.show(d);
            // Pause for smooth animation
            sleeper.sleepFor(50);
        }
    }

    /**
     * The main method that starts the animation by reading ball sizes from command-line arguments.
     * If no arguments are provided, it prints an error message and exits.
     *
     * @param args the command-line arguments, where each argument is a ball radius.
     */
    public static void main(String[] args) {
        // Check if any arguments were provided
        if (args.length == 0) {
            System.err.println("Please provide ball sizes as arguments.");
            System.exit(1);
        }
        // Start the animation with the provided ball sizes
        Ball[] balls = generateBalls(Common.convertArgstoInt(args));
        drawAnimation(balls);
    }
}
