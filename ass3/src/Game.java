import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;

import java.awt.*;

public class Game {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Sleeper sleeper;

    // Constants
    private static final Rectangle GUI_RECT = new Rectangle(new Point(0, 0), 800, 600);
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 20;
    private static final int BALL_RADIUS = 5;
    private static final Velocity BALL_VELOCITY = new Velocity(10, -10);
    private static final int BLOCK_WIDTH = 50;
    private static final int BLOCK_HEIGHT = 25;
    private static final int BLOCK_ROWS = 6;
    private static final int BLOCK_COLUMNS = 14;
    private static final int BOUND_WIDTH = 15;

    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui = new GUI("Arkanoid", (int) GUI_RECT.getWidth(), (int) GUI_RECT.getHeight());
        this.sleeper = new Sleeper();
    }

    public void addCollidable(Collidable c){
        this.environment.addCollidable(c);
    }

    public void addSprite(Sprite s){
        this.sprites.addSprite(s);
    }

    public static Color getColor(int row){
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

    public void initializeBlocks(){
        for (int row = 0; row < BLOCK_ROWS; row++) {
            for (int column = 0; column < BLOCK_COLUMNS - row; column++) {
                int x = 800 - ((column + 1) * BLOCK_WIDTH) - BOUND_WIDTH;
                int y = 50 + row * (BLOCK_HEIGHT);
                Block block = new Block(new Point(x, y), BLOCK_WIDTH, BLOCK_HEIGHT, getColor(row));
                block.addToGame(this);
            }
        }
    }

    public void initializeBalls(){
        Point ballPosition = new Point(
                GUI_RECT.getWidth() / 2,
                GUI_RECT.getHeight() - PADDLE_HEIGHT - BOUND_WIDTH - BALL_RADIUS - 1 // Start right above the paddle
        );
        Ball ball = new Ball(ballPosition, BALL_RADIUS, Color.WHITE, BALL_VELOCITY, environment);
        ball.addToGame(this);
    }

    public void initializePaddle(){
        Rectangle paddleRect = new Rectangle(
                new Point((GUI_RECT.getWidth() - PADDLE_WIDTH) / 2,
                        GUI_RECT.getHeight() - PADDLE_HEIGHT - BOUND_WIDTH), PADDLE_WIDTH, PADDLE_HEIGHT);
        Paddle paddle = new Paddle(paddleRect, gui.getKeyboardSensor(), (int) GUI_RECT.getWidth());
        paddle.addToGame(this);
    }

    public void initializeBoundries(){
        Block background = new Block(new Point(BOUND_WIDTH, BOUND_WIDTH), GUI_RECT.getWidth() - (2 * BOUND_WIDTH),
                GUI_RECT.getHeight() - (2 * BOUND_WIDTH), Color.BLUE);
        Block upperBound = new Block(new Point(0, 0), GUI_RECT.getWidth(), BOUND_WIDTH, Color.GRAY);
        Block lowerBound = new Block(new Point(0, GUI_RECT.getHeight() - BOUND_WIDTH), GUI_RECT.getWidth(),
                BOUND_WIDTH, Color.GRAY);
        Block leftBound = new Block(new Point(0, 0), BOUND_WIDTH, GUI_RECT.getHeight(), Color.GRAY);
        Block rightBound = new Block(new Point(GUI_RECT.getWidth()-BOUND_WIDTH, 0), BOUND_WIDTH, GUI_RECT.getHeight(), Color.GRAY);
        upperBound.addToGame(this);
        lowerBound.addToGame(this);
        leftBound.addToGame(this);
        rightBound.addToGame(this);
        sprites.addSprite(background);

    }

    // Initialize a new game: create the Blocks and Ball (and Paddle)
    // and add them to the game.
    public void initialize() {
        initializeBoundries();
        initializeBlocks();
        initializePaddle();
        initializeBalls();
    }

    // Run the game -- start the animation loop.
    public void run() {
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        boolean running = true;

        while (running) {
            // Check for pause
            if (gui.getKeyboardSensor().isPressed(KeyboardSensor.SPACE_KEY)) {
                pause();
            }

            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();
            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    private void pause() {
        // Wait until the SPACE key is released
        while (gui.getKeyboardSensor().isPressed(KeyboardSensor.SPACE_KEY)) {
            sleeper.sleepFor(50); // Avoid busy-waiting
        }

        boolean paused = true;
        while (paused) {
            DrawSurface d = gui.getDrawSurface();
            d.drawText(200, d.getHeight() / 2, "Game Paused - Press SPACE to Resume", 32);
            gui.show(d);

            // Check if the SPACE key is pressed again to resume
            if (gui.getKeyboardSensor().isPressed(KeyboardSensor.SPACE_KEY)) {
                // Wait until the SPACE key is released before exiting the pause
                while (gui.getKeyboardSensor().isPressed(KeyboardSensor.SPACE_KEY)) {
                    sleeper.sleepFor(50); // Avoid busy-waiting
                }
                paused = false;
            }

            // Sleep briefly to avoid excessive CPU usage
            sleeper.sleepFor(50);
        }
    }


    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}