package sprites;
import biuoop.DrawSurface;
import collisions.Collidable;
import collisions.HitListener;
import game.Game;
import game.Velocity;
import geometry.Point;
import geometry.Rectangle;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import collisions.HitNotifier;

/**
 * Blocks for games.
 *  implements Collidable.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle rectangle;
    private Color color;
    List<HitListener> hitListeners  = new ArrayList<>();

    /**
     * Constructor of block.
     * @param rectangle
     */
    public Block(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
    /**
     * Constructor of block.
     * @param rectangle
     * @param color
     */
    public Block(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
    }
    /**
     * get color.
     */
    public Color getColor (){
        return this.color;
    }
    /**
     * @return the rectangle shape of the block.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }
    /**
     * add sprite to game.
     * @param game
     */
    @Override
    public void addToGame(Game game) {
        game.addSprite(this);
        game.addCollidable(this);
    }
    /**
     * Removes the block from the game.
     * @param game
     */
    @Override
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }
    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     * The return is the new velocity expected after the hit (based on the force the object inflicted on us).
     * @param collisionPoint
     * @param currentVelocity
     * @return the new velocity expected
     */
    public Velocity hit(Ball hitter,Point collisionPoint, Velocity currentVelocity) {
        if (!ballColorMatch(hitter)) {
            this.notifyHit(hitter);
        }
        //Block sides
        double left = this.rectangle.getUpperLeft().getX();
        double right = left + this.rectangle.getWidth();
        double top = this.rectangle.getUpperLeft().getY();
        double bottom = top + this.rectangle.getHeight();
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        double epsilon = 0.1;
        // collision left or right sides
        boolean hitLeftEdge = Math.abs(collisionPoint.getX() - left) < epsilon;
        boolean hitRightEdge = Math.abs(collisionPoint.getX() - right) < epsilon;
        // collision top or bottom
        boolean hitTopEdge = Math.abs(collisionPoint.getY() - top) < epsilon;
        boolean hitBottomEdge = Math.abs(collisionPoint.getY() - bottom) < epsilon;
        if ((hitLeftEdge || hitRightEdge) && (hitTopEdge || hitBottomEdge)) {
            return new Velocity(-dx, -dy);
        }
        if (hitLeftEdge || hitRightEdge) {
            return new Velocity(-dx, dy);
        }
        if (hitTopEdge || hitBottomEdge) {
            return new Velocity(dx, -dy);
        }
        return currentVelocity;
    }
    /**
     * draw a block.
     * @param d
     */
    @Override
    public void drawOn(DrawSurface d) {
        // Set the color for this block
        d.setColor(this.color);

        // Get the rectangle dimensions
        int x = (int) this.rectangle.getUpperLeft().getX();
        int y = (int) this.rectangle.getUpperLeft().getY();
        int width = (int) this.rectangle.getWidth();
        int height = (int) this.rectangle.getHeight();
        d.fillRectangle(x, y, width, height);
        d.setColor(Color.lightGray);
        d.drawRectangle(x, y, width, height);
    }
    /**
     * notify the sprite that time has passed.
     * Course instruction - do not implement because block does not change.
     */
    @Override
    public void timePassed() {
        return;
    }
    /**
     * @param ball
     * @return true if the color of the ball and the block match.
     */
    public boolean ballColorMatch(Ball ball) {
        return this.color.equals(ball.getColor());
    }

    /**
     * Notify all listeners about a hit event.
     * @param hitter
     */
    private void notifyHit(Ball hitter) {
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
    /**
     * Add hl as a listener to hit events.
     */
    public void addHitListener(HitListener hl){
        this.hitListeners.add(hl);
    }
    /**
     * Remove hl from the list of listeners to hit events.
     */
    public void removeHitListener(HitListener hl){
        this.hitListeners.remove(hl);
    }
}
