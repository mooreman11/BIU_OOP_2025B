/**
 * The CollisionInfo class represents information about a collision, including
 * the point of collision and the object involved in the collision.
 */
public class CollisionInfo {
    private final Point collisionPoint;
    private final Collidable collisionObject;

    /**
     * Constructs a CollisionInfo object with the specified collision point and collidable object.
     *
     * @param collisionPoint  The point at which the collision occurs.
     * @param collisionObject The collidable object involved in the collision.
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * Gets the point at which the collision occurs.
     *
     * @return The collision point as a Point object.
     */
    public Point getCollisionPoint() {
        return this.collisionPoint;
    }

    /**
     * Gets the collidable object involved in the collision.
     *
     * @return The collidable object as a Collidable.
     */
    public Collidable getCollisionObject() {
        return this.collisionObject;
    }
}
