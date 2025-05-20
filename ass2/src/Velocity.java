// A04005777 Noah Moore
/**
 * The Velocity class represents the velocity of an object in 2D space.
 * It stores the rate of change in the x and y directions (dx and dy).
 */
public class Velocity {

    // Instance variables
    private double dx;
    private double dy;

    /**
     * Constructs a Velocity object with the given dx and dy values.
     * @param dx the change in the x direction
     * @param dy the change in the y direction
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Takes a point with position (x, y) and returns a new point
     * with position (x + dx, y + dy).
     * @param p the point to which the velocity will be applied
     * @return the new point after applying the velocity
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }

    /**
     * Returns the change in the x direction.
     * @return the dx value
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Returns the change in the y direction.
     * @return the dy value
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Creates a Velocity object from an angle and speed.
     * @param angle the angle in radians
     * @param speed the speed of the object
     * @return a new Velocity object based on the angle and speed
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        angle = Math.toRadians(angle);
        double dx = speed * Math.cos(angle);
        double dy = speed * Math.sin(angle);
        return new Velocity(dx, dy);
    }

    // calculate change over unit of time
    public double getSpeed(){
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }
}
