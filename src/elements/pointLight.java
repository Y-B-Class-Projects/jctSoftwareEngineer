package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import static java.lang.System.out;

/***
 * class to represent omni-directional point source,
 * meaning that the rays of the light spread to all the
 * directions equally (360 degree)
 */

public class pointLight extends Light implements LightSource {

    protected Point3D _position;
    protected double _kC, _kl, _kQ, _radius;

/***********     constructor   *************/

    /***
     * constructor to initialize the fields
     * @param _intensity color of the light source
     * @param _position position of the source light in the space
     * @param _kC constant factor for attenuation
     * @param _kl linear factor for attenuation
     * @param _kQ qatar(חזקה) factor for attenuation
     * @param _radius radius that represent the size of the light source object.
     */
    public pointLight(Color _intensity, Point3D _position, double _kC, double _kl, double _kQ, double _radius) {
        this(_intensity,_position,_kC,_kl,_kQ);
        this._radius = _radius;
    }

    /***
     * constructor to initialize the fields
     * @param _position position of the source light in the space
     * @param _kC constant factor for attenuation
     * @param _kl linear factor for attenuation
     * @param _kQ qatar(חזקה) factor for attenuation
     */
    public pointLight(Color color, Point3D _position, double _kC, double _kl, double _kQ) {
        super(color);
        this._position = new Point3D(_position);
        this._kC = _kC;
        this._kl = _kl;
        this._kQ = _kQ;
        _radius = 0;
    }

    @Override
    public double getRaduis() {
        return _radius;
    }

    @Override
    public double getDistance(Point3D point) {
        return _position.distance(point);
    }

    /********   functions    *******/
    @Override
    public Color getIntensity(Point3D p) {
        double d = p.distance(_position);
        return _intensity.reduce(_kC + _kl*d + _kQ * d * d);
    }

    @Override
    public Vector getL(Point3D p) {
        return (p.subtruct(_position)).normalize();
    }
}
