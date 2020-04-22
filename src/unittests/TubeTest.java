package unittests;

import geometries.Plane;
import geometries.Tube;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for geometries.Tube class
 */
class TubeTest {
    /**
     * Test method for {@link Tube#getNormal(Point3D)}.
     */
    @Test
    void testGetNormal() {
        Tube tube = new Tube(1.0 , new Ray(new Point3D(0,0,0) , new Vector(0,0,1)));

        Point3D P = new Point3D(1,0,10); // the point for the normal

        // ============ Equivalence Partitions Tests ==============
        // There is a simple single test here
        assertEquals("Incorrect calculation of normal" , tube.getNormal(P) ,new Vector(1,0,0));
    }
}