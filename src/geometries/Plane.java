package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Plane class represent a plane in the space by 3D-Point and normal Vector
 */
public class Plane implements geometry {

    /** point*/
    Point3D _p;
    /** normal vector to the plane */
    Vector _normal;

    /**
     * Constructs and initialized a Plane by 3 points.
     * @param p1 point
     * @param p2 point for normal
     * @param p3 point for normal
     */
    public Plane(Point3D p1 , Point3D p2 , Point3D p3)
    {
        _p = p1;
        Vector v1 = p2.subtruct(p1);
        Vector v2 = p3.subtruct(p1);
        _normal = (v1.crossProduct(v2)).normalize();
    }

    /**
     * Constructs and initialized a Plane by point and normal vector.
     * @param point3D point
     * @param _normal normal vector
     */
    public Plane(Point3D point3D , Vector _normal)
    {
        _p = new Point3D(point3D);
        this._normal = new Vector(_normal);
    }

    /**
     * normal getter
     * @param point3D 3D point
     * @return Vector normal to the plane
     */
    @Override
    public Vector getNormal(Point3D point3D) {
        return _normal;
    }

    /**
     * normal getter
     * @return Vector normal to the plane
     */
    public Vector getNormal() {
        return _normal;
    }

    /**
     * point value getter
     * @return point value
     */
    public Point3D get_p() {
        return new Point3D(_p);
    }

    /**
     * Representation the object by a string
     * @return string that represents the object.
     */
    @Override
    public String toString() {
        return "Plane{" +
                "_p=" + _p +
                ", _normal=" + _normal +
                '}';
    }

    @Override
    public List<Point3D> findIntsersections(Ray ray) {
        return null;
    }
}
