import biuoop.DrawSurface;

import java.awt.*;

public class Rectangle {
    private final Point lowerLeftVertex;
    private final double width;
    private final double height;
    private final Color color;

    public Rectangle(Point lowerLeftVertex, double width, double height) {
        this.lowerLeftVertex = lowerLeftVertex;
        this.width = width;
        this.height = height;
        this.color = null;
    }

    public Rectangle(Point lowerLeftVertex, double width, double height, Color color) {
        this.lowerLeftVertex = lowerLeftVertex;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public double getXLowerBound(){
        return lowerLeftVertex.getX();
    }

    public double getXUpperBound(){
        return lowerLeftVertex.getX() + width;
    }

    public double getYLowerBound(){
        return lowerLeftVertex.getY();
    }

    public double getYUpperBound(){
        return lowerLeftVertex.getY() + height;
    }

    public boolean contains(Point point){
        return point.getX() >= lowerLeftVertex.getX() && point.getX() <= lowerLeftVertex.getX() + width ||
                point.getY() <= lowerLeftVertex.getY() && point.getY() <= lowerLeftVertex.getY() + height;
    }

    /**
     * Draws the ball on the provided DrawSurface.
     *
     * @param surface The DrawSurface where the ball will be drawn.
     */
    public void drawOn(DrawSurface surface) {
        if (color == null) return;
        surface.setColor(color);
        surface.fillRectangle((int) lowerLeftVertex.getX(), (int) lowerLeftVertex.getY(), ((int) width), (int) height);
    }
}
