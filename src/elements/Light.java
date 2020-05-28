package elements;

import primitives.Color;

/***
 * abstract class to represent light source model!
 * meaning it represent the rays that spreading from the source light
 * to the objects(התפשטות קרני האור)
 */

abstract class Light {

    protected Color _intensity;

/***********    constructor    ***********/
    /***
     * constructor for class Light, which receive color and set the Light.
     * @param _intensity intensity
     */
    public Light(Color _intensity) {
        this._intensity = _intensity;
    }

/***********    getter    ***********/

    /***
     * getter for get_intensity
     * @return _intensity
     */
    public Color get_intensity() {
        return new Color(_intensity);
    }

}
