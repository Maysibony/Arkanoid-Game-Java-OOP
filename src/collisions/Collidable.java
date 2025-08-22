package collisions;

import game.Velocity;
import geometry.Point;
import geometry.Rectangle;
import sprites.Ball;
import sprites.Sprite;

/**
 * things that can be collided with.
 */
public interface Collidable {
    /**
     * @return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();
    /** Notify the object that we collided with it at collisionPoint with a given velocity.
     * @param collisionPoint
     * @param currentVelocity
     * @return is the new velocity expected after the hit (based on the force the object inflicted on us).
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
