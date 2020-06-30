package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import static primitives.Util.alignZero;

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
    public spotLight( Color color, Point3D _position,Vector _direction, double _kC, double _kl, double _kQ) {
        super(color,_position, _kC, _kl, _kQ);
        this._direction = new Vector(_direction).normalized();
    }

    /***
     * constructor for initialize the class spot light, adding the property radius
     * @param _intensity the color of the light
     * @param _position same as the super
     * @param _kC same as the super
     * @param _kl same as the super
     * @param _kQ same as the super
     * @param _radius radius of the source light
     * @param _direction the direction of the point light
     */
    public spotLight(Color _intensity, Point3D _position,Vector _direction, double _kC, double _kl, double _kQ,  double _radius) {
        super(_intensity, _position, _kC, _kl, _kQ, _radius);
        this._direction = new Vector(_direction).normalized();
    }

    @Override
    public Color getIntensity(Point3D p) {
        Vector l = getL(p);
        double dl = alignZero(_direction.dotProduct(l)); // = cos(a) , a is the angle between _dir and l

        if(dl <= 0){
            return Color.BLACK;
        }
        return super.getIntensity(p).scale(dl);
    }
}
