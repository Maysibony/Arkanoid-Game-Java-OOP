package geometry;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Represents a point in a 2D coordinate system.
 * This class provides methods for manipulating points and calculating distances.
 * A point has an x and a y value
 */
public class Point {
    //fields
    private double x;
    private double y;
    /**
     * Constructs a Point object with the specified x and y coordinates.
     * @param x the x-coordinate of the point.
     * @param y the y-coordinate of the point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Calculates the distance between this point and another point.
     * @param other the other point to calculate the distance to.
     * @return the distance between the two points.
     */
    public double distance(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    /**
     * Checks if this point is equal to another point.
     * @param other the other point to compare with.
     * @return true if the points are equal, false otherwise.
     */
    public boolean equals(Point other) {
        return CommonUtils.isdoubleEqual(this.x, other.x) && CommonUtils.isdoubleEqual(this.y, other.y);
    }

    /**
     * Returns the x-coordinate of the point.
     * @return the x-coordinate.
     */
    public double getX() {
        return this.x;
    }
    /**
     * Returns the y-coordinate of the point.
     * @return the y-coordinate.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Draws the point on a specified drawing surface.
     * @param d the drawing surface to draw on.
     * @param color the color to use for the point.
     */
    public void drawPoint(DrawSurface d, Color color) {
        d.setColor(color);
        d.fillCircle((int) x, (int) y, 3); // Draw a small circle to represent the point
    }
}
