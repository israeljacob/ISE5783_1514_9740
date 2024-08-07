package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.isZero;

public class Ray {
    final private Point p0;
    final private Vector dir;

    /**
     * Constructor: Initializes a new instance of the Ray class.
     *
     * @param p0  A Point object representing the origin point of the ray.
     * @param dir A Vector object representing the direction of the ray.
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /**
     * Constructor for ray deflected by epsilon
     *
     * @param p0  origin
     * @param n   normal vector
     * @param dir direction
     */
    public Ray(Point p0, Vector n, Vector dir) {
        this.p0 = p0.add(n.scale(n.dotProduct(dir) > 0 ? 0.1 : -0.1));
        this.dir = dir.normalize();
    }

    /**
     * Getter: Gets the origin point of the ray.
     *
     * @return A Point object representing the origin point of the ray.
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Getter: Gets the direction vector of the ray.
     *
     * @return A Vector object representing the direction of the ray.
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * Override: Determines whether two Ray objects are equal.
     *
     * @param o The other Ray object to compare with.
     * @return True if the two Ray objects are equal, False otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return (o instanceof Ray other)
                && this.p0.equals(other.p0)
                && this.dir.equals(other.dir);
    }

    public Point getPoint(double t) {
        if (isZero(t))
            throw new IllegalArgumentException("t equal 0 cause illegal Vector ZERO");

        return p0.add(dir.scale(t));
    }

    /**
     * Given a list of points, find the closest point to the origin
     *
     * @param points a list of points
     * @return the closest point to the origin
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }


    /**
     * The function takes a list of GeoPoints and returns the closest GeoPoint to the origin
     *
     * @param points A list of GeoPoints to search through
     * @return The closest GeoPoint to the origin.
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> points) {
        if (points == null)
            return null;

        GeoPoint result = null;
        double closest = Double.MAX_VALUE;
        double ptDistance;

        // A for loop that goes through all the points in the list and finds the closest point to the origin.
        for (var pt : points) {
            ptDistance = pt.point.distance(this.p0);
            if (ptDistance < closest) {
                closest = ptDistance;
                result = pt;
            }
        }

        return result;
    }
}
