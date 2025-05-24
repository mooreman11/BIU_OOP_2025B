import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.*;

public class Paddle implements Sprite, Collidable {
    private final KeyboardSensor keyboard;
    private final Rectangle paddle;
    private final int GUI_WIDTH;
    private static final int STEP = 15;

    Paddle(Rectangle paddle, KeyboardSensor keyboard, int GUI_WIDTH) {
        this.paddle = paddle;
        this.keyboard = keyboard;
        this.GUI_WIDTH = GUI_WIDTH;
    }

    public void moveLeft(){
        double x = paddle.getUpperLeft().getX() - STEP >= 0 ? paddle.getUpperLeft().getX() - STEP :
                GUI_WIDTH - (paddle.getUpperLeft().getX() + STEP % GUI_WIDTH);
        paddle.setUpperLeft(new Point(x, paddle.getUpperLeft().getY()));
    }

    public void moveRight(){
        double x = paddle.getUpperLeft().getX() + STEP <= GUI_WIDTH ? paddle.getUpperLeft().getX() + STEP :
                (paddle.getUpperLeft().getX() + STEP) % GUI_WIDTH;
        paddle.setUpperLeft(new Point(x, paddle.getUpperLeft().getY()));
    }

    // Sprite
    @Override
    public void timePassed(){
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY) || keyboard.isPressed("a")) {
            moveLeft();
        }
        else if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY) || keyboard.isPressed("d")) {
            moveRight();
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.ORANGE);
        d.fillRectangle(
                (int) this.paddle.getUpperLeft().getX(),
                (int) this.paddle.getUpperLeft().getY(),
                (int) this.paddle.getWidth(),
                (int) this.paddle.getHeight()
        );
    }

    // Collidable
    @Override
    public Rectangle getCollisionRectangle(){
        return new Rectangle(
                new Point(this.paddle.getUpperLeft().getX(), this.paddle.getUpperLeft().getY()),
                this.paddle.getWidth(),
                this.paddle.getHeight()
        );
    }
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        if (collisionPoint == null) {
            return currentVelocity;
        }

        double speed = currentVelocity.getSpeed();

        // First check if we hit the sides of the paddle
        if (Common.thresholdComparison(collisionPoint.getX(), paddle.getUpperLeft().getX()) ||
                Common.thresholdComparison(collisionPoint.getX(), (paddle.getLowerLine().end().getX()))) {
            return new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }

        // For top hits, divide paddle into regions
        double regionWidth = this.paddle.getWidth() / 5;
        double hitPoint = collisionPoint.getX() - this.paddle.getUpperLeft().getX();
        int region = (int) (hitPoint / regionWidth);

        // Ensure region is within bounds
        region = Math.max(0, Math.min(4, region));

        // Define angles for each region (from left to right)
        double[] angles = {300, 330, 0, 30, 60};

        // For direct upward reflection in middle region
        if (region == 2) {
            return new Velocity(currentVelocity.getDx(), -Math.abs(currentVelocity.getDy()));
        }
        return Velocity.fromAngleAndSpeed(angles[region], speed);
    }

    // Add this paddle to the game.
    public void addToGame(Game g){
        g.addSprite(this);
        g.addCollidable(this);
    }
}