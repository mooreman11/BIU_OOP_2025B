/**
 * The Collidable interface represents an object that can be collided with.
 * Classes implementing this interface must provide methods to return the collision shape
 * and handle the collision logic.
 */
public interface Collidable {

    /**
     * Returns the "collision shape" of the object.
     *
     * @return A Rectangle representing the shape of the collidable object.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notifies the object that a collision occurred at the given collision point with a specified velocity.
     * Calculates and returns the new velocity after the hit based on the object's effect on the ball.
     *
     * @param hitter          The Ball that hit the collidable object.
     * @param collisionPoint  The point where the collision occurred.
     * @param currentVelocity The velocity of the ball before the collision.
     * @return The new Velocity of the ball after the collision.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
