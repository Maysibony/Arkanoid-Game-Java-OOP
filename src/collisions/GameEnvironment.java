package collisions;
import java.util.ArrayList;
import java.util.List;
import geometry.Point;
import geometry.Rectangle;
import geometry.Line;

/**
 * a collection of collidable objects.
 * The ball will know the game environment, and will use it to check for collisions and direct its movement.
 */
public class GameEnvironment {
    private List<Collidable> collidables;

    /**
     * constructor.
     */
    public GameEnvironment() {
        collidables = new ArrayList<Collidable>();
    }
    /**
     * add the given collidable to the environment.
     * @param c - collidable object.
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }
     /** remove the given collidable to the environment.
     * @param c - collidable object.
     * */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }
    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables in this collection, return null.
     * Else, return the information about the closest collision that is going to occur.
     * @param trajectory
     * @return closest Collision that is going to occur.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        CollisionInfo closestCollision = null;
       double minDistance = Double.MAX_VALUE;
        for (Collidable collidable : collidables) {
            Rectangle rectangle = collidable.getCollisionRectangle();
            Point intersection = trajectory.closestIntersectionToStartOfLine(rectangle);
            if (intersection != null) {
                double curDistance = trajectory.start().distance(intersection);
                if (curDistance < minDistance) {
                    minDistance = curDistance;
                    closestCollision = new CollisionInfo(intersection, collidable);
                }
            }
        }
        return closestCollision;
    }
}
