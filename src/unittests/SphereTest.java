package unittests;

import geometries.Plane;
import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.Intersectable.GeoPoint;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for geometries.Sphere class
 */
class SphereTest {

    /**
     * Test method for {@link geometries.Sphere#getNormal(Point3D)}.
     */
    @Test
    void testGetNormal() {
        Sphere sphere = new Sphere(new Point3D(0,0,1) , 1.0);

        // ============ Equivalence Partitions Tests ==============
        // There is a simple single test here
        assertEquals("Incorrect calculation of normal", sphere.getNormal(new Point3D(0,0,2)), new Vector( 0, 0, 1));
    }


    /**
     * Test method for {@link geometries.Sphere#findIntsersections(Ray)}
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(new Point3D(1, 0, 0) , 1d);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertEquals("TC01: Ray's line out of sphere", null,
                sphere.findIntsersections(new Ray(new Point3D(-1, 0, 0), new Vector(0.7071067811865476, 0.7071067811865476, 0.0))));

        // TC02: Ray starts before and crosses the sphere (2 points)
        GeoPoint p1 = new GeoPoint(sphere, new Point3D(0.0651530771650466, 0.355051025721682, 0));
        GeoPoint p2 =new GeoPoint(sphere, new Point3D(1.53484692283495, 0.844948974278318, 0));
        List<GeoPoint> result =  sphere.findIntsersections(new Ray(new Point3D(-1, 0, 0), new Vector(0.9486832980505139, 0.316227766016838, 0)));
        assertEquals("TC02: Wrong number of points", 2, result.size());
        if (result.get(0).point.get_x().get_coord() > result.get(1).point.get_x().get_coord())
            result = List.of(result.get(1), result.get(0));
        assertEquals("TC02: Ray crosses sphere", List.of(p1, p2), result);

        // TC03: Ray starts inside the sphere (1 point)

        p1.point = new Point3D(0.5, 0, 0.8660254037844386);

        result = sphere.findIntsersections(new Ray(new Point3D(0.5, 0, 0), new Vector(0, 0, 1)));

        assertEquals("TC03: Wrong number of points", 1, result.size());
        assertEquals("TC03: Ray inside sphere", List.of(p1), result);

        // TC04: Ray starts after the sphere (0 points)

        result = sphere.findIntsersections(new Ray(new Point3D(2, 0, 0), new Vector(0, 0, 1)));

        assertEquals("TC04: Wrong number of points", null, result);


        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)

        p1.point = new Point3D(1, 0, 1);
        result = sphere.findIntsersections(new Ray(new Point3D(1, -1, 0), new Vector(0.0, 0.7071067811865476, 0.7071067811865476)));

        assertEquals("TC11: Wrong number of points", 1, result.size());
        assertEquals("TC11: Ray starts at sphere and goes inside", List.of(p1), result);

        // TC12: Ray starts at sphere and goes outside (0 points)

        result = sphere.findIntsersections(new Ray(new Point3D(2, 0, 0), new Vector(1, 0, 0)));
        assertEquals("TC12: Wrong number of points", null, result);

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)

        p1.point = new Point3D(1, 1, 0);
        p2.point = new Point3D(1, -1, 0);

        result = sphere.findIntsersections(new Ray(new Point3D(1, -2, 0), new Vector(0, 1, 0)));
        assertEquals("TC13: Wrong number of points", 2, result.size());

        if (result.get(0).point.get_x().get_coord() > result.get(1).point.get_x().get_coord())
            result = List.of(result.get(1), result.get(0));
        assertEquals("TC13: Ray crosses sphere and goes through the center", List.of(p1, p2), result);

        // TC14: Ray starts at sphere and goes inside (1 points)

        p1.point = new Point3D(1, 1, 0);

        result = sphere.findIntsersections(new Ray(new Point3D(1, -1, 0), new Vector(0, 1, 0)));

        assertEquals("TC14: Wrong number of points", 1, result.size());
        assertEquals("TC14: Ray starts at sphere and goes inside through the center", List.of(p1), result);

        // TC15: Ray starts inside (1 points)

        p1.point = new Point3D(2, 0, 0);

        result = sphere.findIntsersections(new Ray(new Point3D(0.5, 0, 0), new Vector(1, 0, 0)));

        assertEquals("TC15: Wrong number of points", 1, result.size());
        assertEquals("TC15: Ray starts inside and goes through the center", List.of(p1), result);

        // TC16: Ray starts at the center (1 points)

        p1.point = new Point3D(2, 0, 0);

        result = sphere.findIntsersections(new Ray(new Point3D(0.5, 0, 0), new Vector(1, 0, 0)));

        assertEquals("TC16: Wrong number of points", 1, result.size());
        assertEquals("TC16: Ray starts at the center", List.of(p1), result);

        // TC17: Ray starts at sphere and goes outside (0 points)

        result = sphere.findIntsersections(new Ray(new Point3D(2, 0, 0), new Vector(1, 0, 0)));

        assertEquals("TC17: Wrong number of points", null, result);

        // TC18: Ray starts after sphere (0 points)

        result = sphere.findIntsersections(new Ray(new Point3D(3, 0, 0), new Vector(1, 0, 0)));

        assertEquals("TC18: Wrong number of points", null, result);

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point

        result = sphere.findIntsersections(new Ray(new Point3D(0, 0, -1), new Vector(0, 0, 1)));

        assertEquals("TC19: Wrong number of points", null, result);

        // TC20: Ray starts at the tangent point

        result = sphere.findIntsersections(new Ray(new Point3D(1, 1, 0), new Vector(0, 0, 1)));

        assertEquals("TC20: Wrong number of points", null, result);

        // TC21: Ray starts after the tangent point

        result = sphere.findIntsersections(new Ray(new Point3D(0, 0, 1), new Vector(0, 0, 1)));

        assertEquals("TC121: Wrong number of points", null, result);

        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line

        result = sphere.findIntsersections(new Ray(new Point3D(-1, 0, 0), new Vector(0, 0, 1)));

        assertEquals("TC22: Wrong number of points", null, result);
    }
}