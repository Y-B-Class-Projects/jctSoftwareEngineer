package geometries;

import primitives.*;

import java.util.List;

/**
 * Interface for all Geometry Objects.
 */
abstract class Geometry implements Intersectable {


    protected Color _emission;

    /**
     * normal getter
     * @param point3D 3D point
     * @return Vector normal to the body at the received point
     */
    public abstract Vector getNormal(Point3D point3D);

    /**
     * Geometry constructor with specific color
     */
    public Geometry(Color _emission) {
        this._emission = _emission;
    }

    /**
     * Geometry constructor with color black
     */
    public Geometry() {
        this._emission = Color.BLACK;
    }

    /**
     * object color getter
     * @return object color
     */
    public Color get_emission() {
        return _emission;
    }

    @Override
    public abstract List<GeoPoint> findIntsersections(Ray ray);
}