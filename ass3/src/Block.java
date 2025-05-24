import biuoop.DrawSurface;
import java.awt.Color;

/**
 * The Block class represents a block with a rectangular shape and a color.
 * It implements the Collidable and Sprite interfaces, providing functionality for handling collisions
 * and drawing the block on a surface.
 */
public class Block implements Collidable, Sprite {
    private final Rectangle rectangle;
    private final Color color;

    /**
     * Constructs a Block with the given upper-left corner, width, height, and color.
     *
     * @param upperLeft The upper-left corner of the block.
     * @param width     The width of the block.
     * @param height    The height of the block.
     * @param color     The color of the block.
     */
    public Block(Point upperLeft, double width, double height, Color color) {
        this.rectangle = new Rectangle(upperLeft, width, height);
        this.color = color;
    }

    /**
     * Gets the collision rectangle of the block.
     *
     * @return The rectangle representing the block's shape.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * Handles a collision with the block, changing the velocity based on the collision point.
     *
     * @param hitter          The ball that hits the block.
     * @param collisionPoint  The point where the collision occurred.
     * @param currentVelocity The current velocity of the ball.
     * @return The new velocity after the collision.
     */
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

    /**
     * Draws the block on the given DrawSurface.
     *
     * @param surface The DrawSurface on which the block is drawn.
     */
    public void drawOn(DrawSurface surface) {
        int x = (int) this.rectangle.getUpperLeft().getX();
        int y = (int) this.rectangle.getUpperLeft().getY();
        int width = (int) this.rectangle.getWidth();
        int height = (int) this.rectangle.getHeight();

        // Draw the filled rectangle
        surface.setColor(this.color);
        surface.fillRectangle(x, y, width, height);

        // Draw the border of the rectangle
        surface.setColor(Color.BLACK);
        surface.drawRectangle(x, y, width, height);
    }

    /**
     * Updates the block's state as time passes. Since the block does not move, this method is empty.
     */
    @Override
    public void timePassed() {
        // No implementation needed for static blocks.
    }

    /**
     * Adds the block to the game as both a Collidable and a sprite.
     *
     * @param g The game to which the block is added.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}
