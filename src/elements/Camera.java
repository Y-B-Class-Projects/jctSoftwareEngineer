package elements;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.LinkedList;
import java.util.Random;

import static java.lang.System.out;

/***
 * Camera: class to represent a camera on the graphical scene
 */
public class Camera {

    private Point3D placeable;
    private Vector v_up;
    private Vector v_to;
    private Vector v_right;

/************    constructor's    **************/

    /***
     * constructor for initialized Camera class, has
     * to make sure that they normalized and " MEUNACHIM ".
     * @param placeable where the eye camera are placeble
     * @param v_up up vector from the eye of the camera
     * @param v_to forward vector from the eye of the camera
     */
    public Camera(Point3D placeable,  Vector v_to , Vector v_up){
        if (!Util.isZero(v_to.dotProduct(v_up)))
            throw new IllegalArgumentException("not normalized vectors");
        v_to.normalized();
        v_up.normalized();
        this.placeable = new Point3D(placeable);
        this.v_up = new Vector(v_up);
        this.v_to = new Vector(v_to);
        this.v_right = new Vector(v_to.crossProduct(v_up));
    }

/*****************  getter's  **************/

    /***
     * Placeable value getter
     * @return where the camera eye are situated
     */
    public Point3D getPlaceable() {
        return placeable;
    }

    /***
     * vector_up value getter
     * @return vector_up value
     */
    public Vector getV_up() {
        return v_up;
    }

    /***
     * vector_toward value getter
     * @return vector_toward value
     */
    public Vector getV_to() {
        return v_to;
    }

    /***
     * vector_right value getter
     * @return vector_right value
     */
    public Vector getV_right() {
        return v_right;
    }

/************   functions   ****************/

    /***
     * build rays from camera through view plane (in specific pixel) to the objects
     * (NOTE: this function not calculating the intersections with the geometries objects)
     * @param nX with of pixel
     * @param nY height of pixel
     * @param j column index in the view plane
     * @param i row index in the view plane
     * @param screenDistance distance of pixel from camera eye
     * @param screenWith size of view plane with
     * @param screenHeight size of view plane height
     * @return a rya start from camera eye through pixel to the objects
     */
    public LinkedList<Ray> constructRaysThroughPixel(int nX, int nY, int j, int i, double screenDistance, double screenWith, double screenHeight){
        //image center
        Point3D pCenter = placeable.add(v_to.scale(screenDistance));

        // Ratio (Pixel with & height)
        double Ry = screenHeight/nY;
        double Rx = screenWith/nX;

        // range of random for x
        double Xmin = j * Rx - nX/2.0*Rx;
        double Xmax = (j+1) * Rx - nX/2.0*Rx;

        // range of random for y
        double Ymin = i * Ry - nY/2.0*Ry;
        double Ymax = (i+1) * Ry - nY/2.0*Ry;


        LinkedList<Ray> ret = new LinkedList<Ray>();
        double x;
        double y;
        Random rand = new Random();
        for (int n = 0; n < 50 ; n++){
            // create ray through random point in specific pixel at the view plane
            x = Xmin + (Xmax - Xmin) * rand.nextDouble();
            y = Ymin + (Ymax - Ymin) * rand.nextDouble();
            ret.add(createRayTroughViewPlane(x, y, pCenter));
        }
        return ret;
    }

    /***
     * build 5 boundary rays from camera through view plane (corners and center of specific pixel) to the objects
     * (NOTE: this function not calculating the intersections with the geometries objects)
     * @param nX with of pixel
     * @param nY height of pixel
     * @param j column index in the view plane
     * @param i row index in the view plane
     * @param screenDistance distance of pixel from camera eye
     * @param screenWith size of view plane with
     * @param screenHeight size of view plane height
     * @return a rya start from camera eye through pixel to the objects
     */
    public LinkedList<Ray> constructBoundingRaysThroughPixel(int nX, int nY, int j, int i, double screenDistance, double screenWith, double screenHeight){
        //image center
        Point3D pCenter = placeable.add(v_to.scale(screenDistance));

        // Ratio (Pixel with & height)
        double Ry = screenHeight/nY;
        double Rx = screenWith/nX;

        // range of random for x and y
        // range of random for x
        double Xmin = j * Rx - nX/2.0*Rx;
        double Xmax = (j+1) * Rx - nX/2.0*Rx;
        double Ymin = i * Ry - nY/2.0*Ry;
        double Ymax = (i+1) * Ry - nY/2.0*Ry;

        LinkedList<Ray> rays = new LinkedList<Ray>();
        // center of pixel
        rays.add(createRayTroughViewPlane((Xmax + Xmin)/2, (Ymax + Ymin)/2, pCenter));
        // boundary of pixel
        rays.add(createRayTroughViewPlane(Xmin, Ymin, pCenter));
        rays.add(createRayTroughViewPlane(Xmin, Ymax, pCenter));
        rays.add(createRayTroughViewPlane(Xmax, Ymin, pCenter));
        rays.add(createRayTroughViewPlane(Xmax, Ymax, pCenter));
       return rays;
    }

    /***
     * function to build ray inside specific place in specific pixel.
     * @param x x coordinate inside pixel. (not the original coordinate)
     * @param y y coordinate inside pixel. (not the original coordinate)
     * @param pCenter the center of the view plane
     * @return build ray
     */
    private Ray createRayTroughViewPlane(double x, double y, Point3D pCenter){
        if (x != 0) pCenter = pCenter.add(v_right.scale(x));
        if (y != 0) pCenter = pCenter.add(v_up.scale(-y));
        Vector vIJ = pCenter.subtruct(placeable).normalized();
        return new Ray(placeable, vIJ);
    }
/***************   old function only for the test's   **********************/

    /***
     * build rays from camera through view plane (in specific pixel) to the objects
     * (NOTE: this function not calculating the intersections with the geometries objects)
     * @param nX with of pixel
     * @param nY height of pixel
     * @param j column index in the view plane
     * @param i row index in the view plane
     * @param screenDistance distance of pixel from camera eye
     * @param screenWith size of view plane with
     * @param screenHeight size of view plane height
     * @return a rya start from camera eye through pixel to the objects
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i, double screenDistance, double screenWith, double screenHeight){

        //Ratio (pixel width & height)
        double Ry = screenHeight/nY;
        double Rx = screenWith/nX;

        double Xmin = j * Rx;
        double Xmax = (j+1) * Rx;

        double Ymin = i * Ry;
        double Ymax = (i+1) * Ry;


        //image center
        Point3D pCenter = placeable.add(v_to.scale(screenDistance));



        double xJ = (j - nX/2.0) * Rx + (Rx/2.0);
        double yI = (i - nY/2.0) * Ry + Ry/2.0;

        Point3D pIJ = pCenter;
        if (xJ != 0) pIJ = pIJ.add(v_right.scale(xJ));
        if (yI != 0) pIJ = pIJ.add(v_up.scale(-yI));

        Vector vIJ = pIJ.subtruct(placeable).normalized();

        return new Ray(placeable , vIJ);

    }
}
