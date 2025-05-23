import biuoop.DrawSurface;
import java.awt.*;

/**
 * The Rectangle class represents a rectangular shape in 2D space.
 * It is defined by a lower-left vertex point, width, height, and optional color.
 * This class provides methods for boundary checking, containment testing, and drawing.
 */
public class Rectangle {
    private final Point lowerLeftVertex;
    private final double width;
    private final double height;
    private final Color color;

    /**
     * Constructs a Rectangle with the specified lower-left vertex, width, and height.
     * The rectangle will have no color (null).
     *
     * @param lowerLeftVertex the lower-left corner point of the rectangle
     * @param width the width of the rectangle (must be positive)
     * @param height the height of the rectangle (must be positive)
     */
    public Rectangle(Point lowerLeftVertex, double width, double height) {
        this.lowerLeftVertex = lowerLeftVertex;
        this.width = width;
        this.height = height;
        this.color = null;
    }

    /**
     * Constructs a Rectangle with the specified lower-left vertex, width, height, and color.
     *
     * @param lowerLeftVertex the lower-left corner point of the rectangle
     * @param width the width of the rectangle (must be positive)
     * @param height the height of the rectangle (must be positive)
     * @param color the color of the rectangle for drawing purposes
     */
    public Rectangle(Point lowerLeftVertex, double width, double height, Color color) {
        this.lowerLeftVertex = lowerLeftVertex;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    /**
     * Gets the x-coordinate of the left edge of the rectangle.
     *
     * @return the x-coordinate of the lower-left vertex
     */
    public double getXLowerBound(){
        return lowerLeftVertex.getX();
    }

    /**
     * Gets the x-coordinate of the right edge of the rectangle.
     *
     * @return the x-coordinate of the lower-left vertex plus the width
     */
    public double getXUpperBound(){
        return lowerLeftVertex.getX() + width;
    }

    /**
     * Gets the y-coordinate of the bottom edge of the rectangle.
     *
     * @return the y-coordinate of the lower-left vertex
     */
    public double getYLowerBound(){
        return lowerLeftVertex.getY();
    }

    /**
     * Gets the y-coordinate of the top edge of the rectangle.
     *
     * @return the y-coordinate of the lower-left vertex plus the height
     */
    public double getYUpperBound(){
        return lowerLeftVertex.getY() + height;
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
        return point.getX() >= lowerLeftVertex.getX() &&
                point.getX() <= lowerLeftVertex.getX() + width &&
                point.getY() >= lowerLeftVertex.getY() &&
                point.getY() <= lowerLeftVertex.getY() + height;
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
        surface.fillRectangle((int) lowerLeftVertex.getX(), (int) lowerLeftVertex.getY(),
                (int) width, (int) height);
    }
}