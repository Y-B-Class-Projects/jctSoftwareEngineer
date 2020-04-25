package geometries;
import primitives.Point3D;
import primitives.Ray;
import java.util.List;

/**
 * interface for all Geometries to find intersections with ray.
 */
public interface Intersectable
{
    List<Point3D> findIntsersections(Ray ray);
}
