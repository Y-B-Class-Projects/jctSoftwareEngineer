package geometries;

import primitives.*;

import java.util.List;

/**
 * Interface for all Geometry Objects.
 */
abstract class Geometry implements Intersectable {
    protected Color _emission;
    protected Material _material;

/************    constructors   ************/

    /**
     * Geometry constructor with specific color
     */
    public Geometry(Color _emission) {
        this._emission = _emission;
        this._material = new Material(0, 0, 0);
    }

    /**
     * Geometry constructor with color black
     */
    public Geometry() {
        this._emission = Color.BLACK;
        this._material = new Material(0, 0, 0);
    }

    /***
     * constructor for initialize Geometry with the two parameters
     * @param _emission color
     * @param _material material type of the object
     */
    public Geometry(Color _emission, Material _material) {
        this._emission = _emission;
        this._material = _material;
    }
    /************    getters     **********/
    /**
     * normal getter
     * @param point3D 3D point
     * @return Vector normal to the body at the received point
     */
    public abstract Vector getNormal(Point3D point3D);

    /**
     * object color getter
     * @return object color
     */
    public Color get_emission() {
        return _emission;
    }

    /***
     * material getter
     * @return the material that the object made from
     */
    public Material get_material() {
        return new Material(_material.getkD(), _material.getkS(), _material.getnShininess());
    }

    @Override
    public abstract List<GeoPoint> findIntsersections(Ray ray);


}