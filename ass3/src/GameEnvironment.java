import java.util.ArrayList;
import java.util.List;

/**
 * Manages all Collidable objects in the game.
 * Handles collision detection and provides information about the closest collision
 * along a given trajectory.
 */
public class GameEnvironment {
    private final List<Collidable> collidables;

    /**
     * Constructs a new GameEnvironment with an empty list of Collidable objects.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    /**
     * Adds a Collidable object to the environment.
     *
     * @param c the Collidable object to add
     */
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }

    /**
     * Determines the closest collision along a given trajectory.
     * Checks all Collidable objects in the environment for intersections
     * with the trajectory and returns the closest collision point, if any.
     *
     * @param trajectory the trajectory of the moving object
     * @return a CollisionInfo object representing the closest collision,
     *         or null if no collision is detected
     */
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
