package renderer;

import geometries.Intersectable;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;
import elements.Camera;
import java.util.List;

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
     *
     * @param imageWriter
     * @param scene
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
               List<Point3D> intersectionPoints = geometries.findIntsersections(ray);
               if (intersectionPoints == null) {
                   imageWriter.writePixel(column, row, background);
               } else {
                   Point3D closestPoint = getClosesPoint(intersectionPoints);
                   java.awt.Color pixelColor = calcColor(closestPoint).getColor();
                   imageWriter.writePixel(column, row, pixelColor);
               }
           }
       }
   }

    /***
     * function to calculate the color for the closes point to the ray
     * @param p point, for calculating the color
     */
   public Color calcColor(Point3D p){
        return scene.get_ambientlight().GetIntensity();
   }

    /***
     * function receive some points, and return the point with the minimal distance
     * from the ray
     * @param intersectionsPoints several points
     * @return the closest point to the ray
     */
   private Point3D getClosesPoint(List<Point3D> intersectionsPoints){
       Point3D cameraPoint = scene.get_camera().getPlaceable();
       Point3D closestPoint = null;
       double minDistance = Double.MAX_VALUE;
       double distance = 0;

       for(Point3D point: intersectionsPoints){
           distance = cameraPoint.distance(point);
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
     *
     */
   public void writeToImage(){
       imageWriter.writeToImage();
   }
}
