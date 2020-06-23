package geometries;
import primitives.Point3D;
import primitives.Ray;
import java.util.List;

/**
 * interface for all Geometries to find intersections with ray.
 */
public interface Intersectable
{
    /**
     *Class of Representation geometry object and point on it, (the point inside the geometry)
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        /**
         * constructor GeoPoint class
         * @param geometry the geometry object that intersect the ray
         * @param point the specific point on the geometry
         */
        public GeoPoint(Geometry geometry , Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         * equals function for GeoPoint class
         * @param o object for comparison
         * @return true if the object passed is equal to the object itself, otherwise false
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            GeoPoint geoPoint = (GeoPoint) o;

            return ((geometry.equals(geoPoint.geometry)) && (point.equals(geoPoint.point)));
        }
    }

    /**
     * return all intersections between ray and the object (geometry)
     * @param ray ray with the intersection
     * @return for each different geometry object that intersect with the ray return the intersect point (that seating
     * on the geometry object).
     */
    List<GeoPoint> findIntsersections(Ray ray);
}