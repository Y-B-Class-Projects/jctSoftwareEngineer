package geometries;



import primitives.Point3D;

/**
 * Triangle class represent a Polygon with 3 points.
 */
public class Triangle extends Polygon{

    /**
     * Constructs and initialized a Triangle
     * @param points 3 Point3D objects
     */
    public Triangle(Point3D... points)
    {
        super(points);
    }

    /**
     * Representation the object by a string
     * @return string that represents the object.
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
