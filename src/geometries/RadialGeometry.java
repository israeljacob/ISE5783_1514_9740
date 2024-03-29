package geometries;

public abstract class RadialGeometry extends geometries.Geometry {
    final protected double radius;

    /**
     * Constructor: Initializes a new instance of the RadialGeometry class.
     *
     * @param radius The radius of the shape.
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }
}
