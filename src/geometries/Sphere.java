package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Sphere class represent a Sphere in the space by center point and radius
 */
public class Sphere extends RadialGeometry {

    /** the center of the sphere */
    protected Point3D _center;

    /**
     * Constructs and initialized a Sphere
     * @param _center center of the Sphere
     * @param _radius radius of the Sphere
     */
    public Sphere(Point3D _center, double _radius) {
        super(_radius);
        this._center = _center;
    }

    /**
     * radius value getter
     * @return radius value
     */
    @Override
    public double get_radius() {
        return super.get_radius();
    }

    /**
     * center value getter
     * @return center value
     */
    public Point3D get_center() {
        return _center;
    }


    /**
     * normal getter
     * @param P 3D point
     * @return Vector normal to the Sphere
     */
    @Override
    public Vector getNormal(Point3D P) {
        return (P.subtruct(_center)).normalize();
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
