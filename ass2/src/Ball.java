import biuoop.DrawSurface;

public class Ball {
    private Point center;
    private final int r;
    private final java.awt.Color color;
    public Velocity velocity;

    /**
     * Constructor to initialize a Ball with a specific center point, radius,
     * color, and velocity.
     *
     * @param center The center point of the ball.
     * @param r      The radius of the ball.
     * @param color  The color of the ball.
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.r = r;
        this.color = color;
        this.velocity = new Velocity(0, 0);
    }

    /**
     * Constructor to initialize a Ball with a specific center point, radius,
     * color, and velocity.
     *
     * @param x     The x coordinate of the center point of the ball.
     * @param y     The y coordinate of the center point of the ball.
     * @param r     The radius of the ball.
     * @param color The color of the ball.
     */
    public Ball(double x, double y, int r, java.awt.Color color) {
        this.center = new Point(x, y);
        this.r = r;
        this.color = color;
    }

    // accessors

    /**
     * Gets the x-coordinate of the ball's center.
     *
     * @return The x-coordinate of the ball.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * Gets the y-coordinate of the ball's center.
     *
     * @return The y-coordinate of the ball.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * Calculates the area of the ball.
     *
     * @return The area of the ball.
     */
    public int getSize() {
        return (int) (Math.PI * Math.pow(r, 2));
    }

    /**
     * Gets the color of the ball.
     *
     * @return The color of the ball.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    public Velocity getVelocity() {
        return this.velocity;
    }

    public void setVelocity(Velocity v){
        this.velocity = v;
    }


    /**
     * Draws the ball on the provided DrawSurface.
     *
     * @param surface The DrawSurface where the ball will be drawn.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(color);
        surface.fillCircle(this.getX(), this.getY(), this.r);
    }

    /**
     * Moves the ball one step, considering collisions with the game environment.
     */
    public void moveOneStep() {
        this.center = this.getVelocity().applyToPoint(this.center);
    }
    public void moveOneStep(Bound[] bounds) {
        this.center = this.getVelocity().applyToPoint(this.center);
    }
}