package unittests;

import geometries.Plane;
import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import static org.junit.Assert.*;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for geometries.Plane class
 */
class PlaneTest {

    /**
     * Test method for {@link Plane#getNormal(Point3D)}.
     */
    @Test
    void testGetNormal() {
        Plane plane = new Plane(new Point3D(1,2,3) , new Point3D(2,2,3) , new Point3D(3,3,4));

        // ============ Equivalence Partitions Tests ==============
        // There is a simple single test here
        assertEquals("Incorrect calculation of normal" , plane.getNormal(null) ,new Vector(0 , -1/Math.sqrt(2) ,1/Math.sqrt(2)));
    }


    /**
     * Test method for {@link geometries.Plane#findIntsersections(Ray)}
     */
    @Test
    public void testFindIntersections() {
        Plane plane = new Plane(new Point3D(1,0,0) , new Vector(0,0,1));

        // ============ Equivalence Partitions Tests ==============
        //****Group: The Ray neither orthogonal or parallel to the plane
        // TC01: Ray intersects the plane (1 point).

        Point3D p1 = new Point3D(2,0,0);

        List<Point3D> result = plane.findIntsersections(new Ray(new Point3D(1, 0, 1), new Vector(0.7071067811865476, 0, -0.7071067811865476)));



        assertEquals("TC01: Wrong number of points", 1, result.size());
        assertEquals("TC01: Ray intersects the plane", List.of(p1), result);

        // TC02: Ray does not intersect the plane(0 points).

        result = plane.findIntsersections(new Ray(new Point3D(1, 0, 1), new Vector(0.7071067811865476, 0, 0.7071067811865476)));

        assertEquals("TC02: Wrong number of points", null, result);

        // =============== Boundary Values Tests ==================

        //****Group: Ray is parallel to the plane
        //TC10: ray included in the plane(infinity points = 0 points).

        result = plane.findIntsersections(new Ray(new Point3D(1, 1, 1), new Vector(0.7071067811865476, 0.7071067811865476, 0)));

        assertEquals("TC10: Wrong number of points", null, result);

        //TC11: ray not included in the plane(0 point).

        result = plane.findIntsersections(new Ray(new Point3D(1, 1, 1), new Vector(1, 0, 0)));

        assertEquals("TC11: Wrong number of points", null, result);

        //****Group: Ray is orthogonal to the plane
        // TC12: P0 before the plane (1 point).

        p1 = new Point3D(2,2,0);

        result = plane.findIntsersections(new Ray(new Point3D(2, 2, -1), new Vector(0, 0, 1)));

        assertEquals("TC12: Wrong number of points", 1, result.size());

        assertEquals("TC12: Ray is orthogonal and before the plane", List.of(p1), result);

        // TC13: P0 start in plane (0 points).

        result = plane.findIntsersections(new Ray(new Point3D(2, 2, 0), new Vector(0, 0, 1)));

        assertEquals("TC12: Wrong number of points", null, result);

        // TC14: P0 after the plane (0 points).

        result = plane.findIntsersections(new Ray(new Point3D(2, 2, 1), new Vector(0, 0, 1)));

        assertEquals("TC12: Wrong number of points", null, result);

        // TC15:Ray is neither orthogonal nor parallel to and begins at the plane (P0 is in the plane, but not the ray)(0 points).

        result = plane.findIntsersections(new Ray(new Point3D(2, 2, 0), new Vector(0.5773502691896258, 0.5773502691896258, 0.5773502691896258)));

        assertEquals("TC12: Wrong number of points", null, result);

        // TC16:Ray is neither orthogonal nor parallel to the plane and begins in the same point which appears as reference point in the plane (Q) (0 points).

        result = plane.findIntsersections(new Ray(new Point3D(1, 0, 0), new Vector(0.5773502691896258, 0.5773502691896258, 0.5773502691896258)));

        assertEquals("TC12: Wrong number of points", null, result);
    }
}