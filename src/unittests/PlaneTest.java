package unittests;

import geometries.Plane;
import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

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
        // TC01: Ray's line is outside the sphere (1 point).



        // TC02: Ray does not intersect the plane(0 points).


        // =============== Boundary Values Tests ==================

        //****Group: Ray is parallel to the plane
        //TC10: ray included in the plane(infinity points = 0 points).
        //TC11: ray not included in the plane(0 point).

        //****Group: Ray is orthogonal to the plane
        //according to 𝑃0 (, in, after the plane).
        // TC12: P0 before the plane (1 point).
        // TC13: P0 before in plane (0 points).
        // TC14: P0 after the plane (0 points).

        // TC15:Ray is neither orthogonal nor parallel to and begins at the plane (𝑃0 is in the plane, but not the ray)(0 points).


        // TC16:Ray is neither orthogonal nor parallel to the plane and begins in the same point which appears as reference point in the plane (Q) (0 points).


    }
}