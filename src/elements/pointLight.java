package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/***
 * class to represent omni-directional point source,
 * meaning that the rays of the light spread to all the
 * directions equally (360 degree)
 */

public class pointLight extends Light implements LightSource {

    protected Point3D _position;
    protected double _kC, _kl, _kQ;

/***********     constructor   *************/
    /***
     * constructor to initialize the fields
     * @param _position position of the source light in the space
     * @param _kC constant factor for attenuation
     * @param _kl linear factor for attenuation
     * @param _kQ qatar(חזקה) factor for attenuation
     */
    public pointLight(Point3D _position, double _kC, double _kl, double _kQ, Color color) {
        super(color);
        this._position = _position;
        this._kC = _kC;
        this._kl = _kl;
        this._kQ = _kQ;
    }
/********   functions    *******/
    @Override
    public Color getIntensity(Point3D p) {
        return null;
    }

    @Override
    public Vector getL(Point3D p) {
        return null;
    }
}
