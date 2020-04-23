package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * RadialGeometry class is an abstract class for all geometric shapes in space that have a radius
 */
public abstract class RadialGeometry implements Geometry
{
    /** radius of the object */
    protected double _radius;

    /**
     * Constructs and initialized a RadialGeometry object
     * @param _radius the radius of the RadialGeometry object
     */
    public RadialGeometry(double _radius)
    {
        this._radius = _radius;
    }

    /**
     * Copy constructor
     * @param radialGeometry radialGeometry to copy to the object.
     */
    public RadialGeometry(RadialGeometry radialGeometry)
    {
        _radius = radialGeometry.get_radius();
    }

    /**
     * radius value getter
     * @return radius value
     */
    public double get_radius() {
        return _radius;
    }

    /**
     * Representation the object by a string
     * @return string that represents the object.
     */
    @Override
    public String toString() {
        return "RadialGeometry{" +
                "_radius=" + _radius +
                '}';
    }
}
