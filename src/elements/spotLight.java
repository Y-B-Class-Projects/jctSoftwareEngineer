package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/***
 * class that extends from point light, it represent
 * a point light with direction. (such as luxo lump, spot)
 */

public class spotLight extends pointLight {

    private Vector _direction;
/***********    constructor    *************/
    /***
     * constructor for initialize the class spot light,
     * adding the property: direction.
     * @param _position same as the super
     * @param _kC same as the super
     * @param _kl same as the super
     * @param _kQ same as the super
     * @param _direction the direction of the point light
     * @param color the color of the light
     */
    public spotLight(Point3D _position, double _kC, double _kl, double _kQ, Vector _direction, Color color) {
        super(_position, _kC, _kl, _kQ, color);
        this._direction = _direction;
    }
}
