public class CollisionInfo {
    // the point at which the collision occurs.
    public Point collisionPoint;
    // the collidable object involved in the collision.
    public Collidable collisionObject;

    public CollisionInfo(Point collisionPoint, Collidable collisionObject){
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    public Point getCollisionPoint() {
        return this.collisionPoint;
    }

    public Collidable getCollisionObject() {
        return this.collisionObject;
    }
}