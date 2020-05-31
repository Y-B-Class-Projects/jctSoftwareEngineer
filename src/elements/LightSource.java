package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/***
 * interface LightSource to represent, how the light energy L will
 * be reflected to the camera!
 * for this we will use the Phong Reflectence Model
 */

public interface LightSource {

    /***
     * function to prevent the color of the specific light
     * @param p point in the 3D space
     * @return the color of this specific point
     */
    public Color getIntensity(Point3D p);

    /***
     * function to prevent the vector of the direction
     * @param p receive point
     * @return the direction of the source light
     */
    public Vector getL(Point3D p);
}