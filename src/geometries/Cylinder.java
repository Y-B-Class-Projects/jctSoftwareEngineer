package geometries;

import primitives.*;

/**
 * Cylinder class represent a cylinder in the space by radius and height
 */
public class Cylinder extends Tube
{
    /** the height of the cylinder object*/
    protected double _height;

    /**
     * Constructs and initialized a Cylinder
     * @param _height height of the Cylinder
     * @param _radius radius of the Cylinder
     * @param _exisRay ray of the Cylinder
     */
    public Cylinder(double _radius ,  Ray _exisRay , double _height )
    {
        super(_radius , _exisRay);
        this._height = _height;
    }

    /**
     * Constructs and initialized a Cylinder
     * @param color
     * @param _radius
     * @param _exisRay
     * @param _height
     */
    public Cylinder(Color color,double _radius ,  Ray _exisRay , double _height ){
        this(_radius , _exisRay , _height);
        _emission = color;
    }

    /**
     * Constructs and initialized a Cylinder
     * @param color
     * @param material
     * @param _radius
     * @param _exisRay
     * @param _height
     */
    public Cylinder(Color color, Material material, double _radius , Ray _exisRay , double _height ){
        this(_radius , _exisRay , _height);
        _emission = color;
        _material = material;
    }

    /**
     * radius value getter
     * @return radius value
     */
    @Override
    public double get_radius() {
        return super.get_radius();
    }

    /**
     * height value getter
     * @return height value
     */
    public double get_height() {
        return _height;
    }

    /**
     * Representation the object by a string
     * @return string that represents the object.
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
