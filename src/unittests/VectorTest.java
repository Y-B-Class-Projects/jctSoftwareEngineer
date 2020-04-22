package unittests;

import org.junit.*;
import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static primitives.Util.isZero;

import primitives.Point3D;
import primitives.Vector;

class VectorTest {

    /****
     *  Unit tests for primitive.Vector class
     */
    @Test
    void testConstructor() {

        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1.0,0.0,0.0);
        // create new vector, normalized it and check if the result is correct.
        assertEquals("not correct result for normalization, 'CONSTRUCTOR' " ,new Vector(2.0,0.0,0.0).normalized(),v1);

        // =============== Boundary Values Tests ==================
        // try to create a zero vector, if success, error massage receive.
        try {
            Vector zero = new Vector(0.0,0.0,0.0);
            fail("Zero Vector does not throw an exception");
        }
        catch (IllegalArgumentException e) { }
    }

    /***
     *  Test method for {@link Vector#add(Vector)} ()}.
     */
    @Test
    void testAdd() {

        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1.0,1.0,1.0);
        Vector v2 = new Vector(-1.0,-1.0,0.0);

        v1 = v1.add(v2);
        // create two vectors v1 and v2, and try to add v2 to v1 ('correction check') POSITIVE VALUE
        assertEquals("wrong answer for add method, POSITIVE" , new Vector(0.0,0.0,1.0),v1);

        v2 = v2.add(v1);
        // correction check for NEGATIVE VALUE
        assertEquals("wrong answer for add method, NEGATIVE" ,new Vector(-1.0,-1.0,1.0),v2);


        // =============== Boundary Values Tests ==================
        // it should't be able to add v1 to v2, which result zero vector.
        try {
            v1 = new Vector(1,1,-1);
            v2.add(v1);
            fail("Addition of two opposing vectors does not give zero");
        }
        catch (IllegalArgumentException e){}
    }

    /***
     *  Test method for {@link Vector#subtruct(Vector)} (Vector)} ()}.
     */
    @Test
    void testSubtruct() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1.0,1.0,1.0);
        Vector v2 = new Vector(-1.0,-1.0,-1.0);

        v1 = v1.subtruct(v2);
        assertEquals("wrong result NEGATIVE VALUE" ,new Vector(2.0,2.0,2.0),v1);

        v2 = v2.subtruct(v1);
        assertEquals("wrong result POSITIVE VALUE" ,new Vector(-3.0,-3.0,-3.0), v2);

        // =============== Boundary Values Tests ==================
        // it should't be able to subtract two vectors that result the zero vector.
        try {
            v1.subtruct(v1);
            fail("Subtracting two equal vectors does not give a zero vector");
        }
        catch (IllegalArgumentException e){}
    }

    /***
     *  Test method for {@link Vector#scale(double)}
     */
    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1,2,3);
        // standard check for correction.
        assertEquals("wrong result for scale method" ,new Vector(2,4,6), v1.scale(2));

        // =============== Boundary Values Tests ==================
        // if scaling the vector with the value '1', it should return the same vector.
        assertEquals("wrong result for scaling with '1' " ,v1, v1.scale(1));
    }

    /***
     *  Test method for {@link Vector#dotProduct(Vector)}
     */
    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(3.5,-5,10);
        Vector v2 = new Vector(2.5,7,0.5);
        Vector v3 = new Vector(0, 3, -2);
        // standard check for correction.
        assertTrue(Double.compare(v1.dotProduct(v2), (8.75 + -35 + 5)) == 0);

        // =============== Boundary Values Tests ==================
        v1 = new Vector(0, 2, 3);
        v3 = new Vector(0, 3, -2);
        // check orthogonal vectors.
        assertTrue("dotProduct() for orthogonal vectors is not zero" ,isZero(v1.dotProduct(v3)));
    }
    /**
     * Test method for {@link Vector#crossProduct(Vector)}.
     */
    @Test
    public void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v3.length(), vr.length(), 0.00001);

        // Test cross-product result orthogonality to its operands
        assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v3)));

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-productof co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {}
    }


    /***
     *  Test method for {@link Vector#length()}.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        Vector v = new Vector(3.5,-5.0,10.0);
        // check if the result of the v vector length is correct.
        assertTrue("not correct result " ,v.length() == Math.sqrt(12.25 + 25 + 100));
    }

    /***
     *  Test method for {@link Vector#normalize()}.
     */
    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        Vector v = new Vector(3.5,-5,10);
        v.normalize();
        // check if the it return the correct result for normalization.
        assertEquals("wrong result for the normalize method" ,1,v.length(),1e-10);

        v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v);
        Vector vCopyNormalize = vCopy.normalize();
        assertTrue("normalize() function creates a new vector" , vCopy == vCopyNormalize);
    }

    /***
     *  Test method for {@link Vector#normalized()}.
     */
    @Test
    void testNormalized() {
        // ============ Boundary Values Tests ==============
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalized();
        assertTrue ("normalizated() function does not create a new vector" , u != v);
    }
}