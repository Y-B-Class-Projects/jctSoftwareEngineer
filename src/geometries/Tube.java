package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Tube class represent a Tube in the space by radius and ray - לברר מה זה, זה דומה לגליל אבל זה לא
 */
public class Tube extends RadialGeometry {

    /** Ray for the Tube */
    Ray _exisRay;

    /**
     * Constructs and initialized a Tube by radius and ray
     * @param _exisRay Ray
     * @param _radius radius
     */
    public Tube(double _radius , Ray _exisRay) {
        super(_radius);
        this._exisRay = _exisRay;
    }

    /**
     * Constructs and initialized a Tube by color radius and ray
     * @param color
     * @param _radius
     * @param _exisRay
     */
    public Tube(Color color,double _radius , Ray _exisRay){
        this(_radius , _exisRay);
        _emission = color;
    }

    /**
     * Constructs and initialized a Tube by color radius, ray and material
     * @param color
     * @param _radius
     * @param _exisRay
     */
    public Tube(Color color, Material material, double _radius , Ray _exisRay){
        this(_radius , _exisRay);
        _emission = color;
        _material = material;
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
        if (t!=0) {
            Point3D O = new Point3D(p0.add((V.scale(t)))); // O is projection of P on cylinder's ray

            return (P.subtruct(O)).normalize(); // with O and P we can calculate the normal same as Tube.
        }
        return (P.subtruct(p0)).normalize();

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

    /**
     * checks is given point is on tube
     * @param p1
     * @return
     */
    public boolean is_on_tube(Point3D p1) {
		/*if() {
			return true;
		}
		return false;*/
        return false;
    }


    @Override
    public List<GeoPoint> findIntsersections(Ray r) {
        ArrayList<GeoPoint> intersections = new ArrayList<>();
        ArrayList<Point3D> arrPoints = new ArrayList<>();

        Vector deltaP = r.get_p0().subtruct(this._exisRay.get_p0());
        double dotProduct1 = r.get_dir().dotProduct(this._exisRay.get_dir());
        double dotProduct2 = deltaP.dotProduct(this._exisRay.get_dir());
        Vector temp1 = null;
        Vector temp2 = null;

        if (!Util.isZero(dotProduct1)) temp1 = r.get_dir().subtruct(this._exisRay.get_dir().scale(dotProduct1));
        if (!Util.isZero(dotProduct2)) temp2 = deltaP.subtruct(this._exisRay.get_dir().scale(dotProduct2));

        if(temp1 != null && temp2!= null) {

        double A = temp1.dotProduct(temp1);
        double B = 2 * (temp1.dotProduct(temp2));
        double C = temp2.dotProduct(temp2) - this._radius * this._radius;
        double discriminant = B*B - 4*A*C;


            if (Util.isZero(discriminant))
                discriminant = 0;
            if (discriminant < 0)
                return intersections;

            double discriminantRoot = Math.sqrt(discriminant);
            double t1 = (-B + discriminantRoot) / (2 * A);
            double t2 = (-B - discriminantRoot) / (2 * A);

            if (t1 > 0 && !Util.isZero(t1))
                arrPoints.add(r.get_p0().add(r.get_dir().scale(t1)));
            if (t2 > 0 && discriminant != 0 && !Util.isZero(t2))
                arrPoints.add(r.get_p0().add(r.get_dir().scale(t2)));

            if (arrPoints.size() != 0) {
                ArrayList<GeoPoint> geoPoints = new ArrayList<>();

                for (Point3D arrPoint : arrPoints) {
                    geoPoints.add(new GeoPoint(this, arrPoint));
                }

                intersections.addAll(geoPoints);
            }
            return intersections;
        }
        return null;
    }

}
