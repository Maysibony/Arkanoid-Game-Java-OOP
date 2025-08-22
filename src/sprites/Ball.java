package sprites;
import biuoop.DrawSurface;
import java.awt.Color;
import java.util.Random;
import collisions.CollisionInfo;
import collisions.HitListener;
import game.Game;
import geometry.Line;
import geometry.Point;
import game.Velocity;
import collisions.GameEnvironment;

/**
 * Represents a ball object defined by center point, radius and color.
 */
public class Ball implements Sprite {
    private Point center;
    private int radius;
    private Color color;
    private Velocity velocity;
    private GameEnvironment environment;


    /**
     * constructor of ball object.
     * @param center the center point of the ball
     * @param r the radius of the circle
     * @param color the color of the ball.
     */
    public Ball(Point center, int r, Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
    }
    /**
     * constructor of ball object.
     * @param x the center.x point of the ball
     * @param y the center.y point of the ball
     * @param r the radius of the circle
     * @param color the color of the ball.
     */
    public Ball(double x, double y, int r, Color color) {
        this.center = new Point(x, y);
        this.radius = r;
        this.color = color;
    }

    /**
     * get x of center point.
     * @return x of center point.
     */
    public int getX() {
        return (int) this.center.getX();
    }
    /**
     * get y of center point.
     * @return y of center point.
     */
    public int getY() {
        return (int) this.center.getY();
    }
    /**
     * get radius of the ball.
     * @return radius.
     */
    public int getSize() {
        return this.radius;
    }
    /**
     * get the color of the ball.
     * @return the color of the ball.
     */
    public Color getColor() {
        return this.color;
    }
    /**
     * get the color of the ball.
     * @return the color of the ball.
     */
    public void setColor(Color color) {
        this.color = color;
    }
    /**
     * draw the ball on the given DrawSurface.
     * @param surface to draw on.
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle((int) center.getX(), (int) center.getY(), radius);
    }
    /**
     * notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {
        this.moveOneStep();
    }
    /**
     *  Set velocity of the ball.
     * @param v the new velocity.
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }
    /**
     *  Set velocity of the ball.
     * @param dx of the velocity.
     * @param dy of the velocity.
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }
    /**
     *  @return the velocity of the ball.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }
    /**
     * set velocity by the radius of the ball.
     * radius bigger than 50 gets the same speed.
     * @param radius
     * @param speed
     */
    public void setVelocityBySize(int radius, double speed) {
        Random rand = new Random();
        double angle = rand.nextDouble() * 360;
        int bigRadius = 50; //all balls with radius bigger than 50 have the same speed
        Velocity v = radius < bigRadius
                ? Velocity.fromAngleAndSpeed(angle, (speed / radius) + 1)
                : Velocity.fromAngleAndSpeed(angle, speed / bigRadius);
        this.setVelocity(v);
    }
    /**
     * create game environment.
     * @param environment
     */
    public void setGameEnvironment(GameEnvironment environment) {
        this.environment = environment;
    }
    /**
     * add sprite to game.
     * @param game
     */
    @Override
    public void addToGame(Game game) {
        game.addSprite(this);
    }
    /**
     * remove sprite to game.
     * @param game
     */
    @Override
    public void removeFromGame(Game game) {
        game.removeSprite(this);
    }
    /**
     * change the location of the ball with the screen size 600X800.
     */
    public void moveOneStep() {
        Point curLocation = this.center;
        Point newLocation = this.getVelocity().applyToPoint(this.center);
        Line trajectory = new Line(curLocation, newLocation);
        CollisionInfo collision = this.environment.getClosestCollision(trajectory);
        if (collision == null) {
            this.center = newLocation;
        } else {
            Point colPoint = collision.collisionPoint();
            double dx = this.velocity.getDx();
            double dy = this.velocity.getDy();
            double length = Math.sqrt(dx * dx + dy * dy);
            double epsilon = 0.1;
            double newDx = dx / length;
            double newDy = dy / length;
            double newX = colPoint.getX() - newDx * epsilon;
            double newY = colPoint.getY() - newDy * epsilon;
            this.center = new Point(newX, newY);
            this.velocity = collision.collisionObject().hit(this, colPoint, this.velocity);
        }
}
/**
 *  change the location of the ball.
 *  done separately from the MoveOneStep method following course instructions.
 * @param minX the leftmost point
 * @param maxX the rightmost point
 * @param minY the lowest point
 * @param maxY the highest point
 */
public void moveInsideRectangle(int minX, int maxX, int minY, int maxY) {
    double newX = center.getX() + velocity.getDx();
    double newY = center.getY() + velocity.getDy();
    //left or right
    if (newX - radius < minX) {
        velocity = new Velocity(-velocity.getDx(), velocity.getDy());
        newX = minX + radius;
    } else if (newX + radius > maxX) {
        velocity = new Velocity(-velocity.getDx(), velocity.getDy());
        newX = maxX - radius;
    }
    //top or bottom
    if (newY - radius < minY) {
        velocity = new Velocity(velocity.getDx(), -velocity.getDy());
        newY = minY + radius;
    } else if (newY + radius > maxY) {
        velocity = new Velocity(velocity.getDx(), -velocity.getDy());
        newY = maxY - radius;
    }
    //Update
    this.center = new Point(newX, newY);

}
    /**
     * move outdise gray frame.
     * @param screenWidth
     * @param screenHeight
     * @param recStart
     * @param length
     */
    public void moveOutsideGrayFrame(int screenWidth, int screenHeight, int recStart, int length) {
        double x = this.getX();
        double y = this.getY();
        double dx = this.getVelocity().getDx();
        double dy = this.getVelocity().getDy();
        int r = this.getSize();
        double newX = x + dx;
        double newY = y + dy;
        int rectEnd = recStart + length;
        double minVelocity = 1.0;
        // Bounce from screen edges
        if (newX - r < 0 || newX + r > screenWidth) {
            dx = -dx;
            if (Math.abs(dx) < minVelocity) {
                dx = dx > 0 ? minVelocity : -minVelocity;
            }
            newX = x + dx;
        }
        if (newY - r < 0 || newY + r > screenHeight) {
            dy = -dy;
            if (Math.abs(dy) < minVelocity) {
                dy = dy > 0 ? minVelocity : -minVelocity;
            }
            newY = y + dy;
        }
        //Check if the ball wil collide the gray rectangle
        boolean willCollideRight = (newX - r < rectEnd) && (x - r >= rectEnd)
                && (newY + r > recStart) && (newY - r < rectEnd);
        boolean willCollideLeft = (newX + r > recStart) && (x + r <= recStart)
                && (newY + r > recStart) && (newY - r < rectEnd);
        boolean willCollideTop = (newY + r > recStart) && (y + r <= recStart)
                && (newX + r > recStart) && (newX - r < rectEnd);
        boolean willCollideBottom = (newY - r < rectEnd) && (y - r >= rectEnd)
                && (newX + r > recStart) && (newX - r < rectEnd);
        if (willCollideRight) {
            dx = -dx;
            if (Math.abs(dx) < minVelocity) {
                dx = -minVelocity;
            }
            newX = rectEnd + r;
        } else if (willCollideLeft) {
            dx = -dx;
            if (Math.abs(dx) < minVelocity) {
                dx = minVelocity;
            }
            newX = recStart - r;
        } else if (willCollideTop) {
            dy = -dy;
            if (Math.abs(dy) < minVelocity) {
                dy = minVelocity;
            }
            newY = recStart - r;
        } else if (willCollideBottom) {
            dy = -dy;
            if (Math.abs(dy) < minVelocity) {
                dy = -minVelocity;
            }
            newY = rectEnd + r;
        }
        // Set the updated velocity
        this.setVelocity(dx, dy);
        // Move the ball using the corrected position
        this.center = new Point(newX, newY);
    }
    /**
     * if radius smaller than 0 or bigger than screen - change it to default radius size - 5 currently.
     * @param maxSize of possible radius
     */
    public void defaultRadius(int maxSize) {
        int defaultRadius = 5;
        if (this.radius <= 0 || this.radius * 2 >= maxSize) {
            this.radius = defaultRadius;
        }
    }
}