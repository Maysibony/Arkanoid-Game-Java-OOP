package collisions;

import geometry.Point;

/**
 * hold the information about coliision.
 */
public class CollisionInfo {
  private Point colision;
  private Collidable collidable;

    /**
     * constructor.
     * @param colision - the collision point
     * @param collidable - the colission object
     */
    public CollisionInfo(Point colision, Collidable collidable) {
        this.colision = colision;
        this.collidable = collidable;
    }

    /**
     * @return the point at which the collision occurs.
     */
    public Point collisionPoint() {
        return this.colision;
    }
    /**
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.collidable;
    }
}
