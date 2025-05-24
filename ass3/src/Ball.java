import biuoop.DrawSurface;

/**
 * The Ball class represents a ball object that has properties such as center point, radius, color, velocity, 
 * and game environment. The ball can be drawn, moved, and participate in game mechanics such as collisions.
 * Implements the Sprite interface.
 */
public class Ball implements Sprite {
    private Point center;
    private final int r;
    private final java.awt.Color color;
    private Velocity velocity;
    private GameEnvironment gameEnvironment;

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
        this.velocity = new Velocity(0, 0);
    }

    /**
     * Constructor to initialize a Ball with center, radius, color, velocity, 
     * and game environment.
     *
     * @param center          The center point of the ball.
     * @param r               The radius of the ball.
     * @param color           The color of the ball.
     * @param velocity        The velocity of the ball.
     * @param gameEnvironment The game environment of the ball.
     */
    public Ball(Point center, int r, java.awt.Color color, Velocity velocity, GameEnvironment gameEnvironment) {
        this.center = center;
        this.r = r;
        this.color = color;
        this.velocity = velocity;
        this.gameEnvironment = gameEnvironment;
    }

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
     * Gets the radius of the ball.
     *
     * @return The radius of the ball.
     */
    public int getRadius() {
        return this.r;
    }

    /**
     * Gets the color of the ball.
     *
     * @return The color of the ball.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Gets the velocity of the ball.
     *
     * @return The velocity of the ball.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param v The velocity to set.
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Draws the ball on the provided DrawSurface.
     *
     * @param surface The DrawSurface where the ball will be drawn.
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(color);
        surface.fillCircle(this.getX(), this.getY(), this.r);
    }

    /**
     * Calculates the trajectory of the ball based on its current velocity.
     *
     * @return A Line object representing the trajectory of the ball.
     */
    public Line calculateTrajectory() {
        Point endPoint = getVelocity().applyToPoint(center);
        return new Line(center, endPoint);
    }

    /**
     * Adjusts the position of the ball to avoid "sticking" to objects upon collision.
     *
     * @param collisionPoint The point of collision.
     * @param velocity       The velocity of the ball at the time of collision.
     * @return The adjusted position of the ball.
     */
    public Point adjustPosition(Point collisionPoint, Velocity velocity) {
        double adjustmentFactor = this.r + 0.1;
        double adjustedX = collisionPoint.getX();
        double adjustedY = collisionPoint.getY();
        if (velocity.getDx() < 0) adjustedX += adjustmentFactor;
        else if (velocity.getDx() > 0) adjustedX -= adjustmentFactor;
        if (velocity.getDy() < 0) adjustedY += adjustmentFactor;
        else if (velocity.getDy() > 0) adjustedY -= adjustmentFactor;
        return new Point(adjustedX, adjustedY);
    }

    /**
     * Moves the ball one step, checking for collisions.
     */
    public void moveOneStep() {
        Line trajectory = this.calculateTrajectory();
        CollisionInfo hit = this.gameEnvironment.getClosestCollision(trajectory);
        if (hit != null) {
            Velocity newVelocity = hit.getCollisionObject().hit(this, hit.getCollisionPoint(), this.velocity);
            this.center = this.adjustPosition(hit.getCollisionPoint(), this.velocity);
            this.velocity = newVelocity;
        } else {
            this.center = this.getVelocity().applyToPoint(this.center);
        }
    }

    /**
     * Updates the ball's state for a single time step.
     */
    @Override
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * Adds the ball to the game as a sprite.
     *
     * @param g The game to add the ball to.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }
}
