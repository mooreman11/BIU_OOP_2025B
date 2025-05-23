import biuoop.DrawSurface;

public class Ball {
    private Point center;
    private final int r;
    private final java.awt.Color color;
    private Velocity velocity;

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
        this.velocity = new Velocity(0, 0);
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
     * Moves the ball one step without any collision detection.
     */
    public void moveOneStep() {
        this.center = this.getVelocity().applyToPoint(this.center);
    }

    /**
     * Moves the ball one step, considering collisions with boundaries.
     * For backward compatibility with existing code.
     * @param bounds an array of bounds which balls must stay inside
     */
    public void moveOneStep(Rectangle[] bounds) {
        moveOneStep(bounds, new Rectangle[0]);
    }

    /**
     * Moves the ball one step with full collision detection.
     * @param containers Rectangles the ball must stay inside (null or empty for no containers)
     * @param obstacles Rectangles the ball must stay outside (bounce off from outside) (null or empty for no obstacles)
     */
    public void moveOneStep(Rectangle[] containers, Rectangle[] obstacles) {
        double dx = this.velocity.getDx();
        double dy = this.velocity.getDy();
        double currentX = this.center.getX();
        double currentY = this.center.getY();
        double nextX = currentX + dx;
        double nextY = currentY + dy;

        // Handle container boundaries (stay inside) if provided
        if (containers != null && containers.length > 0) {
            for (Rectangle container : containers) {
                boolean collided = false;

                // Check horizontal collisions with container
                if (nextX - this.r < container.getXLowerBound()) {
                    nextX = container.getXLowerBound() + this.r;
                    dx = Math.abs(dx); // Ensure positive velocity when bouncing off left wall
                    collided = true;
                } else if (nextX + this.r > container.getXUpperBound()) {
                    nextX = container.getXUpperBound() - this.r;
                    dx = -Math.abs(dx); // Ensure negative velocity when bouncing off right wall
                    collided = true;
                }

                // Check vertical collisions with container
                if (nextY - this.r < container.getYLowerBound()) {
                    nextY = container.getYLowerBound() + this.r;
                    dy = Math.abs(dy); // Ensure positive velocity when bouncing off bottom wall
                    collided = true;
                } else if (nextY + this.r > container.getYUpperBound()) {
                    nextY = container.getYUpperBound() - this.r;
                    dy = -Math.abs(dy); // Ensure negative velocity when bouncing off top wall
                    collided = true;
                }

                if (collided) {
                    break; // Only process one container collision
                }
            }
        }

        // Reset next position for obstacle checking
        nextX = currentX + dx;
        nextY = currentY + dy;

        // Handle obstacle boundaries (bounce off from outside) if provided
        if (obstacles != null) {
            for (Rectangle obstacle : obstacles) {
                // Check if we're currently outside the obstacle (as we should be)
                boolean currentlyOutside = !isCircleOverlappingRect(currentX, currentY, this.r, obstacle);

                if (currentlyOutside) {
                    // Check if next position would overlap with obstacle
                    boolean wouldOverlap = isCircleOverlappingRect(nextX, nextY, this.r, obstacle);

                    if (wouldOverlap) {
                        // Determine which side we're approaching from and bounce accordingly

                        // Horizontal collision
                        if (currentX + this.r <= obstacle.getXLowerBound() &&
                                nextX + this.r > obstacle.getXLowerBound()) {
                            // Approaching from left
                            dx = -Math.abs(dx);
                        } else if (currentX - this.r >= obstacle.getXUpperBound() &&
                                nextX - this.r < obstacle.getXUpperBound()) {
                            // Approaching from right
                            dx = Math.abs(dx);
                        }

                        // Vertical collision
                        if (currentY + this.r <= obstacle.getYLowerBound() &&
                                nextY + this.r > obstacle.getYLowerBound()) {
                            // Approaching from bottom
                            dy = -Math.abs(dy);
                        } else if (currentY - this.r >= obstacle.getYUpperBound() &&
                                nextY - this.r < obstacle.getYUpperBound()) {
                            // Approaching from top
                            dy = Math.abs(dy);
                        }
                    }
                }
            }
        }

        // Update position and velocity after resolving all collisions
        this.velocity = new Velocity(dx, dy);
        this.center = this.velocity.applyToPoint(this.center);
    }

    /**
     * Helper method to check if a circle overlaps with a rectangle.
     */
    private boolean isCircleOverlappingRect(double cx, double cy, int radius, Rectangle rect) {
        // Check if any part of the circle overlaps with the rectangle
        double closestX = Math.max(rect.getXLowerBound(), Math.min(cx, rect.getXUpperBound()));
        double closestY = Math.max(rect.getYLowerBound(), Math.min(cy, rect.getYUpperBound()));

        double distanceX = cx - closestX;
        double distanceY = cy - closestY;

        return (distanceX * distanceX + distanceY * distanceY) < (radius * radius);
    }
}