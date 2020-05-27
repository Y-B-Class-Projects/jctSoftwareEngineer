package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.lang.reflect.Array;
import java.util.*;

public class Geometries implements Intersectable {

    /**  list to contain group of geometries objects */
    private ArrayList<Intersectable> _geometriesList;

    /************* constructors **************/

    /***
     * Constructs and initialized the list to ArrayList.
     */
    public Geometries() {
        this._geometriesList = new ArrayList<Intersectable>();
    }

    public Geometries(Intersectable geometries) {
        this();
        this._geometriesList.add(geometries);
    }

    /*************  functions  *************/

    /**
     * function add to add single geometries object to the group.
     * @param geometries tho pointer to the list
     */
    public void add(Intersectable... geometries) {
        this._geometriesList.addAll(Arrays.asList(geometries));
    }

    /***
     * function to add all the intersection that stores in the _geometriesList
     * to a list, using iterator principle that run for each Geometry element
     * @param ray to check for intersections with this ray
     * @return all the intersection in the objects group, when no intersections return null.
     */
    @Override
    public ArrayList<GeoPoint> findIntsersections(Ray ray) {
        ArrayList<GeoPoint> intersections = null;
        for (Intersectable geometry : _geometriesList) {
            ArrayList<GeoPoint> tempIntersections = (ArrayList<GeoPoint>) geometry.findIntsersections(ray);
            if (tempIntersections != null) {
                if (intersections == null)
                    intersections = new ArrayList<GeoPoint>();
                intersections.addAll(tempIntersections);
            }
        }
        return intersections;
    }

}

