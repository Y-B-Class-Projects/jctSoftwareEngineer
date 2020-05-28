package elements;

import primitives.Color;

/**
 * class to represent an ambient lighting for the objects.
 * extends from the Light class
 */
public class AmbientLight extends Light{

/**********  constructor   *************/
    /**
     * AmbientLight constructor, use the super constructor
     * with the parameter: Ia*Ka.
     * @param Ia ambient light
     * @param Ka bright
     */
    public AmbientLight(Color Ia , double Ka){
        super(Ia.scale(Ka));
    }



}
