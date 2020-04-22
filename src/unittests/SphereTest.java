package unittests;

import geometries.Plane;
import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for geometries.Sphere class
 */
class SphereTest {

    /**
     * Test method for {@link Sphere#getNormal(Point3D)}.
     */
    @Test
    void testGetNormal() {
        Sphere sphere = new Sphere(new Point3D(0,0,1) , 1.0);

        // ============ Equivalence Partitions Tests ==============
        // There is a simple single test here
        assertEquals("Incorrect calculation of normal", sphere.getNormal(new Point3D(0,0,2)), new Vector( 0, 0, 1));
    }
}