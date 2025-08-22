package geometry;
import biuoop.DrawSurface;
import java.awt.Color;
import java.util.List;

/**
 * Represents a line segment defined by two points.
 */
public class Line {
    //fields
    private Point start;
    private Point end;
    private double yIntercept;
    private double slope;
    private boolean isHorizontal;
    private boolean isVertical;

    /**
     * Constructs a Line object using two points.
     * @param start the starting point of the line.
     * @param end the ending point of the line.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
        this.isHorizontal = CommonUtils.isdoubleEqual(start.getY(), end.getY());
        this.isVertical = CommonUtils.isdoubleEqual(start.getX(), end.getX());
        this.slope = isVertical ? 0.0 : (end.getY() - start.getY()) / (end.getX() - start.getX());
        this.yIntercept = isVertical ? 0 : start.getY() - slope * start.getX();
    }
    /**
     * Constructs a Line object using four coordinates.
     * @param x1 the x-coordinate of the starting point.
     * @param y1 the y-coordinate of the starting point.
     * @param x2 the x-coordinate of the ending point.
     * @param y2 the y-coordinate of the ending point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
        this.isHorizontal = CommonUtils.isdoubleEqual(y1, y2);
        this.isVertical = CommonUtils.isdoubleEqual(x1, x2);
        this.slope = isVertical ? 0.0 : (end.getY() - start.getY()) / (end.getX() - start.getX());
        this.yIntercept = isVertical ? 0 : start.getY() - slope * start.getX();
    }

    /**
     * Calculates the length of the line.
     * @return the length of the line.
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * Calculates the middle point of the line.
     * @return the middle point of the line.
     */
    public Point middle() {
        double midX = (start.getX() + end.getX()) / 2;
        double midY = (start.getY() + end.getY()) / 2;
        return new Point(midX, midY);
    }

    /**
     * Returns the starting point of the line.
     * @return the starting point.
     */
    public Point start() {
         return this.start;
    }

    /**
     * Returns the end point of the line.
     * @return the ending point.
     */
    public Point end() {
        return this.end;
    }

    /**
     * Checks if this line intersects with another line.
     * @param other the other line to check for intersection.
     * @return true if the lines intersect, false otherwise.
     */
    public boolean isIntersecting(Line other) {
        if (this.equals(other) || this.isLineOnLine(other)) {
            return true;
        }
        Point intersection = intersectionWith(other);
        return intersection != null && isPointOnLine(intersection) && other.isPointOnLine(intersection);
    }

    /**
     * Checks if this line intersects with two other lines.
     * @param other1 the first other line.
     * @param other2 the second other line.
     * @return true if the lines intersect, false otherwise.
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return this.isIntersecting(other1) && this.isIntersecting((other2));
    }

    /**
     * Finds the intersection point between this line and another line.
     * @param other the other line to check for intersection.
     * @return the intersection point if the lines intersect, null otherwise.
     */
    public Point intersectionWith(Line other) {
        if (this.equals(other)) {
            return null;
        }
        if ((this.isHorizontal && other.isHorizontal)
                && !CommonUtils.isdoubleEqual(this.start.getY(), other.start.getY())
                || (this.isVertical && other.isVertical)
                && !CommonUtils.isdoubleEqual(this.start.getX(), other.start.getX())) {
            return null;
        }

        //found intersection point
        double x, y;
        if (this.isVertical && !other.isVertical) {
            x = this.start.getX();
            y = other.slope * x + other.yIntercept;
        } else if (other.isVertical && !this.isVertical) {
            x = other.start.getX();
            y = this.slope * x + this.yIntercept;
        } else {
            double m1 = this.slope;
            double b1 = this.yIntercept;
            double m2 = other.slope;
            double b2 = other.yIntercept;
            //The lines are parallel:
            if (CommonUtils.isdoubleEqual(m1, m2)) {
                //they don't have the same b - they will never meet
                if (!CommonUtils.isdoubleEqual(b1, b2)) {
                    return null;
                }
                //they have infinity shared points
                if (this.isLineOnLine(other)) {
                    return null;
                }
                //they have one shard point
                if (this.start.equals(other.start) || this.start.equals(other.end)) {
                    return new Point(this.start.getX(), this.start.getY());
                }
                if (this.end.equals(other.start) || this.end.equals(other.end)) {
                    return new Point(this.end.getX(), this.end.getY());
                }
            }
            //The lines aren't parallel:
            x = (b2 - b1) / (m1 - m2);
            y = m1 * x + b1;
        }
        //we found a potential shared point - now check if it's on both lines:
        Point intersection = new Point(x, y);
        if (isPointOnLine(intersection) && other.isPointOnLine(intersection)) {
            return intersection;
        }
        return null;
    }

    /** isLineOnLine -- return true if the lines have shared parts.
     * @param other the other line to check.
     * @return true if there is euqual parts
     */
    public boolean isLineOnLine(Line other) {

        if (this.equals(other)) {
            return true;
        }
        if (this.isVertical && other.isVertical) {
            double minY1 = Math.min(this.start.getY(), this.end.getY());
            double maxY1 = Math.max(this.start.getY(), this.end.getY());
            double minY2 = Math.min(other.start.getY(), other.end.getY());
            double maxY2 = Math.max(other.start.getY(), other.end.getY());
            return maxY1 > minY2 && maxY2 > minY1;
        } else if (CommonUtils.isdoubleEqual(other.slope, this.slope)
                && CommonUtils.isdoubleEqual(other.yIntercept, this.yIntercept)) {
            double minX1 = Math.min(this.start.getX(), this.end.getX());
            double maxX1 = Math.max(this.start.getX(), this.end.getX());
            double minX2 = Math.min(other.start.getX(), other.end.getX());
            double maxX2 = Math.max(other.start.getX(), other.end.getX());
            return maxX1 > minX2 && maxX2 > minX1;
        }
        return false;
    }

    /**
     * Checks if a point is on the line.
     * @param point the point to check.
     * @return true if the point lies on the line, false otherwise.
     */
    public boolean isPointOnLine(Point point) {
        double length = this.start.distance(this.end);
        double distance = this.start.distance(point) + this.end.distance(point);
        return CommonUtils.isdoubleEqual(length, distance);
    }

    /**
     * Checks if two lines are equal.
     * @param other the other line to compare.
     * @return true if the lines are equal, false otherwise.
     */
    public boolean equals(Line other) {
        return this.start.equals(other.start) && this.end.equals(other.end)
                || (this.start.equals(other.end) && this.end.equals(other.start));
    }

    /**
     * Draws the line on a specified drawing surface.
     * @param d the drawing surface to draw on.
     * @param color the color to use for the line.
     */
    public void drawLine(DrawSurface d, Color color) {
        d.setColor(color);
        d.drawLine((int) start.getX(), (int) start.getY(),
                (int) end.getX(), (int) end.getY());
    }

    /**
     * Draws the intersection points between this line and another line on a specified drawing surface.
     * @param d the drawing surface to draw on.
     * @param other the other line to check for intersections.
     */
    public void drawIntersections(DrawSurface d, Line other) {
        if (this.isIntersecting(other)) {
            Point intersection = this.intersectionWith(other);
            if (intersection != null) {
                intersection.drawPoint(d, Color.RED);
            }
        }
    }
    /**
     *  If this line does not intersect with the rectangle, return null.
     *  Otherwise, return the closest intersection point to the start of the line.
     * @param rect the rectangle
     * @return closet intersection point to start of line.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> intersectionPoints = rect.intersectionPoints(this);
        if (intersectionPoints.isEmpty()) {
            return null;
        }
        double minDistance = this.start.distance(intersectionPoints.get(0));
        Point closet = intersectionPoints.get(0);
        for (int i = 1; i < intersectionPoints.size(); i++) {
            double distance = this.start.distance(intersectionPoints.get(i));
            if (minDistance > distance) {
                minDistance = distance;
                closet = intersectionPoints.get(i);
            }
        }
        return closet;
    }
}