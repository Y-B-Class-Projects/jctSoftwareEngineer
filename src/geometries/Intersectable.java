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
     *Class of Representation geometry object and point on it
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        /**
         * constructor
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry , Point3D point){
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

            if (geometry != null ? !geometry.equals(geoPoint.geometry) : geoPoint.geometry != null) return false;
            return point != null ? point.equals(geoPoint.point) : geoPoint.point == null;
        }
    }


    List<GeoPoint> findIntsersections(Ray ray);
}
