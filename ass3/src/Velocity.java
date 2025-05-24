/**
 * Represents velocity in 2D space with horizontal (dx) and vertical (dy) components.
 */
public class Velocity {

    private final double dx;
    private final double dy;

    /**
     * Creates a Velocity with a given dx and dy.
     * @param dx change in x direction
     * @param dy change in y direction
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Moves a point by this velocity.
     * @param p the point to move
     * @return new point after applying velocity
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }

    /**
     * @return horizontal velocity component (dx)
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * @return vertical velocity component (dy)
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Creates Velocity from an angle (degrees) and speed.
     * Angle 0 is up, 90 is right, 180 is down, 270 is left.
     * @param angle angle in degrees
     * @param speed speed magnitude
     * @return new Velocity object
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double convertedAngle = angle - 90;
        double radians = Math.toRadians(convertedAngle);
        double dx = speed * Math.cos(radians);
        double dy = speed * Math.sin(radians);
        return new Velocity(dx, dy);
    }

    /**
     * @return the speed (magnitude) of the velocity vector
     */
    public double getSpeed() {
        return Math.sqrt(dx * dx + dy * dy);
    }
}