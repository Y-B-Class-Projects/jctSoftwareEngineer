package primitives;


import java.util.Objects;

/**
 * Point 3D: class for representing a 3D point in the cartesian coordinate
 */

public class Point3D {

    /** coordinates in the x axis */
    private Coordinate _x;
    /** coordinates in the y axis */
    private Coordinate _y;
    /** coordinates in the z axis */
    private Coordinate _z;

    /** zero coordinates (0,0,0) */
    public static final Point3D ZERO = new Point3D(new Coordinate(0.0), new Coordinate(0.0), new Coordinate(0.0));

    /************* constructor ****************/

    /**
     * Constructs and initialized a Point3D on position (x,y,z)
     * @param _x coordinates in the x axis
     * @param _Y coordinates in the y axis
     * @param _z  coordinates in the z axis
     */
    public Point3D(Coordinate _x, Coordinate _Y, Coordinate _z) {
        this._x = _x;
        this._y = _Y;
        this._z = _z;
    }

    /**
     * Constructs and initialized a Point3D on position (x,y,z)
     * @param x coordinates in the x axis
     * @param y coordinates in the y axis
     * @param z coordinates in the z axis
     */
    public Point3D(double x, double y, double z) {
        this._x = new Coordinate(x);
        this._y = new Coordinate(y);
        this._z = new Coordinate(z);
    }

    /**
     * Copy constructor
     * @param p Point to copy to the object.
     */
    public Point3D(Point3D p) {
        this._x = p.get_x();
        this._y = p.get_Y();
        this._z = p.get_z();
    }


    /*************** getter's ******************/


    /**
     * coordinate-x value getter
     * @return coordinate-x value
     */
    public Coordinate get_x() {
        return new Coordinate(_x);
    }

    /**
     * coordinate-y value getter
     *
     * @return coordinate-y value
     */
    public Coordinate get_Y() {
        return new Coordinate(_y);
    }

    /**
     * coordinate-z value getter
     *
     * @return coordinate-z value
     */
    public Coordinate get_z() {
        return new Coordinate(_z);
    }

    /******************  equals() and ToString() *********************/

    /**
     * A function to compare two Point3D objects.
     * @param o object for comparison
     * @return true if the object is equal to o, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Point3D)) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) &&
                _y.equals(point3D._y) &&
                _z.equals(point3D._z);
    }

    /**
     * Representation the object by a string
     * @return string that represents the object.
     */
    @Override
    public String toString() {
        return "(" +
                "x=" + _x +
                ", Y=" + _y +
                ", z=" + _z +
                ')';
    }

    /***** function's *******/
    /**
     * Subtraction two Point3D
     * @param p2 point from space
     * @return vector that contain the result of subtract the object with p2.
     */
    public Vector subtruct(Point3D p2){
        return new Vector(new Point3D(this._x.get_coord() - p2._x.get_coord(), this._y.get_coord() - p2._y.get_coord(), this._z.get_coord() - p2._z.get_coord()));
    }

    /**
     * Addition vector and Point3D.
     * @param vec vector to the Addition
     * @return result of the Addition between vector and the object.
     */
    public Point3D add(Vector vec){
        return new Point3D(this._x.get_coord() + vec.get_head().get_x().get_coord(),
                this._y.get_coord() + vec.get_head().get_Y().get_coord(),
                this._z.get_coord() + vec.get_head().get_z().get_coord());
    }


    /**
     * Square distance between two Point3D objects.
     * @param point3D second Point3D object.
     * @return Square Distance between the object and the received point3D
     */
    public double distanceSquared(Point3D point3D) {
        return (this.get_x().get_coord() - point3D.get_x().get_coord()) * (this._x.get_coord() - point3D.get_x().get_coord()) +
                (this._y.get_coord() - point3D.get_Y().get_coord()) * (this._y.get_coord() - point3D.get_Y().get_coord()) +
                (this._z.get_coord() - point3D.get_z().get_coord()) * (this._z.get_coord() - point3D.get_z().get_coord());
    }


    /**
     * Distance between two Point3D objects.
     * @param point3D second Point3D object.
     * @return  Distance between the object and the received Point3D
     */
    public double distance(Point3D point3D)
    {
        return Math.sqrt(distanceSquared(point3D));
    }
}
