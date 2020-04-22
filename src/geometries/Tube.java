package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Tube class represent a Tube in the space by radius and ray
 */
public class Tube extends RadialGeometry {

    /** Ray for the Tube */
    Ray _exisRay;

    /**
     * Constructs and initialized a Tube
     * @param _exisRay Ray
     * @param _radius radius
     */
    public Tube(double _radius , Ray _exisRay) {
        super(_radius);
        this._exisRay = _exisRay;
    }

    /**
     * normal getter
     * @param P 3D point
     * @return Vector normal to the Tube
     */
    @Override
    public Vector getNormal(Point3D P) {

        Vector V = this._exisRay.get_dir();
        Point3D p0 = this._exisRay.get_p0();

        double t = V.dotProduct(new Vector(P.subtruct(p0))); // t is the distance between O and p0
        Point3D O = new Point3D(p0.add((V.scale(t)))); // O is projection of P on cylinder's ray

        return (P.subtruct(O)).normalize(); // with O and P we can calculate the normal same as Tube.

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
     * exisRay value getter
     * @return exisRay value
     */
    public Ray get_exisRay() {
        return new Ray(_exisRay);
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
