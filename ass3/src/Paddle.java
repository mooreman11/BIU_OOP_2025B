import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.*;

public class Paddle implements Sprite, Collidable {
    private final KeyboardSensor keyboard;
    private final Rectangle paddle;

    Paddle(Rectangle paddle, KeyboardSensor keyboard) {
        this.paddle = paddle;
        this.keyboard = keyboard;
    }

    public void moveLeft() {
        double currentX = paddle.getUpperLeft().getX();
        double newX = currentX - GameConstants.STEP;
        if (newX < GameConstants.BOUND_WIDTH) {
            newX = GameConstants.BOUND_WIDTH;
        }
        paddle.setUpperLeft(new Point(newX, paddle.getUpperLeft().getY()));
    }

    public void moveRight() {
        double currentX = paddle.getUpperLeft().getX();
        double newX = currentX + GameConstants.STEP;
        double rightBound = GameConstants.GUI_WIDTH - GameConstants.BOUND_WIDTH - paddle.getWidth();
        if (newX > rightBound) {
            newX = rightBound;
        }
        paddle.setUpperLeft(new Point(newX, paddle.getUpperLeft().getY()));
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
        double paddleLeftBound = paddle.getUpperLeft().getX();
        double paddleRightBound = paddleLeftBound + paddle.getWidth();
        double paddleTop = paddle.getUpperLeft().getY();
        double paddleBottom = paddleTop + paddle.getHeight();

        boolean hitLeft = Common.thresholdComparison(collisionPoint.getX(), paddleLeftBound);
        boolean hitRight = Common.thresholdComparison(collisionPoint.getX(), paddleRightBound);

        if ((hitLeft || hitRight) && collisionPoint.getY() >= paddleTop && collisionPoint.getY() <= paddleBottom) {
            return new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }

        boolean hitTop = Common.thresholdComparison(collisionPoint.getY(), paddleTop)
                && collisionPoint.getX() >= paddleLeftBound && collisionPoint.getX() <= paddleRightBound;

        if (hitTop) {
            // determine regions of contact -- divide paddle width into 5 regions,
            // and then determine the angle of the hit and adjust the velocity accordingly.
            double regionWidth = paddle.getWidth() / 5.0;
            double hitPoint = collisionPoint.getX() - paddleLeftBound;
            int region = (int) (hitPoint / regionWidth);
            region = Math.max(0, Math.min(4, region));
            if (region == 2) {
                return new Velocity(currentVelocity.getDx(), -Math.abs(currentVelocity.getDy()));
            }
            return Velocity.fromAngleAndSpeed(GameConstants.angles[region], speed);
        }

        // handling case if a ball is stuck in the paddle
        boolean stuck = this.paddle.contains(new Point(collisionPoint.getX(), collisionPoint.getY()));
        if (stuck) {
            return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        }
        return new Velocity(-currentVelocity.getDx(), -currentVelocity.getDy());
    }

    // Add this paddle to the game.
    public void addToGame(Game g){
        g.addSprite(this);
        g.addCollidable(this);
    }
}