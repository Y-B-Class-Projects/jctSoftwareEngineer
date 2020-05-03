package unittests;

import elements.Camera;
import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.assertEquals;

/**
 * Testing Integration
 *
 */
public class IntegrationTests {

    /**
     * Test method for
     * {@link elements.Camera#constructRayThroughPixel(int, int, int, int, double, double, double)}.
     */
    @Test
    public void CameraSphereIntersection() {
        Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
        Sphere sphere = new Sphere(new Point3D(0,0,3) , 1);
        int sum = 0;
        for (int i = 0; i<3 ; i++)
            for(int j = 0; j <3 ; j++) {
                var intsersections = sphere.findIntsersections(camera.constructRayThroughPixel(3, 3, i, j, 1, 3, 3));
                if(intsersections != null)
                    sum += intsersections.size();
            }
        assertEquals("Bad number of rays", 2, sum);

        sum = 0;
        camera = new Camera(new Point3D(0,0,-0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
        sphere = new Sphere(new Point3D(0,0,2.5) , 2.5);
        for (int i = 0; i<3 ; i++)
            for(int j = 0; j <3 ; j++) {
                var intsersections = sphere.findIntsersections(camera.constructRayThroughPixel(3, 3, i, j, 1, 3, 3));
                if(intsersections != null)
                    sum += intsersections.size();
            }
        assertEquals("Bad number of rays", 18, sum);

        sum = 0;
        sphere = new Sphere(new Point3D(0,0,2) , 2);

        for (int i = 0; i<3 ; i++)
            for(int j = 0; j <3 ; j++) {
                var intsersections = sphere.findIntsersections(camera.constructRayThroughPixel(3, 3, i, j, 1, 3, 3));
                if(intsersections != null)
                    sum += intsersections.size();
            }
        assertEquals("Bad number of rays", 10, sum);

        sum = 0;
        sphere = new Sphere(new Point3D(0,0,2) , 4);
        for (int i = 0; i<3 ; i++)
            for(int j = 0; j <3 ; j++) {
                var intsersections = sphere.findIntsersections(camera.constructRayThroughPixel(3, 3, i, j, 1, 3, 3));
                if(intsersections != null)
                    sum += intsersections.size();
            }
        assertEquals("Bad number of rays", 9, sum);

        sum = 0;
        sphere = new Sphere(new Point3D(0,0,-1) , 0.5);
        for (int i = 0; i<3 ; i++)
            for(int j = 0; j <3 ; j++) {
                var intsersections = sphere.findIntsersections(camera.constructRayThroughPixel(3, 3, i, j, 1, 3, 3));
                if(intsersections != null)
                    sum += intsersections.size();
            }
        assertEquals("Bad number of rays", 0, sum);
    }

    /**
     * Test method for
     * {@link elements.Camera#constructRayThroughPixel(int, int, int, int, double, double, double)}.
     */
    @Test
    public void CameraPlaneIntersection() {
        Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
        Plane plane = new Plane(new Point3D(0,0,2) , new Vector(0,0,1));

        int sum = 0;
        for (int i = 0; i<3 ; i++)
            for(int j = 0; j <3 ; j++) {
                var intsersections = plane.findIntsersections(camera.constructRayThroughPixel(3, 3, i, j, 1, 3, 3));
                if(intsersections != null)
                    sum += intsersections.size();
            }
        assertEquals("Bad number of rays", 9, sum);

        plane = new Plane(new Point3D(0,0,2) , new Vector(0,0.5,1));

        sum = 0;
        for (int i = 0; i<3 ; i++)
            for(int j = 0; j <3 ; j++) {
                var intsersections = plane.findIntsersections(camera.constructRayThroughPixel(3, 3, i, j, 1, 3, 3));
                if(intsersections != null)
                    sum += intsersections.size();
            }
        assertEquals("Bad number of rays", 9, sum);


        plane = new Plane(new Point3D(0,0,2) , new Vector(0,3,1));

        sum = 0;
        for (int i = 0; i<3 ; i++)
            for(int j = 0; j <3 ; j++) {
                var intsersections = plane.findIntsersections(camera.constructRayThroughPixel(3, 3, i, j, 1, 3, 3));
                if(intsersections != null)
                    sum += intsersections.size();
            }
        assertEquals("Bad number of rays", 6, sum);
    }

    /**
     * Test method for
     * {@link elements.Camera#constructRayThroughPixel(int, int, int, int, double, double, double)}.
     */
    @Test
    public void CameraTriangleIntersection() {
        Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
        Triangle triangle = new Triangle(new Point3D(0,-1,2) , new Point3D(1,1,2) , new Point3D(-1,1,2));

        int sum = 0;
        for (int i = 0; i<3 ; i++)
            for(int j = 0; j <3 ; j++) {
                var intsersections = triangle.findIntsersections(camera.constructRayThroughPixel(3, 3, i, j, 1, 3, 3));
                if(intsersections != null)
                    sum += intsersections.size();
            }
        assertEquals("Bad number of rays", 1, sum);

        triangle = new Triangle(new Point3D(0,-20,2) , new Point3D(1,1,2) , new Point3D(-1,1,2));

        sum = 0;
        for (int i = 0; i<3 ; i++)
            for(int j = 0; j <3 ; j++) {
                var intsersections = triangle.findIntsersections(camera.constructRayThroughPixel(3, 3, i, j, 1, 3, 3));
                if(intsersections != null)
                    sum += intsersections.size();
            }
        assertEquals("Bad number of rays", 2, sum);
    }
}

