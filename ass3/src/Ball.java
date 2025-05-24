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
    private final GameEnvironment gameEnvironment;

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
     * @param collisionObject The object that was hit.
     * @return The adjusted position of the ball.
     */
    public Point adjustPosition(Point collisionPoint, Collidable collisionObject) {
        double adjustmentDistance = 1.0; // Small distance to move away from collision

        Rectangle rect = collisionObject.getCollisionRectangle();
        double rectLeft = rect.getUpperLeft().getX();
        double rectRight = rectLeft + rect.getWidth();
        double rectTop = rect.getUpperLeft().getY();
        double rectBottom = rectTop + rect.getHeight();

        // Determine which side was hit and adjust accordingly
        double distToLeft = Math.abs(collisionPoint.getX() - rectLeft);
        double distToRight = Math.abs(collisionPoint.getX() - rectRight);
        double distToTop = Math.abs(collisionPoint.getY() - rectTop);
        double distToBottom = Math.abs(collisionPoint.getY() - rectBottom);

        double minDist = Math.min(Math.min(distToLeft, distToRight), Math.min(distToTop, distToBottom));

        double adjustedX = center.getX();
        double adjustedY = center.getY();

        // Adjust the position based on the side that was hit with consideration to the radius of the ball
        if (minDist == distToLeft) {
            adjustedX = rectLeft - this.r - adjustmentDistance;
        } else if (minDist == distToRight) {
            adjustedX = rectRight + this.r + adjustmentDistance;
        } else if (minDist == distToTop) {
            adjustedY = rectTop - this.r - adjustmentDistance;
        } else if (minDist == distToBottom) {
            adjustedY = rectBottom + this.r + adjustmentDistance;
        }

        return new Point(adjustedX, adjustedY);
    }

    /**
     * Moves the ball one step, checking for collisions.
     */
    public void moveOneStep() {
        Line trajectory = this.calculateTrajectory();
        CollisionInfo hit = this.gameEnvironment.getClosestCollision(trajectory);

        if (hit != null) {
            // Get new velocity from the hit object
            Velocity newVelocity = hit.getCollisionObject().hit(this, hit.getCollisionPoint(), this.velocity);

            // Adjust position to prevent sticking
            this.center = this.adjustPosition(hit.getCollisionPoint(), hit.getCollisionObject());

            // Set the new velocity
            this.velocity = newVelocity;
        } else {
            // No collision, move normally
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