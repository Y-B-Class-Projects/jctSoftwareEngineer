package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.*;

public class Geometries implements Intersectable {

    /**  list to contain group of geometries objects */
    private List<Intersectable> _geometriesList;

    /************* constructors **************/

    /***
     * Constructs and initialized the list to ArrayList.
     */
    public Geometries() {
        this._geometriesList = new ArrayList<Intersectable>();
    }

    public Geometries(Intersectable geometries) {
        this._geometriesList = new ArrayList<Intersectable>();
        this._geometriesList.add(geometries);
    }

    /*************  functions  *************/

    /**
     * function add to add single geometries object to the group.
     * @param geometries tho pointer to the list
     */
    public void add(Intersectable geometries) {
        this._geometriesList.add(geometries);
    }

    /***
     * function to check intersection with given ray and all the geometries object
     * @param ray to check for intersections with this ray
     * @return all the intersection in the objects group, when no intersections return null.
     */
    @Override
    public List<Point3D> findIntsersections(Ray ray) {
        return null;
    }

}

