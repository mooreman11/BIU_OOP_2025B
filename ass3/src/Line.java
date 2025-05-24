import java.util.List;

/**
 * The Line class represents a line segment defined by two points in 2D space.
 */
public class Line {

    private final Point start;
    private final Point end;

    /**
     * Constructs a Line object with the given start and end points.
     *
     * @param start the start point of the line
     * @param end the end point of the line
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Constructs a Line object with the given coordinates for the start and end
     * points.
     *
     * @param x1 the x-coordinate of the start point
     * @param y1 the y-coordinate of the start point
     * @param x2 the x-coordinate of the end point
     * @param y2 the y-coordinate of the end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Returns the length of the line segment.
     *
     * @return the length of the line segment
     */
    public double length() {
        return Math.sqrt(Math.pow(this.start.getX() - this.end.getX(), 2)
                + Math.pow(this.start.getY() - this.end.getY(), 2));
    }

    /**
     * Returns the middle point of the line segment.
     *
     * @return the middle point of the line segment
     */
    public Point middle() {
        return new Point((this.start.getX() + this.end.getX()) / 2, (this.start.getY() + this.end.getY()) / 2);
    }

    /**
     * Returns the start point of the line segment.
     *
     * @return the start point of the line
     */
    public Point start() {
        return new Point(this.start.getX(), this.start.getY());
    }

    /**
     * Returns the end point of the line segment.
     *
     * @return the end point of the line
     */
    public Point end() {
        return new Point(this.end.getX(), this.end.getY());
    }

    /**
     * Returns true if this line segment intersects with another line segment.
     *
     * @param other the other line to check for intersection
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        return this.intersectionWith(other) != null;
    }

    /**
     * Returns true if this line segment intersects with both of the other line
     * segments.
     *
     * @param other1 the first line to check for intersection
     * @param other2 the second line to check for intersection
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return this.isIntersecting(other1) && this.isIntersecting(other2)
                && !this.intersectionWith(other1).equals(this.intersectionWith(other2));
    }

    /**
     * Calculates the determinant of two line segments, used to determine if
     * they are parallel or if they intersect.
     *
     * @param a the first line segment
     * @param b the second line segment
     * @return the determinant of the two line segments
     */
    private static double getDeterminant(Line a, Line b) {
        return ((a.end.getX() - a.start.getX()) * (b.end.getY() - b.start.getY()))
                - ((a.end.getY() - a.start.getY()) * (b.end.getX() - b.start.getX()));
    }

    /**
     * Calculates the intersection point of this line segment with another line
     * segment. If the segments do not intersect or are parallel, returns
     * {@code null}.
     *
     * @param other the other line segment to check for intersection
     * @return the intersection point if the segments intersect, {@code null}
     * otherwise
     */
    public Point intersectionWith(Line other) {
        double determinant = getDeterminant(this, other);
        if (Common.thresholdComparison(determinant, 0d)) {
            return null;
        }
        double t = (((other.start.getX() - this.start.getX()) * (other.end.getY() - other.start.getY()))
                - ((other.start.getY() - this.start.getY()) * (other.end.getX() - other.start.getX()))) / determinant;
        double u = (((other.start.getX() - this.start.getX()) * (this.end.getY() - this.start.getY()))
                - ((other.start.getY() - this.start.getY()) * (this.end.getX() - this.start.getX()))) / determinant;
        if (t < 0 || t > 1 || u < 0 || u > 1) {
            return null;
        }
        double intersectionX = this.start.getX() + t * (this.end.getX() - this.start.getX());
        double intersectionY = this.start.getY() + t * (this.end.getY() - this.start.getY());
        return new Point(intersectionX, intersectionY);
    }

    /**
     * Checks if this line segment is equal to another line segment. Two line
     * segments are considered equal if their start and end points are the same.
     *
     * @param other the other line segment to compare with
     * @return true if the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        return (Common.thresholdComparison(this.start.getX(), other.start.getX())
                && Common.thresholdComparison(this.end.getX(), other.end.getX())
                && Common.thresholdComparison(this.start.getY(), other.start.getY())
                && Common.thresholdComparison(this.end.getY(), other.end.getY()));
    }


    /**
     * Store all intersections in an arraylist and then calculate Euclidean
     * distance to find the closest intersection to the start of the line
     *
     * @param rect the rectangle to check for intersection
     * @return closest intersection to the start of the line to the rectangle
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect){
        List<Point> intersections = rect.intersectionPoints(this);
        Point closestIntersection = null;
        double closestDistance = Double.MAX_VALUE;
        for (Point point : intersections) {
            double distance = point.distance(this.start);
            if (distance < closestDistance) {
                closestIntersection = point;
                closestDistance = distance;
            }
        }
        return closestIntersection;
    }

    private boolean isVerticalToAxisX() {
        return Math.abs(this.start.getX() - this.end.getX()) < Common.THRESHOLD;
    }

    private boolean isVerticalToAxisY() {
        return Math.abs(this.start.getX() - this.end.getX()) < Common.THRESHOLD;
    }

    /**
     * Checks if a point lies on the line segment.
     *
     * @param p the point to check
     * @return true if the point lies on the line segment, false otherwise
     */
    public boolean isContaining(Point p) {
        // Check if the line is vertical to the X axis
        if (this.isVerticalToAxisX()) {
            if (Math.abs(p.getX() - this.start.getX()) > Common.THRESHOLD) {
                return false;
            }
            double maxY = Math.max(this.start.getY(), this.end.getY());
            double minY = Math.min(this.start.getY(), this.end.getY());
            return p.getY() >= minY - Common.THRESHOLD && p.getY() <= maxY + Common.THRESHOLD;
        }

        // Check if the line is vertical to the Y axis
        if (this.isVerticalToAxisY()) {
            if (Math.abs(p.getY() - this.start.getY()) > Common.THRESHOLD) {
                return false;
            }
            double maxX = Math.max(this.start.getX(), this.end.getX());
            double minX = Math.min(this.start.getX(), this.end.getX());
            return p.getX() >= minX - Common.THRESHOLD && p.getX() <= maxX + Common.THRESHOLD;
        }

        // For non-vertical lines, check if the point is on the line segment
        double slope = (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
        double intercept = this.start.getY() - slope * this.start.getX();
        double expectedY = slope * p.getX() + intercept;

        return Math.abs(p.getY() - expectedY) <= Common.THRESHOLD
                && p.getX() >= Math.min(this.start.getX(), this.end.getX()) - Common.THRESHOLD
                && p.getX() <= Math.max(this.start.getX(), this.end.getX()) + Common.THRESHOLD
                && p.getY() >= Math.min(this.start.getY(), this.end.getY()) - Common.THRESHOLD
                && p.getY() <= Math.max(this.start.getY(), this.end.getY()) + Common.THRESHOLD;
    }
}
