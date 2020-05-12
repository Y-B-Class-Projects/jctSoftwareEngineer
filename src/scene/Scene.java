package scene;

import elements.AmbientLight;
import elements.Camera;
import geometries.Geometries;
import geometries.Intersectable;
import geometries.Triangle;
import primitives.Color;
import primitives.Point3D;

import java.util.ArrayList;

/***
 * Scene class store all the scene components, including
 * Background color, ambient light, geometries objects, camera and
 * distance of the view plane from the Geometries objects.
 */
public class Scene {
    /***
     * name - scene name
     * background -  the background color
     * ambientlight - the color on the specific object
     * geometries - the geometries objects represent on the scene
     * camera - details about the camera
     * distance - the distance from the camera to the objects
     */
    private String _name;
    private Color _background;
    private AmbientLight _ambientlight;
    private Geometries _geometries;
    private Camera _camera;
    private double _distance;

    /*********   constructor   **********/

    /***
     * constructor for initialization
     * @param _name receive the scene name and initial it withe empty
     *              Geometries list
     */
    public Scene(String _name) {
        this._name = _name;
        this._geometries = new Geometries();
    }

    /*********   Getter's    ********/

    /***
     *  name Getter
     * @return name of the scene
     */
    public String get_name() {
        return _name;
    }
    /***
     * background color Getter
     * @return background color
     */
    public Color get_background() {
        return _background;
    }
    /***
     * ambient light Getter
     * @return ambient light
     */
    public AmbientLight get_ambientlight() {
        return _ambientlight;
    }
    /***
     * geometries Getter
     * @return the list fo all the Geometries on the scene
     */
    public Geometries get_geometries() {
        return _geometries;
    }
    /***
     * camera Getter
     * @return the camera components
     */
    public Camera get_camera() {
        return _camera;
    }
    /***
     * distance Getter
     * @return the distance between the view plane and geometries
     */
    public double get_distance() {
        return _distance;
    }

    /*********  functions  ***********/
    /***
     * function to update the background color
     * @param color the color of the background
     */
    public void setBackground(Color color){
        this._background = color;
    }
    /***
     * function to update the ambient light of the object
     * @param color ambient light color
     */
    public void setAmbientLight(AmbientLight color){
        this._ambientlight = color;
    }
    /***
     * function to update the camera details
     * @param param camera details
     */
    public void setCamera(Camera param){
        this._camera = param;
    }
    /***
     * function to update the distance from view plane to camera
     * @param dis the distance from view plane to camera
     */
    public void setDistance(double dis){
        this._distance = dis;
    }

    /***
     * function to add Geometries objects to the scene
     * receive list of Point3D and add them to the geometries list.
     * @param geometries Geometries objects
     */
    public void addGeometries(Intersectable geometries){
        this._geometries.add(geometries);
    }

    //public void addGeometries(Triangle triangle, Triangle triangle1, Triangle triangle2, Triangle triangle3) { }
}
