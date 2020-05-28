package renderer;

import elements.LightSource;
import geometries.Intersectable;
import primitives.*;
import scene.Scene;
import elements.Camera;
import java.util.List;
import geometries.Intersectable.GeoPoint;
import static geometries.Intersectable.GeoPoint;
import static java.lang.Math.*;
import static java.lang.System.out;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/***
 * class Render, responsible to take all the calculation of ray intersections
 * and light, and to create a picture from that.
 */

public class Render {
    /***
     * scene - stores all the scene components
     * imageWriter - save the picture and project path and the pixels matrix
     */
   private Scene scene;
   private ImageWriter imageWriter;

    /*********     constructor   ***********/
    /***
     * constructor to initialized class Render
     * @param imageWriter use imageWriter class to write the picture
     * @param scene take from the scene all the details
     */

    public Render(ImageWriter imageWriter, Scene scene){
        this.imageWriter = imageWriter;
        this.scene = scene;
    }

   /********    functions    **********/
    /***
     * this function responsible for fill up the pixels matrix from the scene,
     * it receive from the scene the colors for each pixel and fill the matrix
     * in the image writer
     */
   public void renderImage(){
       Camera camera = scene.get_camera();
       Intersectable geometries = scene.get_geometries();
       java.awt.Color background = scene.get_background().getColor();
       double distance = scene.get_distance();

       //Nx and Ny are the width and height of the image.
       int Nx = imageWriter.getNx(); //columns
       int Ny = imageWriter.getNy(); //rows
       double width = imageWriter.getWidth();
       double height = imageWriter.getHeight();
       //pixels grid
       for (int row = 0; row < Ny; ++row) {
           for (int column = 0; column < Nx; ++column) {
               Ray ray = camera.constructRayThroughPixel(Nx, Ny, column, row, distance, width, height);
               List<GeoPoint> intersectionPoints = geometries.findIntsersections(ray);
               if (intersectionPoints == null) {
                   imageWriter.writePixel(column, row, background);
               } else {
                   GeoPoint closestPoint = getClosesPoint(intersectionPoints);
                   java.awt.Color pixelColor = calcColor(closestPoint).getColor();
                   imageWriter.writePixel(column, row, pixelColor);
               }
           }
       }
   }

    /***
     * function to calculate the color for the closes point to the ray
     * @param intersection point, for calculating the color
     */
   private Color calcColor(GeoPoint intersection){
       Color color = scene.get_ambientlight().get_intensity();
       color = color.add(intersection.geometry.get_emission());

       Vector v = intersection.point.subtruct(scene.get_camera().getPlaceable()).normalized();
       Vector n = intersection.geometry.getNormal(intersection.point);
       Material material =intersection.geometry.get_material();
       int nShininess = material.getnShininess();
       double kd = material.getkD();
       double ks = material.getkS();

       for (LightSource lightSource : scene.get_lights()) {
           Vector l = lightSource.getL(intersection.point);
           if (signum(n.dotProduct(l)) == signum(n.dotProduct(v)) && !isZero(n.dotProduct(l)) && !isZero(n.dotProduct(v))) {
               Color lightIntensity = lightSource.getIntensity(intersection.point);
               color = color.add(calcDiffusive(kd, l, n, lightIntensity), calcSpecular(ks, l, n, v, nShininess, lightIntensity));
           }
       }
       return color;
   }

    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        return lightIntensity.scale(kd * Math.abs(l.dotProduct(n)));
    }

    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
       Vector r = l.subtruct(n.scale(2*l.dotProduct(n))).normalized();  // r = l - 2*(l-n)*n
       double vr = alignZero(v.dotProduct(r));

        if(vr >= 0)
            return Color.BLACK;
        return lightIntensity.scale(ks * pow(-vr , nShininess));
    }


    /***
     * function receive some points, and return the point with the minimal distance
     * from the ray
     * @param intersectionsPoints several points
     * @return the closest point to the ray
     */
   private GeoPoint getClosesPoint(List<GeoPoint> intersectionsPoints){
       Point3D cameraPoint = scene.get_camera().getPlaceable();
       GeoPoint closestPoint = null;
       double minDistance = Double.MAX_VALUE;
       double distance = 0;

       for(GeoPoint point: intersectionsPoints){
           distance = cameraPoint.distance(point.point);
           if (distance<minDistance){
               minDistance = distance;
               closestPoint = point;
           }
       }
        return closestPoint;
   }

    /***
     * function to print grid into the scene, to make sure everything is in the right
     * place
     * @param interval size
     * @param color color of the grid
     */
   public void printGrid(int interval, java.awt.Color color){
       int Ny = imageWriter.getNy();
       int Nx = imageWriter.getNx();

       for (int row = 0; row < Ny; ++row) {
           for (int column = 0; column < Nx; ++column) {
               if (row % interval == 0 || column % interval == 0) {
                   imageWriter.writePixel(column, row, color);
               }
           }
       }
   }

    /***
     * write the details of the scene, by calling the function from Image Writer class
     */
   public void writeToImage(){
       imageWriter.writeToImage();
   }
}
