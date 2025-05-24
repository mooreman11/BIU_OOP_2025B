public class GameConstants {
    public static final int GUI_WIDTH = 800;
    public static final int GUI_HEIGHT = 600;
    public static final Rectangle GUI_RECT = new Rectangle(new Point(0, 0), GUI_WIDTH, GUI_HEIGHT);
    public static final int PADDLE_WIDTH = 100;
    public static final int PADDLE_HEIGHT = 20;
    public static final int BALL_RADIUS = 5;
    public static final Velocity BALL_VELOCITY = new Velocity(10, -10);
    public static final int BLOCK_WIDTH = 50;
    public static final int BLOCK_HEIGHT = 25;
    public static final int BLOCK_ROWS = 6;
    public static final int BLOCK_COLUMNS = 14;
    public static final int BOUND_WIDTH = 15;
    public static final int BOUND_HEIGHT = 15;
    public static final int STEP = 15;
    public static final double[] angles = {300, 330, 0, 30, 60};
}
