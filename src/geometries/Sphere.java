package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;

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

    @Override
    public List<Point3D> findIntsersections(Ray ray) {
        Point3D P0 = ray.get_p0();
        Vector v = ray.get_dir();

        //Vector P = P0.add(V.scale())

        Vector u = _center.subtruct(P0); //vector from P0 to O
        double tm = v.dotProduct(u); // length on vector v from P0 to O (proj-u on v).
        double d = Math.sqrt(u.lengthSquared() - tm * tm); // Distance between point O and continuation of vector v

        if(d >= _radius) //if the continuation of vector v inside the Sphere
            return null;
        else {
            double th = Math.sqrt(_radius * _radius - d * d); //

            List<Point3D> result = new ArrayList<>();

            double t1 = alignZero(tm + th);
            double t2 = alignZero(tm - th);

            if(t1 < 0 && t2 < 0)
                return  null;

            if (t1 > 0) {

                result.add(P0.add(v.scale(t1)));
            }
            if (t1 != t2) {
                if (t2 > 0) {
                    result.add(P0.add(v.scale(t2)));
                }
            }
            return result;
        }
    }
}
