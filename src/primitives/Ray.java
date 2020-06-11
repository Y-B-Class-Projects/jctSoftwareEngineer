package primitives;

import java.util.Objects;

/**
 * Class Ray, basic object in geometry - group of point's that represent a ray.
 */

public class Ray {

    /** point */
    private Point3D _p0;
    /** direction */
    private Vector _dir;
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
        _p0 = ray.get_p0();
        _dir = ray.get_dir();
    }

    /***
     * constructor for initialize Ray fields consider the DELTA ******************
     * @param head
     * @param direction
     * @param n
     */
    public Ray(Point3D head, Vector direction, Vector n){
        this(head.add(n.scale((n.dotProduct(direction)) > 0 ? DELTA : - DELTA)),direction);
    }

    /********* getter's  *********/

    /**
     * point-value getter
     * @return coordinate-x value
     */
    public Point3D get_p0() {
        return new Point3D(_p0);
    }

    /**
     * direction-value getter
     * @return direction value
     */
    public Vector get_dir() {
        return new Vector(_dir);
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
