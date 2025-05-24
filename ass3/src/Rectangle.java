import biuoop.DrawSurface;
import java.awt.Color;

/**
 * The Rectangle class represents a rectangular shape in 2D space.
 * It is defined by a lower-left vertex point, width, height, and optional color.
 * This class provides methods for boundary checking, containment testing, and drawing.
 */
public class Rectangle {
    private Point upperLeft;
    private final double width;
    private final double height;
    private final Color color;

    /**
     * Constructs a Rectangle with the specified lower-left vertex, width, and height.
     * The rectangle will have no color (null).
     *
     * @param upperLeft the upper-left corner point of the rectangle
     * @param width the width of the rectangle (must be positive)
     * @param height the height of the rectangle (must be positive)
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        this.color = null;
    }

    // getters for rectangle's dimensions
    public double getWidth(){
        return this.width;
    }

    public double getHeight(){
        return this.height;
    }

    // getters for rectangle's vertices
    public Point getUpperLeft(){
        return new Point(upperLeft.getX(), upperLeft.getY());
    }
    public Point getLowerLeft(){
        return new Point(upperLeft.getX(), upperLeft.getY() + height);
    }
    public Point getLowerRight(){
        return new Point(upperLeft.getX() + width, upperLeft.getY() + height);
    }
    public Point getUpperRight(){
        return new Point(upperLeft.getX() + width, upperLeft.getY());
    }


    public Line getUpperLine(){
        return new Line(getUpperLeft(), getUpperRight());
    }
    public Line getLowerLine(){
        return new Line(getLowerLeft(), getLowerRight());
    }
    public Line getLeftLine(){
        return new Line(getUpperLeft(), getLowerLeft());
    }
    public Line getRightLine(){
        return new Line(getUpperRight(), getLowerRight());
    }

    public void setUpperLeft(Point p){
        this.upperLeft = p;
    }

    /**
     * Determines whether the specified point is contained within this rectangle.
     * A point is considered inside the rectangle if its coordinates fall within
     * the rectangle's boundaries (inclusive of the edges).
     *
     * @param point the point to test for containment
     * @return true if the point is within or on the boundary of the rectangle, false otherwise
     */
    public boolean contains(Point point){
        return point.getX() >= upperLeft.getX() &&
                point.getX() <= upperLeft.getX() + width &&
                point.getY() >= upperLeft.getY() + height &&
                point.getY() <= upperLeft.getY();
    }

    public java.util.List<Point> intersectionPoints(Line line){
        Line[] lines = {getUpperLine(), getLowerLine(), getLeftLine(), getRightLine()};
        java.util.List<Point> points = new java.util.ArrayList<>();
        for (Line l : lines){
            Point p = l.intersectionWith(line);
            if(p != null){
                points.add(p);
            }
        }
        return points;
    }

    /**
     * Draws this rectangle on the provided DrawSurface.
     * If the rectangle has no color (color is null), nothing will be drawn.
     * The rectangle is drawn as a filled rectangle using the rectangle's color.
     *
     * @param surface the DrawSurface where the rectangle will be drawn
     */
    public void drawOn(DrawSurface surface) {
        if (color == null) return;
        surface.setColor(color);
        surface.fillRectangle((int) upperLeft.getX(), (int) upperLeft.getY(),
                (int) width, (int) height);
    }
}