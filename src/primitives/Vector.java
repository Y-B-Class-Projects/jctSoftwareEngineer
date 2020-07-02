package primitives;

import java.util.Objects;

/**
 * Vector class represent a vector in the space, the vector contain Head point - the end of the vector.
 * end the beginning of the vector is the point (0,0,0) "RESHIT AHTZIRIM".
 */
public class Vector {

    /** head of the vector object */
    private Point3D _head;

/*********  constructor's ***********/

    /**
     * Constructs and initialized a Vector.
     * @param _head head of the vector.
     */
    public Vector(Point3D _head) {
        if (_head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("ZERO VECTOR NOT ALLOWED");
        this._head = _head;
    }

    /**
     * Copy constructor
     * @param vector Vector to copy to the object.
     */
    public Vector(Vector vector) {
        this._head = vector._head;
    }

    /**
     * Constructs and initialized a Vector.
     * @param x coordinates in the x axis
     * @param y coordinates in the y axis
     * @param z coordinates in the z axis
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        this(new Point3D(x,y,z));
    }

    /**
     * Constructs and initialized a Vector.
     * @param x coordinates in the x axis
     * @param y coordinates in the y axis
     * @param z coordinates in the z axis
     */
    public Vector(double x, double y, double z) {
        this(new Point3D(x,y,z));
    }

/******** getter **********/

    /**
     * head-value getter
     * @return head-value value
     */
    public Point3D get_head() {
        return _head ;
    }

/******   ToString   **********/

    /**
     * Representation the object by a string
     * @return string that represents the object.
     */
    @Override
    public String toString() {
        return "(" +
                "_head=" + _head +
                ')';
    }

/************ equals *************/

    /**
     * A function to compare two Vector objects.
     * @param o object for comparison
     * @return true if the object is equal to o, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Vector)) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }

/************ functions  *****************/

    /**
     * Addition two Vectors objects
     * @param vec second vector
     * @return result of the Addition
     */
    public Vector add(Vector vec) {
        return new Vector(_head._x._coord + vec._head._x._coord,
                          _head._y._coord + vec._head._y._coord,
                       _head._z._coord + vec._head._z._coord);
    }

    /**
     * Subtraction two Vectors objects
     * @param vec second vector
     * @return result of the subtraction
     */
    public Vector subtruct(Vector vec) {
        return new Vector(_head._x._coord - vec._head._x._coord,
                _head._y._coord - vec._head._y._coord,
                _head._z._coord - vec._head._z._coord);
    }

    /**
     * Scalar-multiplication for Vector
     * @param scalar scalar to multiplication
     * @return result of the scalar multiplication
     */
    public Vector scale(double scalar) {
        Point3D point3D= new Point3D(_head._x._coord * scalar,
                _head._y._coord * scalar,
                _head._z._coord * scalar);
        return new Vector(point3D);
    }

    /**
     * Dot-Product for two Vector objects.
     * @param vec second vector.
     * @return result of the Dot-Product.
     */
    public double dotProduct(Vector vec) {
        return _head._x._coord * vec._head._x._coord +
                _head._y._coord * vec._head._y._coord +
                _head._z._coord * vec._head._z._coord;
    }

    /**
     * Cross-Product for two Vector objects.
     * @param vec second vector.
     * @return result of the cross-product.
     */
    public Vector crossProduct(Vector vec) {
        return new Vector(_head._y._coord * vec._head._z._coord -_head._z._coord * vec._head._y._coord,
                          _head._z._coord * vec._head._x._coord -_head._x._coord * vec._head._z._coord,
                          _head._x._coord * vec._head._y._coord -_head._y._coord * vec._head._x._coord);
    }

    /**
     * Square length of a Vector.
     * @return Square length of the object.
     */
    public double lengthSquared() {
        return this.dotProduct(this);
    }

    /**
     * length of a Vector.
     * @return length of the object.
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * Vector normalization.
     * @return Normalize of the object.
     */
    public Vector normalize() {
        double length = length();     // this.length()
        this._head = new Point3D(_head._x._coord / length,
                _head._y._coord  / length,
                _head._z._coord  / length);
        return this;
    }

    /**
     * Get the normal value of a vector
     * @return new object equal to the normal of the object.
     */
    public Vector normalized()
    {
        Vector temp = new Vector(this);
        return temp.normalize();
    }
}
