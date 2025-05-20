// A04005777 Noah Moore
/**
 * The Point class represents a point in 2D space with x and y coordinates.
 */
public class Point {
    // Instance variables for x and y coordinates
    private double x, y;

    /**
     * Constructs a Point object with the given x and y coordinates.
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the distance from this point to another point.
     * @param other the other point to which the distance is calculated
     * @return the distance between this point and the other point
     */
    public double distance(Point other) {
        return Math.sqrt(Math.pow(x - other.getX(), 2) + Math.pow(y - other.getY(), 2));
    }

    /**
     * Checks if this point is equal to another point.
     * Two points are considered equal if their x and y coordinates are the same.
     * @param other the other point to compare with
     * @return true if the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        return Common.thresholdComparison(this.x, other.getX()) && Common.thresholdComparison(this.y, other.getY());
    }

    /**
     * Returns the x-coordinate of this point.
     * @return the x-coordinate of the point
     */
    public double getX() {
        return this.x;
    }

    /**
     * Returns the y-coordinate of this point.
     * @return the y-coordinate of the point
     */
    public double getY() {
        return this.y;
    }
}
