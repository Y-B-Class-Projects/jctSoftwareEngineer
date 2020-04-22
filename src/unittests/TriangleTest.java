package unittests;

import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

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
}