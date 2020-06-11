package renderer;

import elements.LightSource;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.*;
import scene.Scene;
import elements.Camera;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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

   private static final int MAX_CALC_COLOR_LEVEL = 10;
   private static final double MIN_CALC_COLOR_K = 0.001;


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
               GeoPoint closestPoint = findCLosestIntersection(ray);
               if (closestPoint == null) {
                   imageWriter.writePixel(column, row, background);
               } else {
                   imageWriter.writePixel(column, row, calcColor(closestPoint, ray).getColor());
               }
           }
       }
   }

    /***
     * function to calculate the color for the closes point to the ray
     * @param intersection point, for calculating the color
     */
   private Color calcColor(GeoPoint intersection, Ray r){
      /* Color color = scene.get_ambientlight().get_intensity();
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
               if (unshaded(l, n, intersection)){
                   Color lightIntensity = lightSource.getIntensity(intersection.point);
                   color = color.add(calcDiffusive(kd, l, n, lightIntensity), calcSpecular(ks, l, n, v, nShininess, lightIntensity));
               }
           }
       }
       return color;*/
      return calcColor(intersection, r, MAX_CALC_COLOR_LEVEL, 1);
   }

    /***
     *
     * @param gp
     * @param ray
     * @return
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, double k){
        Color color = gp.geometry.get_emission(); // remove ambition light

        Vector v = gp.point.subtruct(scene.get_camera().getPlaceable()).normalized();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material =gp.geometry.get_material();
        int nShininess = material.getnShininess();
        double kd = material.getkD();
        double ks = material.getkS();

        for (LightSource lightSource : scene.get_lights()) {
            Vector l = lightSource.getL(gp.point);
            if (signum(n.dotProduct(l)) == signum(n.dotProduct(v)) && !isZero(n.dotProduct(l)) && !isZero(n.dotProduct(v))) {
                if (unshaded(lightSource, l, n, gp)){ // הוספתי פה שהפונקצייה תקבל גם light source
                    Color lightIntensity = lightSource.getIntensity(gp.point);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity), calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }

        if (level == 1) return Color.BLACK;

        double kr = gp.geometry.get_material().getkR();
        double kkr = k*kr;
        if (kkr > MIN_CALC_COLOR_K){
            Ray reflectedRay = constructReflectedRay(n, gp.point, ray);
            GeoPoint reflectedPoint = findCLosestIntersection(reflectedRay);
            if (reflectedPoint != null)
                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr));
        }

        double kt = gp.geometry.get_material().getkT();
        double kkt = k*kt;
        if (kkt > MIN_CALC_COLOR_K){
            Ray refractedRay = constructRefracterRay(gp.point, ray);
            GeoPoint refractedPoint = findCLosestIntersection(refractedRay);
            if (refractedPoint != null){
                color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt));
            }
        }
        return color;
    }


    /**
     * function to calculate the diffusive
     * @param kd
     * @param l
     * @param n
     * @param lightIntensity
     * @return
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        return lightIntensity.scale(kd * Math.abs(l.dotProduct(n)));
    }

    /**
     * function to calculate the specular
     * @param ks
     * @param l
     * @param n
     * @param v
     * @param nShininess
     * @param lightIntensity
     * @return
     */
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
       if(intersectionsPoints == null)
           return null;
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


    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source

        Ray lightRay = new Ray(geopoint.point, lightDirection, n);
        List<GeoPoint> intersections = scene.get_geometries().findIntsersections(lightRay);
        if (intersections == null) return true;

        double lightDistance = light.getDistance(geopoint.point);
        for (GeoPoint gp: intersections){
            if (alignZero(gp.point.distance(geopoint.point) -lightDistance) <= 0 &&
            gp.geometry.get_material().getkT() == 0)
                return false;
        }
        return true;
    }

    /***
     *
     * @param point
     * @param r
     * @return
     */
    private Ray constructRefracterRay(Point3D point, Ray r) {
        return new Ray(point, r.get_dir());
    }
    /***
     * construct reflected ray******************
     * @param n
     * @param point3D
     * @param r
     * @return
     */
    public Ray constructReflectedRay(Vector n, Point3D point3D, Ray r){
        return new Ray(point3D, r.get_dir().add(n.scale(-2 * r.get_dir().dotProduct(n))),n);
    }

    /***
     * the function find all the intersection of a given ray, and also the closest point to that given
     * ray. it doing the job of two given function: 1) get closest point, 2) find all intersection.
     * @param ray receive a ray to check all the intersection with the geometries objects.
     * @return point that is the closest intersection to the geometries.
     */
    private GeoPoint findCLosestIntersection(Ray ray){
        return getClosesPoint(scene.get_geometries().findIntsersections(ray));
    }
}
