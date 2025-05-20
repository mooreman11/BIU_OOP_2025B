// A04005777 Noah Moore
/**
 * The Line class represents a line segment defined by two points in 2D space.
 */
public class Line {

    private Point start, end;

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
     * Determines if the given lines intersect each other to form a triangle.
     *
     * @param other1 the first line segment
     * @param other2 the second line segment
     * @return true if the lines form a triangle, false otherwise
     */
    public boolean formsTriangle(Line other1, Line other2) {
        return this.isIntersecting(other1, other2) && other1.isIntersecting(this, other2)
                && other2.isIntersecting(other1, this);
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
}
