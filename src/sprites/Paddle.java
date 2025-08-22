package sprites;
import biuoop.DrawSurface;
import collisions.Collidable;
import geometry.Point;
import geometry.Rectangle;
import game.Velocity;
import game.Game;

import java.awt.Color;

/**
 * paddle for the game.
 */
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Rectangle bounds;
    private static final int  BLOCKTHICKNESS = 20;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int SPEED = 5;


    /**
     * constructor.
     * @param keyboard
     */
    public Paddle(biuoop.KeyboardSensor keyboard) {
        this.keyboard = keyboard;
        this.bounds = new Rectangle(new Point(WIDTH / 2, HEIGHT - (BLOCKTHICKNESS *  2)),
                BLOCKTHICKNESS * 5, BLOCKTHICKNESS);
    }
    /**
     * If the user press left button, the paddle will move to the left.
     */
    public void moveLeft() {
        if (this.bounds.getUpperLeft().getX() - SPEED <= 0) {
            Point newPoint = new Point(WIDTH, HEIGHT - (BLOCKTHICKNESS *  2));
            this.bounds.setUpperLeft(newPoint);
        } else {
            this.bounds.moveHorizontall(-SPEED);
        }
    }
    /**
     * If the user press right button, the paddle will move to the right.
     */
    public void moveRight() {
        double rightSideX = this.bounds.getUpperLeft().getX()
                + this.bounds.getWidth();
        if (rightSideX - SPEED >= WIDTH) {
            Point newPoint = new Point(0, HEIGHT - (BLOCKTHICKNESS *  2));
            this.bounds.setUpperLeft(newPoint);
        } else {
            this.bounds.moveHorizontall(SPEED);
        }
    }
    /**
     * moving the paddle.
     */
    @Override
    public void timePassed() {
        if (keyboard.isPressed(biuoop.KeyboardSensor.LEFT_KEY)) {
            this.moveLeft();
        } else if (keyboard.isPressed(biuoop.KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
        }
    }
    /**
     * draw the paddle.
     * @param d
     */
    public void drawOn(DrawSurface d)
    {
        Color yellow = new Color(255, 213, 140);
        this.bounds.drawOn(d, yellow);
    }
    /**
     * Collidable.
     * @return collision rectangle.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.bounds;
    }
    /**
     * hit.
     * @param collisionPoint
     * @param currentVelocity
     * @return
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Rectangle collisionRectangle = this.getCollisionRectangle();
        double parts = collisionRectangle.getWidth() / 5;
        double hitX = collisionPoint.getX();
        double upperLeftX = collisionRectangle.getUpperLeft().getX();
        int ballSpeed = 3;
        int area = (int) ((hitX - upperLeftX) / parts) + 1;
        double angle;
        //if ball collide the sides of the paddle
        if (area < 1) {
            area = 1;
        } else if (area > 5) {
            area = 5;
        }
        switch (area) {
            case 1:
                angle = 300;
                break;
            case 2:
                angle = 330;
                break;
            case 3: //vertical
                return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
            case 4:
                angle = 30;
                break;
            case 5:
                angle = 60;
                break;
            default:
                return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        }
        currentVelocity = Velocity.fromAngleAndSpeed(angle, ballSpeed);
        return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
    }
    /**
     * Add this paddle to the game.
     * @param g game
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
    /**
     * remove this paddle to the game.
     * @param g game
     */
    public void removeFromGame(Game g) {
        g.removeSprite(this);
        g.removeCollidable(this);
    }
}
