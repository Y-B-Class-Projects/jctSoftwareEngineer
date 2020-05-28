package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/***
 * this class is represent all the directional lights, light
 * like the sun
 */

public class DirectionalLight extends Light implements LightSource{

    private Vector _direction;

/***********     constructor   ************/
    /***
     * constructor to initialize the direction vector
     * @param _direction receive vector
     */
    public DirectionalLight(Color color , Vector _direction) {
        super(color);
        this._direction = _direction;
    }
/*********     functions     ***********/
    @Override
    public Color getIntensity(Point3D p) {
        return _intensity;
    }

    @Override
    public Vector getL(Point3D p) {
        return _direction.normalized();
    }
}
