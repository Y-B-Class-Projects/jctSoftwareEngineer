package geometries;
import java.util.*;

import geometries.Tube;
import primitives.*;
import primitives.Vector;

import javax.swing.*;

public class Cylinder extends Tube {
    private double _hight;
    private Point3D _Pcenter1;
    private Point3D _Pcenter2;
    private Plane _plane1;
    private Plane _plane2;

    // ***************** Constructors ********************** //

    /**
     * regular constructor
     * @param r
     * @param _axisPoint
     * @param _axisDirection
     * @param _hight
     * @param emission
     * @param material
     */
    public Cylinder(Color emission, Material material , double r, Point3D _axisPoint, Vector _axisDirection, double _hight){
        super(emission, material,r, new Ray(_axisPoint, _axisDirection));
        if(_hight <= 0)
            throw new IllegalArgumentException("Hight must be positive");
        this._hight = _hight;
        this._Pcenter1 = this._exisRay.get_p0().add(this._exisRay.get_dir().scale(_hight/2.0));
        this._Pcenter2 = this._exisRay.get_p0().add(this._exisRay.get_dir().scale(-_hight/2.0));

        this._plane1 = new Plane(this._emission, this._material ,this._Pcenter1, this._exisRay.get_dir());
        this._plane2 = new Plane(this._emission, this._material , this._Pcenter2, this._exisRay.get_dir());

    }

    /**
     * copy constructor
     * @param c
     */
    public Cylinder(Cylinder c) {
        super(c._emission, c._material , c._radius,new Ray(c._exisRay.get_p0(), c._exisRay.get_dir()));
        this._hight = c._hight;
        this._Pcenter1 = new Point3D (c._Pcenter1);
        this._Pcenter2 = new Point3D (c._Pcenter2);
        this._plane1 = new Plane(c._plane1._emission , c._plane1._material , c._plane1._p , c._plane1._normal);
        this._plane2 = new Plane(c._plane2._emission , c._plane2._material , c._plane2._p , c._plane2._normal);
    }

    // ***************** Getters ********************** //

    public double get_hight() {
        return _hight;
    }

    // ***************** Administration  ******************** //

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cylinder other = (Cylinder) obj;
        if (Double.doubleToLongBits(_hight) != Double.doubleToLongBits(other._hight))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + "Cylinder [_hight=" + _hight + "]";
    }

    // ***************** Operations ******************** //

    /**
     * checks if the point is on the first cap of the cylinder
     * @param p
     * @return boolean
     */
    public boolean is_on_cap1(Point3D p) {
        if(p.equals(_Pcenter1))
            return true;
        Vector v = p.subtruct(_Pcenter1);
        if(Util.isZero(p.subtruct(this._Pcenter1).dotProduct(this._exisRay.get_dir())) && v.dotProduct(v) < this._radius * this._radius)
            return true;
        return false;

    }

    /**
     * checks if the point is on the second cap of the cylinder
     * @param p
     * @return boolean
     */
    public boolean is_on_cap2(Point3D p) {
        if(p.equals(_Pcenter2))
            return true;
        Vector v = p.subtruct(_Pcenter2);
        if(Util.isZero(p.subtruct(this._Pcenter2).dotProduct(this._exisRay.get_dir())) && v.dotProduct(v) < this._radius * this._radius)
            return true;
        return false;

    }

    /**
     * checks if the point is on the first cap of the cylinder
     * given that is definitely on the plane of the cap
     * @param p
     * @return boolean
     */
    public boolean is_on_cap1_given_on_plane(Point3D p) {
        if(p.equals(_Pcenter1))
            return true;
        Vector v = p.subtruct(_Pcenter1);
        double a = Math.sqrt(v.dotProduct(v));

        if(Util.isZero((a - this._radius)))
            return false;
        if(a < this._radius)
            return true;
        return false;

    }

    /**
     * checks if the point is on the second cap of the cylinder
     * given that is definitely on the plane of the cap
     * @param p
     * @return boolean
     */
    public boolean is_on_cap2_given_on_plane(Point3D p) {
        if(p.equals(_Pcenter2))
            return true;
        Vector v = p.subtruct(_Pcenter2);
        double a = Math.sqrt(v.dotProduct(v));

        if(Util.isZero((a - this._radius)))
            return false;
        if(a < this._radius)
            return true;
        return false;

    }

    @Override
    public Vector getNormal(Point3D p) {

        if(this.is_on_cap1(p)) {
            if(!p.equals(_Pcenter1))
                return p.subtruct(this._exisRay.get_p0().add(p.subtruct(this._Pcenter1))).normalize();
            else
                return this._exisRay.get_dir().normalized();
        }
        if(this.is_on_cap2(p))
            if(!p.equals(_Pcenter2))
                return p.subtruct(this._exisRay.get_p0().add(p.subtruct(this._Pcenter2))).normalize();
            else
                return this._exisRay.get_dir().normalized();

        return super.getNormal(p);
    }

    @Override
    public List<GeoPoint> findIntsersections(Ray r) {
        ArrayList<GeoPoint> intersections = new ArrayList<>();
        ArrayList<Point3D> arrPoints = new ArrayList<>();

        List<GeoPoint> plane1Points = this._plane1.findIntsersections(r);//.get(this._plane1);
        List<GeoPoint> plane2Points = this._plane2.findIntsersections(r);//.get(this._plane2);
        List<GeoPoint> tubePoints = super.findIntsersections(r);//.get( (Tube)this);

        if(plane1Points != null)
            if(plane1Points.size() != 0)
                if(is_on_cap1_given_on_plane(plane1Points.get(0).point))
                    arrPoints.add(plane1Points.get(0).point);
        if(plane2Points != null)
            if(plane2Points.size() != 0)
                if(is_on_cap2_given_on_plane(new Point3D(plane2Points.get(0).point)))
                    arrPoints.add(new Point3D(plane2Points.get(0).point));
        if(tubePoints != null)
        {
            for(int i = 0; i < tubePoints.size(); i++)
            {
                double d1 = this._exisRay.get_dir().dotProduct(tubePoints.get(i).point.subtruct(this._Pcenter1));
                double d2 = this._exisRay.get_dir().dotProduct(tubePoints.get(i).point.subtruct(this._Pcenter2));
                if(d1 <= 0 && d2 >= 0)
                    arrPoints.add(tubePoints.get(i).point);
                else if(Util.isZero(d1) && Util.isZero(d2))
                    arrPoints.add(tubePoints.get(i).point);
            }
        }

        if(arrPoints.size() != 0) {
            ArrayList<GeoPoint> geoPoints = new ArrayList<>();

            for (Point3D arrPoint : arrPoints) {
                geoPoints.add(new GeoPoint(this, arrPoint));
            }

            intersections.addAll(geoPoints);
        }

        return intersections;
    }
}