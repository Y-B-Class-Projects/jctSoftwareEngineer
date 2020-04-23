package geometries;

import primitives.*;

/**
 * Interface for all Geometry Objects.
 */
interface Geometry extends Intersectable {
    /**
     * normal getter
     * @param point3D 3D point
     * @return Vector normal to the body at the received point
     */
    Vector getNormal(Point3D point3D);
}