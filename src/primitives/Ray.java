package primitives;

import java.util.Objects;

import static java.lang.System.out;
import static primitives.Util.isZero;

/**
 * Class Ray, basic object in geometry - group of point's that represent a ray.
 */

public class Ray {

    /** point */
    private Point3D _p0;
    /** direction */
    private Vector _dir;
    /**  */
    private static final double DELTA = 0.1;

    /*******  constructor's  **********/

    /**
     * Constructs and initialized a Ray object.
     * @param _p0 point
     * @param _dir direction
     */
    public Ray(Point3D _p0, Vector _dir) {
        if (_dir.length() != 1)
            _dir.normalize();
        this._p0 = _p0;
        this._dir = _dir;
    }

    /**
     * Copy constructor
     * @param ray Ray to copy to the object.
     */
    public Ray(Ray ray) {
        this(ray._p0 , ray._dir);
    }

    /***
     * constructor that used also for moving the point out of the object, the point
     * will be exactly at the intersection with the object.
     * @param head point inside the object
     * @param direction direction of the ray
     * @param n vector normal to the direction and to the object.
     */
    public Ray(Point3D head, Vector direction, Vector n){
        _dir = direction.normalized();

        double nv = n.dotProduct(direction);

        Vector normalDelta = n.scale((nv > 0 ? DELTA : -DELTA));
        _p0 = head.add(normalDelta);
    }

    /********* getter's  *********/

    /**
     * point-value getter
     * @return coordinate-x value
     */
    public Point3D get_p0() {
        return _p0;
    }

    /**
     * direction-value getter
     * @return direction value
     */
    public Vector get_dir() {
        return _dir;
    }

    /***
     *function to evaluate the size of the Ray, P = p0 + t*v
     * @param t scalar
     * @return size of the Ray
     */
    public Point3D getPoint(double t) {
        return new Point3D(this._p0).add(this._dir.scale(t));
    }
    /******** ToString **********/

    /**
     * Representation the object by a string
     * @return string that represents the object.
     */
    @Override
    public String toString() {
        return "(" +
                "_p0=" + _p0 +
                "_dir" + _dir +
                ')';
    }

    /********  equals  **********/
    /**
     * A function to compare two Ray objects.
     * @param o object for comparison
     * @return true if the object is equal to o, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Ray)) return false;
        Ray ray = (Ray) o;
        return this._p0.equals(ray._p0) && this._dir.equals(ray._dir);
    }
}
