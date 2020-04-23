package geometries;

import primitives.*;

/**
 * Interface for all Geometry Objects.
 */
interface geometry extends Intersectable {
    /**
     * normal getter
     * @param point3D 3D point
     * @return Vector normal to the body at the received point
     */
    Vector getNormal(Point3D point3D);
}