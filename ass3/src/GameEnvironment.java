import java.util.ArrayList;
import java.util.List;

public class GameEnvironment {
    private List<Collidable> collidables;

    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    public void addCollidable(Collidable c) {
        collidables.add(c);
    }

    // Assume an object moving from line.start() to line.end().
    // If this object will not collide with any of the collidables
    // in this collection, return null. Else, return the information
    // about the closest collision that is going to occur.
    public CollisionInfo getClosestCollision(Line trajectory) {
        CollisionInfo closestCollision = null;
        double closestDistance = Double.MAX_VALUE;
        for (Collidable collidable : collidables) {
            Rectangle rect = collidable.getCollisionRectangle();
            Point collision = trajectory.closestIntersectionToStartOfLine(rect);
            if (collision != null) {
                double distance = trajectory.start().distance(collision);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestCollision = new CollisionInfo(collision, collidable);
                }
            }
        }
        return closestCollision;
    }
}