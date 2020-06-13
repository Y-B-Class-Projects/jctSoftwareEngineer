package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;
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
     * Constructs and initialized a Sphere by color, center point and radius
     * @param color
     * @param _center
     * @param _radius
     */
    public Sphere(Color color,Point3D _center, double _radius){
        this(_center,_radius);
        _emission = color;
    }

    /**
     * Constructs and initialized a Sphere by color, center point, radius and material
     * @param color
     * @param material
     * @param _center
     * @param _radius
     */
    public Sphere(Color color, Material material, Point3D _center, double _radius){
        this(_center,_radius);
        _emission = color;
        _material = material;
    }
    public Sphere(Color color, Material material, double _radius, Point3D _center){
        this(color,material,_center,_radius);
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


    /**
     * find intsersection of the sphere and the ray
     * @param ray for the intsersection
     * @return the intsersection
     */
    @Override
    public List<GeoPoint> findIntsersections(Ray ray) {
        Point3D P0 = ray.get_p0();
        Vector v = ray.get_dir();

        Vector u = _center.subtruct(P0); //vector from P0 to O
        double tm = v.dotProduct(u); // length on vector v from P0 to O (proj-u on v).
        double d = Math.sqrt(u.lengthSquared() - tm * tm); // Distance between point O and continuation of vector v

        if(d >= _radius) //if the continuation of vector v inside the Sphere
            return null;
        else {
            double th = Math.sqrt(_radius * _radius - d * d);

            List<GeoPoint> result = new ArrayList<>();

            double t1 = alignZero(tm + th);
            double t2 = alignZero(tm - th);

            if(t1 <= 0 && t2 <= 0)
                return  null;

            if (t1 > 0) {
                result.add(new GeoPoint(this, ray.getPoint(t1)));
            }

            if (t1 != t2 && t2 > 0) {
                result.add(new GeoPoint(this, ray.getPoint(t2)));
            }
            return result;
        }
    }
}
