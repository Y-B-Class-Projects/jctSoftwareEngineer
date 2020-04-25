package unittests;

import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for geometries.Triangle class
 */
class TriangleTest {

    /**
     * Test method for {@link Triangle#getNormal(Point3D)}.
     */
    @Test
    void testGetNormal() {
        Triangle triangle = new Triangle(new Point3D(1,2,3) , new Point3D(2,2,3) , new Point3D(3,3,4));

        // ============ Equivalence Partitions Tests ==============
        // There is a simple single test here
        assertEquals("Incorrect calculation of normal" , triangle.getNormal(null) ,new Vector(0 , -1/Math.sqrt(2) ,1/Math.sqrt(2)));
    }

    /**
     * Test method for {@link geometries.Triangle#findIntsersections(Ray)}
     */
    @Test
    public void testFindIntersections() {
        Triangle triangle = new Triangle(new Point3D(1,1,0) , new Point3D(1,-1,0) , new Point3D(2,0,0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: ray goes inside triangle (1 points)

        Point3D p1 = new Point3D(1.5, 0, 0);
        List<Point3D> result = triangle.findIntsersections(new Ray(new Point3D(1.5,0,-1), new Vector(0.0, 0.0, 1)));

        assertEquals("TC01: Wrong number of points", 1, result.size());
        assertEquals("TC01: Ray goes inside triangle", List.of(p1), result);

        // TC02: ray goes outside against edge (0 points)

        result = triangle.findIntsersections(new Ray(new Point3D(1.5, 10, -1), new Vector(0, 0, 1)));

        assertEquals("TC02: Wrong number of points", null, result);

        // TC03: Outside against vertex (0 points)

        // =============== Boundary Values Tests ==================

        // TC10: Ray goes through the edge of triangle (0 points)

        p1 = new Point3D(1, 0, 0);
        result = triangle.findIntsersections(new Ray(new Point3D(1,0,-1), new Vector(0.0, 0.0, 1)));

        assertEquals("TC10: Wrong number of points", null, result);

        // TC11: Ray goes through the vertex of triangle (0 points)

        p1 = new Point3D(2, 0, 0);
        result = triangle.findIntsersections(new Ray(new Point3D(2,0,-1), new Vector(0.0, 0.0, 1)));

        assertEquals("TC11: Wrong number of points", null, result);

        // TC12: Ray goes through the edge continuation of triangle (0 point)

        result = triangle.findIntsersections(new Ray(new Point3D(1,2,-1), new Vector(0.0, 0.0, 1)));

        assertEquals("TC12: Wrong number of points", null, result);

        // TC13: Ray start at the edge of triangle (0 points)

        result = triangle.findIntsersections(new Ray(new Point3D(1,0,0), new Vector(0.0, 0.0, 1)));

        assertEquals("TC13: Wrong number of points", null, result);

        // TC14: Ray start at the vertex of triangle (0 points)

        p1 = new Point3D(2, 0, 0);
        result = triangle.findIntsersections(new Ray(new Point3D(1,1,0), new Vector(0.0, 0.0, 1)));

        assertEquals("TC14: Wrong number of points", null, result);

        // TC15: Ray start at the edge continuation of triangle (0 points)

        p1 = new Point3D(1, 2, 0);
        result = triangle.findIntsersections(new Ray(new Point3D(1,10,0), new Vector(0.0, 0.0, 1)));

        assertEquals("TC15: Wrong number of points", null, result);
    }
}