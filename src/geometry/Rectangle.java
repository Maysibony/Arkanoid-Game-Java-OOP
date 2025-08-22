package geometry;
import biuoop.DrawSurface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 Rectangle class.
 */
public class Rectangle {
    private Point upperLeft;
    private double width, height;

    /**
     * Rectangle constructor.
     * @param upperLeft point of the rectangle
     * @param width
     * @param height
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Return a (possibly empty) List of intersection points with the specified line.
     * @param line to check intersections with
     * @return list of intersections points
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        List<Point> intersectionPoints = new ArrayList<Point>();
        Point upperRight = new Point(upperLeft.getX() + width, upperLeft.getY());
        Point lowerLeft = new Point(upperLeft.getX(), upperLeft.getY() + height);
        Point lowerRight = new Point(upperLeft.getX() + width, upperLeft.getY() + height);
        Line top = new Line(upperLeft, upperRight);
        Line bottom = new Line(lowerLeft, lowerRight);
        Line left = new Line(upperLeft, lowerLeft);
        Line right = new Line(upperRight, lowerRight);
        Line[] sides = {top, bottom, left, right};
        for (Line side : sides) {
            Point intersection = line.intersectionWith(side);
            if (intersection != null) {
                intersectionPoints.add(intersection);
            }
        }
        return intersectionPoints;
    }
    /**
     * @return the vertices points of the rectangle from top to bottom.
     */
    public Point[] getVertices() {
        Point topRight = new Point(upperLeft.getX() + width, upperLeft.getY());
        Point bottomRight = new Point(upperLeft.getX() + width, upperLeft.getY() + height);
        Point bottomLeft = new Point(upperLeft.getX(), upperLeft.getY() + height);
        return new Point[]{upperLeft, topRight, bottomRight, bottomLeft};
    }

    /**
     * get Width of rectangle.
     * @return width
     */
    public double getWidth() {
        return width;
    }
    /**
     * get height of rectangle.
     * @return height
     */
    public double getHeight() {
        return height;
    }
    /**
     * get upper left point of rectangle.
     * @return upper left point
     */
    public Point getUpperLeft() {
        return upperLeft;
    }
    /**
     * set upper left point of rectangle.
     * @param upperLeft
     */
    public void setUpperLeft(Point upperLeft) {
        this.upperLeft = upperLeft;
    }
    /**
     * Draw a rectangle.
     * @param surface
     * @param color
     */
    public void drawOn(DrawSurface surface, Color color) {
        surface.setColor(color);
        surface.fillRectangle((int) upperLeft.getX(), (int) upperLeft.getY(), (int) width, (int) height);
    }
    /**
     * move the block horizontal.
     * @param dx
     */
    public void moveHorizontall(double dx) {
        Point upperLeft = this.getUpperLeft();
        this.upperLeft = new Point(upperLeft.getX() + dx, upperLeft.getY());
    }
}
