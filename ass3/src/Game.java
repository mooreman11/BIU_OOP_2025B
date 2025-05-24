import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;

import java.awt.*;

/**
 * The Game class represents the main structure and functionality of the Arkanoid game.
 * It manages the game environment, GUI, sprites, and animation loop.
 */
public class Game {
    private final SpriteCollection sprites;
    private final GameEnvironment environment;
    private final GUI gui;
    private final Sleeper sleeper;

    // Constants for game configuration
    private static final int GUI_WIDTH = 800;
    private static final int GUI_HEIGHT = 600;
    private static final Rectangle GUI_RECT = new Rectangle(new Point(0, 0), GUI_WIDTH, GUI_HEIGHT);
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 20;
    private static final int BALL_RADIUS = 5;
    private static final Velocity BALL_VELOCITY = new Velocity(10, -10);
    private static final int BLOCK_WIDTH = 50;
    private static final int BLOCK_HEIGHT = 25;
    private static final int BLOCK_ROWS = 6;
    private static final int BLOCK_COLUMNS = 14;
    private static final int BOUND_WIDTH = 15;
    private static final int BOUND_HEIGHT = 15;

    /**
     * Constructs a new Game instance and initializes the sprite collection, game environment, GUI, and sleeper.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui = new GUI("Arkanoid", (int) GUI_RECT.getWidth(), (int) GUI_RECT.getHeight());
        this.sleeper = new Sleeper();
    }

    /**
     * Adds a Collidable object to the game environment.
     *
     * @param c the Collidable object to add.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Adds a Sprite object to the sprite collection.
     *
     * @param s the Sprite object to add.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Determines the color of blocks based on their row number.
     *
     * @param row the row number of the block.
     * @return the color assigned to the row.
     */
    public static Color getColor(int row) {
        return switch (row) {
            case 0 -> Color.GRAY;
            case 1 -> Color.RED;
            case 2 -> Color.YELLOW;
            case 3 -> Color.BLUE;
            case 4 -> Color.PINK;
            case 5 -> Color.GREEN;
            default -> Color.BLACK;
        };
    }

    /**
     * Initializes the blocks for the game, positioning them based on their row and column.
     */
    public void initializeBlocks() {
        for (int row = 0; row < BLOCK_ROWS; row++) {
            for (int column = 0; column < BLOCK_COLUMNS - row; column++) {
                int x = 800 - ((column + 1) * BLOCK_WIDTH) - BOUND_WIDTH;
                int y = 50 + row * BLOCK_HEIGHT;
                Block block = new Block(new Point(x, y), BLOCK_WIDTH, BLOCK_HEIGHT, getColor(row));
                block.addToGame(this);
            }
        }
    }

    /**
     * Initializes the ball in the game and places it above the paddle.
     */
    public void initializeBalls() {
        Point ballPosition = new Point(
                GUI_RECT.getWidth() / 2,
                GUI_RECT.getHeight() - PADDLE_HEIGHT - BOUND_WIDTH - BALL_RADIUS - 1
        );
        Ball ball = new Ball(ballPosition, BALL_RADIUS, Color.WHITE, BALL_VELOCITY, environment);
        ball.addToGame(this);
    }

    /**
     * Initializes the paddle in the game.
     */
    public void initializePaddle() {
        Rectangle paddleRect = new Rectangle(
                new Point((GUI_RECT.getWidth() - PADDLE_WIDTH) / 2,
                        GUI_RECT.getHeight() - PADDLE_HEIGHT - BOUND_WIDTH), PADDLE_WIDTH, PADDLE_HEIGHT);
        Paddle paddle = new Paddle(paddleRect, gui.getKeyboardSensor(), (int) GUI_RECT.getWidth());
        paddle.addToGame(this);
    }

    /**
     * Initializes the boundaries of the game, including the frame and background.
     */
    public void initializeBoundaries() {
        Block background = new Block(new Point(0, 0), GUI_RECT.getWidth(), GUI_RECT.getHeight(), Color.BLUE);
        Block upperBound = new Block(new Point(0, 0), GUI_RECT.getWidth(), BOUND_HEIGHT, Color.GRAY);
        Block lowerBound = new Block(new Point(0, GUI_RECT.getHeight() - BOUND_WIDTH),
                GUI_RECT.getWidth(), BOUND_HEIGHT, Color.GRAY);
        Block leftBound = new Block(new Point(0, 0), BOUND_WIDTH, GUI_RECT.getHeight(), Color.GRAY);
        Block rightBound = new Block(new Point(GUI_RECT.getWidth() - BOUND_WIDTH, 0), BOUND_WIDTH, GUI_RECT.getHeight(), Color.GRAY);
        sprites.addSprite(background);
        upperBound.addToGame(this);
        lowerBound.addToGame(this);
        leftBound.addToGame(this);
        rightBound.addToGame(this);
    }

    /**
     * Initializes the game by setting up boundaries, blocks, paddle, and balls.
     */
    public void initialize() {
        initializeBoundaries();
        initializeBlocks();
        initializePaddle();
        initializeBalls();
    }

    /**
     * Runs the game by starting the animation loop.
     */
    public void run() {
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;

        while (true) {
            if (gui.getKeyboardSensor().isPressed(KeyboardSensor.SPACE_KEY)) {
                pause();
            }

            long startTime = System.currentTimeMillis();
            DrawSurface d = gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    /**
     * Pauses the game until the SPACE key is pressed again.
     */
    private void pause() {
        while (gui.getKeyboardSensor().isPressed(KeyboardSensor.SPACE_KEY)) {
            sleeper.sleepFor(50);
        }

        boolean paused = true;
        while (paused) {
            DrawSurface d = gui.getDrawSurface();
            d.drawText(200, d.getHeight() / 2, "Game Paused - Press SPACE to Resume", 32);
            gui.show(d);

            if (gui.getKeyboardSensor().isPressed(KeyboardSensor.SPACE_KEY)) {
                while (gui.getKeyboardSensor().isPressed(KeyboardSensor.SPACE_KEY)) {
                    sleeper.sleepFor(50);
                }
                paused = false;
            }

            sleeper.sleepFor(50);
        }
    }

    /**
     * The main method to start the game.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}
