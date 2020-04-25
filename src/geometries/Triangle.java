package geometries;


import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

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

    @Override
    public List<Point3D> findIntsersections(Ray ray) {

        Plane plane = new Plane(_vertices.get(0), _vertices.get(1), _vertices.get(2));

        if(plane.findIntsersections(ray) == null)
            return null;

        Point3D p = plane.findIntsersections(ray).get(0); //if there is a intsersection between the ray and the plane, this is the only result

        Point3D P0 = ray.get_p0();

        Vector v1 = _vertices.get(0).subtruct(P0);
        Vector v2 = _vertices.get(1).subtruct(P0);
        Vector v3 = _vertices.get(2).subtruct(P0);

        Vector N1 = v1.crossProduct(v2).normalize();
        Vector N2 = v2.crossProduct(v3).normalize();
        Vector N3 = v3.crossProduct(v1).normalize();

        Vector v = ray.get_dir();

        if(v.dotProduct(N1) > 0 && v.dotProduct(N2) > 0 && v.dotProduct(N3) > 0
            ||v.dotProduct(N1) < 0 && v.dotProduct(N2) < 0 && v.dotProduct(N3) < 0){

            List<Point3D> result = new ArrayList<>();
            result.add(p);
            return result;
        }
        return null;
    }
}
