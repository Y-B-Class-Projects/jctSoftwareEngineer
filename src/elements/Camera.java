package elements;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

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
    public Camera(Point3D placeable, Vector v_up, Vector v_to){
        if (v_to.dotProduct(v_up) != 0)
            throw new IllegalArgumentException("not normalized vectors");
        v_to.normalized();
        v_up.normalized();
        this.placeable = new Point3D(placeable);
        this.v_up = new Vector(v_up);// אפשר להכניס אותם ידנית ובכך לחסוך העתקה (1,0,0) ו (0,1,0)
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
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i, double screenDistance, double screenWith, double screenHeight){

        return null;
    }
}
