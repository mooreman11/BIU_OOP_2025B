// A04005777 Noah Moore
import biuoop.GUI;
import biuoop.DrawSurface;
import java.util.Random;
import java.awt.Color;

/**
 * The AbstractArtDrawing class generates and visualizes an abstract art piece
 * by drawing random lines, marking their midpoints, and showing intersections and triangles
 * formed by those lines on a GUI window.
 */
public class AbstractArtDrawing {

    private Line[] lines = new Line[10];
    private Point[] intersectionPoints = new Point[0];
    private Line[] triangleLines = new Line[0];

    /**
     * Generates a random point within the bounds of the drawing surface.
     *
     * @return a Point with random x and y coordinates.
     */
    private Point generateRandomPoint() {
        Random rand = new Random(); // random number generator
        int x = rand.nextInt(400) + 1; // get integer in range 1-400
        int y = rand.nextInt(300) + 1; // get integer in range 1-300
        return new Point(x, y);
    }

    /**
     * Generates a random line by creating two random points.
     *
     * @return a Line with random start and end points.
     */
    private Line generateRandomLine() {
        return new Line(generateRandomPoint(), generateRandomPoint());
    }

    /**
     * Adds three points to form a triangle by connecting them with lines.
     *
     * @param a the first point of the triangle.
     * @param b the second point of the triangle.
     * @param c the third point of the triangle.
     */
    private void addTriangle(Point a, Point b, Point c) {
        Line[] oldTriangleLines = this.triangleLines.clone();
        this.triangleLines = new Line[oldTriangleLines.length + 3];
        System.arraycopy(oldTriangleLines, 0, this.triangleLines, 0, oldTriangleLines.length);
        this.triangleLines[oldTriangleLines.length] = new Line(a, b);
        this.triangleLines[oldTriangleLines.length + 1] = new Line(b, c);
        this.triangleLines[oldTriangleLines.length + 2] = new Line(c, a);
    }

    /**
     * Adds an intersection point to the list of intersections.
     *
     * @param a the intersection point to be added.
     */
    private void addIntersection(Point a) {
        Point[] oldIntersections = this.intersectionPoints.clone();
        this.intersectionPoints = new Point[oldIntersections.length + 1];
        System.arraycopy(oldIntersections, 0, this.intersectionPoints, 0, oldIntersections.length);
        this.intersectionPoints[oldIntersections.length] = a;
    }

    /**
     * Draws random lines, calculates their intersections, and identifies triangles formed.
     * Displays all the information on a GUI window.
     */
    private void drawRandomLines() {
        GUI gui = new GUI("Random Lines", 400, 300); // Create GUI instance
        DrawSurface d = gui.getDrawSurface(); // Create DrawSurface instance

        // Generate and draw random lines
        for (int i = 0; i < 10; i++) {
            lines[i] = generateRandomLine();
            d.drawLine((int) lines[i].start().getX(), (int) lines[i].start().getY(),
                    (int) lines[i].end().getX(), (int) lines[i].end().getY());
        }

        // Calculate intersections and identify triangles
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    Point intersection1 = lines[i].intersectionWith(lines[j]);
                    Point intersection2 = lines[j].intersectionWith(lines[k]);
                    Point intersection3 = lines[i].intersectionWith(lines[k]);
                    if (i != j && j != k && lines[i].formsTriangle(lines[j], lines[k])) {
                        addTriangle(intersection1, intersection2, intersection3);
                    }
                    if (i != j && intersection1 != null) {
                        addIntersection(intersection1);
                    }
                }
            }
        }

        // Draw midpoints of lines
        for (Line line : this.lines) {
            d.setColor(Color.BLUE);
            d.fillCircle((int) line.middle().getX(), (int) line.middle().getY(), 3);
        }

        // Draw intersection points
        for (Point intersectionPoint : this.intersectionPoints) {
            d.setColor(Color.RED);
            if (intersectionPoint != null) {
                d.fillCircle((int) intersectionPoint.getX(), (int) intersectionPoint.getY(), 3);
            }
        }

        // Draw triangle lines
        for (Line triangleLine : triangleLines) {
            d.setColor(Color.GREEN);
            d.drawLine((int) triangleLine.start().getX(), (int) triangleLine.start().getY(),
                    (int) triangleLine.end().getX(), (int) triangleLine.end().getY());
        }

        gui.show(d); // Display the drawing
    }

    /**
     * Main method to execute the program.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        AbstractArtDrawing example = new AbstractArtDrawing();
        example.drawRandomLines();
    }
}
