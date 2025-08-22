package game;

import geometry.Point;

/**
 * Velocity specifies the change in position on the `x` and the `y` axes.
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * constructor Velocity object.
     * @param dx the change in the x-coordinate.
     * @param dy the change in the y-coordinate.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }
    /**
     * return velocity basaed on angle and speed.
     * @param angle of ball
     * @param speed of ball
     * @return new velocity
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double radians = Math.toRadians(angle);
        double dx = speed * Math.sin(radians);
        double dy = speed * Math.cos(radians);
        return new Velocity(dx, dy);
    }
    /**
     * Take a point with position (x,y) and return a new point.
     * with position (x+dx, y+dy).
     * @param p the point to change
     * @return the updated point.
     */
    public Point applyToPoint(Point p) {
        double moveX = p.getX() + this.dx;
        double moveY = p.getY() + this.dy;
        return new Point(moveX, moveY);
    }
    /**
     * get Dx.
     * @return dx
     */
    public double getDx() {
        return this.dx;
    }
    /**
     * get Dy.
     * @return dy
     */
    public double getDy() {
        return this.dy;
    }
}