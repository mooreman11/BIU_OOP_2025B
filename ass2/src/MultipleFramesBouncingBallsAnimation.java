// MultipleFramesBouncingBallsAnimation.java
// A04005777 Noah Moore

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Draws two frames (gray and yellow) and bounces balls either inside the gray
 * frame or outside both the gray and yellow frames (i.e., in the rest of the window).
 */
public class MultipleFramesBouncingBallsAnimation {

    // Window size
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    // Gray frame (50,50) to (500,500)
    private static final int GRAY_X = 50;
    private static final int GRAY_Y = 50;
    private static final int GRAY_WIDTH = 450;
    private static final int GRAY_HEIGHT = 450;

    // Yellow frame (450,450) to (600,600)
    private static final int YEL_X = 450;
    private static final int YEL_Y = 450;
    private static final int YEL_WIDTH = 150;
    private static final int YEL_HEIGHT = 150;

    /**
     * Generates balls: first half inside the gray frame, second half anywhere
     * in the window except inside gray or yellow.
     */
    private static Ball[] generateBalls(int[] sizes) {
        Random rand = new Random();
        Ball[] balls = new Ball[sizes.length];
        Rectangle grayFrame = new Rectangle(new Point(GRAY_X, GRAY_Y), GRAY_WIDTH, GRAY_HEIGHT);
        Rectangle yellowFrame = new Rectangle(new Point(YEL_X, YEL_Y), YEL_WIDTH, YEL_HEIGHT);

        int half = (sizes.length + 1) / 2;   // extra goes into gray if odd

        for (int i = 0; i < sizes.length; i++) {
            int r = sizes[i];
            int x, y;

            if (i < half) {
                // Place inside gray frame
                x = GRAY_X + r + rand.nextInt(GRAY_WIDTH - 2 * r);
                y = GRAY_Y + r + rand.nextInt(GRAY_HEIGHT - 2 * r);
            } else {
                //generate outside gray and yellow frames
                do {
                    x = r + rand.nextInt(WINDOW_WIDTH - 2 * r);
                    y = r + rand.nextInt(WINDOW_HEIGHT - 2 * r);
                } while (!grayFrame.contains(new Point(x, y)) && !yellowFrame.contains(new Point(x, y)));
            }

            Ball b = new Ball(x, y, r,
                    new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
            // Give it a small random velocity
            int dx = rand.nextInt(5) + 1;
            int dy = rand.nextInt(5) + 1;
            b.setVelocity(new Velocity(dx, dy));
            balls[i] = b;
        }
        return balls;
    }

    /**
     * Animates all balls. Gray‐frame balls bounce only on the gray rectangle;
     * others bounce on the window borders plus the gray & yellow frames
     * (so they stay outside both).
     */
    private static void drawAnimation(Ball[] balls) {
        GUI gui = new GUI("Multiple Frames Bouncing Balls", WINDOW_WIDTH, WINDOW_HEIGHT);
        Sleeper sleeper = new Sleeper();

        // Prepare our frames
        Rectangle grayFrame = new Rectangle(new Point(GRAY_X, GRAY_Y), GRAY_WIDTH, GRAY_HEIGHT, Color.GRAY);
        Rectangle yellowFrame = new Rectangle(new Point(YEL_X, YEL_Y), YEL_WIDTH, YEL_HEIGHT, Color.YELLOW);
        Rectangle windowBorder = new Rectangle(new Point(0, 0), WINDOW_WIDTH, WINDOW_HEIGHT);

        int half = (balls.length + 1) / 2;

        while (true) {
            DrawSurface d = gui.getDrawSurface();
            // Draw frames
            grayFrame.drawOn(d);
            yellowFrame.drawOn(d);

            for (int i = 0; i < balls.length; i++) {
                Ball b = balls[i];

                // Decide which set of bounds to use
                List<Rectangle> bounds = new ArrayList<>();
                if (i < half) {
                    // first half → only gray
                    bounds.add(grayFrame);
                } else {
                    // second half → window + gray + yellow
                    bounds.add(windowBorder);
                    bounds.add(grayFrame);
                    bounds.add(yellowFrame);
                }

                b.moveOneStep(bounds.toArray(new Rectangle[0]));
                b.drawOn(d);
            }

            gui.show(d);
            sleeper.sleepFor(50);
        }
    }
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Please provide ball sizes as arguments");
            System.exit(1);
        }
        int[] sizes = Common.convertArgstoInt(args);
        Ball[] balls = generateBalls(sizes);
        drawAnimation(balls);
    }
}
