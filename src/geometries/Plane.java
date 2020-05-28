package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import primitives.Material;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Plane class represent a plane in the space by 3D-Point and normal Vector
 */
public class Plane extends Geometry {

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
     * Constructs and initialized a Plane by 3 points and color
     * @param color plane color
     * @param p1 point
     * @param p2 point for normal
     * @param p3 point for normal
     */
    public Plane(Color color, Point3D p1 , Point3D p2 , Point3D p3){
        this(p1 , p2 ,p3);
        this._emission = color;
    }

    /**
     * Constructs and initialized a Plane by point, normal vector and color.
     * @param color plane color
     * @param material
     * @param point3D point
     * @param _normal normal
     */
    public Plane(Color color,Material material, Point3D point3D , Vector _normal){
        this(point3D , _normal);
        this._emission = color;
        this._material = material;
    }

    /**
     * Constructs and initialized a Plane by 3 points, color and material
     * @param color plane color
     * @param material
     * @param p1 point
     * @param p2 point for normal
     * @param p3 point for normal
     */
    public Plane(Color color ,Material material, Point3D p1 , Point3D p2 , Point3D p3){
        this(p1 , p2 ,p3);
        this._emission = color;
        this._material = material;
    }

    /**
     * Constructs and initialized a Plane by point, normal vector, color and material
     * @param color plane color
     * @param point3D point
     * @param _normal normal
     */
    public Plane(Color color, Point3D point3D , Vector _normal){
        this(point3D , _normal);
        this._emission = color;
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
                ", color=" + _emission +
                '}';
    }

    /**
     * find intsersection of the plane and the ray
     * @param ray for the intsersection
     * @return the intsersection
     */
    @Override
    public List<GeoPoint> findIntsersections(Ray ray) {

        //p = P0 + tv , t >= 0 | Ray points.
        //t = [N*(Q0 - P0)]/[N*v] | N - plane normal, Q0 - plane point, P0 - ray point, v - ray direction.

        double nv = _normal.dotProduct(ray.get_dir());

        if(isZero(nv))
            return null;

        if (_p.equals(ray.get_p0())) // if the ray start on the plane and on P0 point;
            return null;

        double N_Q0_Minus_P0 = _normal.dotProduct(_p.subtruct(ray.get_p0()));

        if (_p.equals(ray.get_p0())) // if the ray start on the plane;
            return null;

        double t  = alignZero(N_Q0_Minus_P0/nv);

        if(t <= 0)
            return null;

        List<GeoPoint> result = new ArrayList<>();

        result.add(new GeoPoint(this,ray.getPoint(t)));

        return result;
    }
}
