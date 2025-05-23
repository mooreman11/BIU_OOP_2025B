import biuoop.DrawSurface;
import java.awt.Color;
/**
 * The Block class represents a block with a rectangular shape and a color.
 * It implements the Collidable interface and provides methods to handle collisions and draw the block.

 */
public class Block implements Collidable, Sprite {
    private Rectangle rectangle;
    public Color color;


    public Block(Point upperLeft, double width, double height, Color color) {
        this.rectangle = new Rectangle(upperLeft, width, height);
        this.color = color;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        if (collisionPoint == null) {
            return currentVelocity;
        }

        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();

        if (this.rectangle.getUpperLine().isContaining(collisionPoint) ||
                this.rectangle.getLowerLine().isContaining(collisionPoint)) {
            dy = -dy;
        }
        if (this.rectangle.getLeftLine().isContaining(collisionPoint) ||
                this.rectangle.getRightLine().isContaining(collisionPoint)) {
            dx = -dx;
        }
        return new Velocity(dx, dy);
    }

    public void drawOn(DrawSurface surface) {
        int x = (int) this.rectangle.getUpperLeft().getX();
        int y = (int) this.rectangle.getUpperLeft().getY();
        int width = (int) this.rectangle.getWidth();
        int height = (int) this.rectangle.getHeight();
        surface.setColor(Color.black);
        surface.drawRectangle(x, y, width, height);

        // Draw the filled rectangle
        surface.setColor(this.color);
        surface.fillRectangle(x, y, width, height);

        // Draw the border of the rectangle
        surface.setColor(Color.BLACK);
        surface.drawRectangle(x, y, width, height);
    }

    public void timePassed () {
        // leave the method empty, since the block does not move
    }

    public void addToGame(Game g){
        g.addSprite(this);
        g.addCollidable(this);
    }
}