package unittests;
import org.junit.*;
import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;
import org.junit.jupiter.api.Test;
import org.junit.Assert.*;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class GeometriesTests {

    /***
     * Test method for {@link geometries.Geometries#findIntsersections(Ray)}
     */
    @Test
    void testfindIntsersections(Ray ray) {

        // ============ Equivalence Partitions Tests ==============

        Sphere sphere = new Sphere(new Point3D(1,1,1), 0.5);
        Ray test_ray = new Ray(new Point3D(0.8,0.8,0.8),new Vector(new Point3D(1,0,0)));
        Plane plane = new Plane(new Point3D(0,0,0), new Point3D(0,1,0), new Point3D(0,0,1));
        //  לא סיימתי עדיין את הפונקציה הזאת
        //assertEqual("wrong answer for findIntersectins calculate",sphere.findIntsersections(ray), sphere.findIntsersections(ray));

        // =============== Boundary Values Tests ==================


    }

}
