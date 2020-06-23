package geometries;

import primitives.*;

import java.util.List;

/**
 * abstract class for all Geometry Objects!
 * the Geometry Objects has to contain: Color, Material.
 * and also function of: "getNormal()", "findIntersection()".
 */
public abstract class Geometry implements Intersectable {
    protected Color _emission;
    protected Material _material;

/************    constructors   ************/

    /**
     * Geometry constructor with specific color
     */
    public Geometry(Color _emission) {
        this(_emission,new Material(0, 0, 0));
    }

    /**
     * Geometry constructor with color black
     */
    public Geometry() {
        this(Color.BLACK,new Material(0, 0, 0));
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
     * @return Vector normal to the body at the received point. (normal to the object, at the
     * object point intersection)
     */
    public abstract Vector getNormal(Point3D point3D);

    /**
     * object color getter
     * @return object color
     */
    public Color get_emission() {
        return new Color(_emission);
    }

    /***
     * material getter
     * @return the material that the object made from
     */
    public Material get_material() {
        return new Material(_material.getkD(), _material.getkS(), _material.getnShininess() , _material.getkT() , _material.getkR());
    }

    @Override
    public abstract List<GeoPoint> findIntsersections(Ray ray);

}