package renderer;

import elements.LightSource;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.*;
import primitives.Vector;
import scene.Scene;
import elements.Camera;

import java.time.temporal.ValueRange;
import java.util.*;

import geometries.Intersectable.GeoPoint;
import static geometries.Intersectable.GeoPoint;
import static java.lang.Math.*;
import static java.lang.System.out;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/***
 * class Render, responsible to take all the calculation of ray intersections
 * and light, and to create view plane matrix that represent the picture from it.
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

               // receive boundary and center rays in the current pixel.
               LinkedList<Ray> boundaryRays = camera.constructBoundingRaysThroughPixel(Nx, Ny, column, row, distance, width, height);

               LinkedList<Color> colors = new LinkedList<Color>();

               // get color of boundary rays of the pixel
               for (int i = 0; i < boundaryRays.size(); i++){
                   colors.add(getColorIntersection(boundaryRays.get(i)));
               }
               if (!IsDifferentColors(colors)){ // if the ray corners are the same color.
                   imageWriter.writePixel(column, row, getColorIntersection(boundaryRays.get(0)).getColor());
               }
               else
               { // average colors from all rays
                   LinkedList<Ray> rays = camera.constructRaysThroughPixel(Nx, Ny, column, row, distance, width, height);
                   Color color = new Color(Color.BLACK);
                   for (Ray ray: rays){
                       color = color.add(getColorIntersection(ray));
                   }
                   color = color.reduce(rays.size());
                   imageWriter.writePixel(column, row, color.getColor());
               }
           }
       }
   }

    /***
     * function to check if all the colors in the corners and in the center of the pixel are equal or different.
     * @param colors receive corners and center colors
     * @return equals or not
     */
    private boolean IsDifferentColors(LinkedList<Color> colors){
        for (int i = 1; i < colors.size(); i++){
            if (!colors.get(i).getColor().equals(colors.get(0).getColor()))
                return true;
        }
        return false;
    }

    /***
     * function to evaluate the color of the intersection of the ray with geoPoint.
     * @param ray given ray
     * @return the intersection color
     */
    private Color getColorIntersection(Ray ray){
        GeoPoint closestPoint = findCLosestIntersection(ray);
        if (closestPoint == null) {
            return scene.get_background();
        } else {
            return calcColor(closestPoint, ray);
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
      return calcColor(intersection, r, MAX_CALC_COLOR_LEVEL, 1).add(scene.get_ambientlight().get_intensity());
   }

    /***
     * function to calculate the color
     * @param gp geo point
     * @param ray ray
     * @return sum of the colors
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
            if (signum(n.dotProduct(l)) == signum(n.dotProduct(v)) && n.dotProduct(l)*n.dotProduct(v) > 0) {
                if (unshaded(lightSource, l, n, gp)) { // return true if there is'nt ABSOLUTELY shadow

                    double ktr = transparency(lightSource, l, n, gp);

                    if (ktr * k > MIN_CALC_COLOR_K) {
                        Color lightIntensity = lightSource.getIntensity(gp.point).scale(ktr);
                        color = color.add(calcDiffusive(kd, l, n, lightIntensity), calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                    }
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
                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
        }

        double kt = gp.geometry.get_material().getkT();
        double kkt = k*kt;
        if (kkt > MIN_CALC_COLOR_K){
            Ray refractedRay =  constructRefractedRay(n,gp.point, ray) ;
            GeoPoint refractedPoint = findCLosestIntersection(refractedRay);
            if (refractedPoint != null){
                color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
            }
        }
        return color;
    }
    /***
     * function to check if the color are different on the list
     * @param boundaryKtr list of boundary values
     * @return true if the value are different
     */
    private boolean IsKtrBoundaryDifferent(LinkedList<Double> boundaryKtr){
        for (int i = 1; i < boundaryKtr.size(); i++){
            if (boundaryKtr.get(i) != boundaryKtr.get(0))
                return true;
        }
        return false;
    }
    /**
     * function to evaluate the diffusive factor of the light, according to "Phong model"
     * the diffusive factor represent the light reflected from the object to everywhere
     * (in 180 degree from the point of the intersection between object and source light ray).
     * @param kd the brightness
     * @param l vector from the light to the object.
     * @param n vector normal to the object.
     * @param lightIntensity the intensity of the light.
     * @return the diffusive color
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        return lightIntensity.scale(kd * Math.abs(l.dotProduct(n)));
    }

    /**
     * function to evaluate the specular factor of the light, according to "Phong model",
     * is the direct light that is reflected from the object.
     * @param ks factor of the diffusive component
     * @param l vector from the light to the object.
     * @param n vector normal to the object.
     * @param v the vector reflected from the object.
     * @param nShininess the object shininess.
     * @param lightIntensity the intensity of the light.
     * @return the specular color
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
       Vector r = l.subtruct(n.scale(2*l.dotProduct(n))).normalized();  // r = l - 2*(l-n)*n
       double vr = alignZero(v.dotProduct(r));

        if(vr >= 0)
            return Color.BLACK;
        return new Color(lightIntensity.scale(ks * pow(-vr , nShininess)));
    }


    /***
     * function receive some points, and return the point with the minimal distance
     * from the ray
     * @param intersectionsPoints several points
     * @return the closest point to the ray
     */
   private GeoPoint getClosesPoint(Ray ray, List<GeoPoint> intersectionsPoints){
       if(intersectionsPoints == null)
           return null;
       Point3D cameraPoint = ray.get_p0();
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

    /***
     * function to check if the original ray is moving through any object,
     * that will make a shadow on another object.
     * @param light the light on the object.
     * @param l vector to the object from the camera through the "glass"
     * @param n normal to the object.
     * @param geopoint point on the object.
     * @return if there is any ABSOLUTELY shadow return false, otherwise return true.
     */
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
     * function to evaluate the factor of the boundary values of the light source
     * @param ls the light through the "glass"
     * @param l vector of the light
     * @param n normal to the object
     * @param geopoint point on the object
     * @return list that contain the boundary values
     */
    private LinkedList<Double> boundaryTransparency(LightSource ls, Vector l, Vector n, GeoPoint geopoint){
        Vector lightDirection = l.scale(-1); // from point to light source

        LinkedList<Ray> lightRays = new LinkedList<Ray>();

    }
    /***
     * function to evaluate the factor of all the reflection on the object,
     * we take all the glasses that the original ray move through it and evaluate
     * the effect on the pixel.
     * @param ls the light through the "glass"
     * @param l vector of the light
     * @param n normal to the object
     * @param geopoint point on the object
     * @return transparency factor of all the "glasses" together.
     */
    private double transparency(LightSource ls, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source

        LinkedList<Ray> lightRays = new LinkedList<Ray>();

        if (ls.getRaduis() != 0){ //
            // initialize cartesian axis inside the light source (x,y)
            Vector x  = new Vector(-lightDirection.get_head().get_z().get_coord(),0,lightDirection.get_head().get_x().get_coord()).normalized();
            Vector y = new Vector(x.crossProduct(lightDirection)).normalized();

            // first check if we need to create group of N rays, if not don't create
            LinkedList<Ray> boundaryRays = boundaryTransparency(ls, l, n, geopoint, x, y); // receive boundary's value
            LinkedList<Double> boundaryKtr =
            if (!IsKtrBoundaryDifferent(boundaryRays.)) {
                lightRays.add(boundaryRays.get(0));
            }
            else {
                ktr = transparencyMultipleRays(ls, l, n, geopoint, x, y); // ktr = factor of light transparency to the GeoPoint.(average of the group)
            }

            return ktr;
            // create group of rays
            for (int i = 0; i < 50; i++) {

                Random rand = new Random();
                double cos = -1 + 2 * rand.nextDouble(); // rand(-1,1)
                double sin = Math.sqrt(1 - Math.pow(cos, 2)); // sin^2 + cos^2 = 1
                double d = -ls.getRaduis() + 2 * ls.getRaduis() * rand.nextDouble(); // rand(-r,r)

                // the point in the light source 2D space
                double _x = cos * d; // x coordinate
                double _y = sin * d; // y coordinate

                double distance = ls.getDistance(geopoint.point); // distance from GeoPoint to the light source
                Point3D pCenter = geopoint.point.add(lightDirection.scale(distance)); // center of the light source

                // pIJ = pCenter + x * _x + y * _y
                Point3D pIJ = pCenter;
                if (_x != 0) pIJ = pIJ.add(x.scale(_x));
                if (_y != 0) pIJ = pIJ.add(y.scale(_y));

                // add ray to the list of rays
                lightRays.add(new Ray(geopoint.point, new Vector(pIJ.subtruct(geopoint.point)).normalized(),n));
            }

        }
        else
        {
            lightRays.add(new Ray(geopoint.point, lightDirection, n));
        }

        // calculate the average of the group rays
        ArrayList<GeoPoint> intersections;
        double sumOfKtr = 0;
        for (Ray ray: lightRays){
            intersections = scene.get_geometries().findIntsersections(ray); // all the intersection of specific ray with GeoPoints
            sumOfKtr += getKtr(intersections,ls, geopoint);
        }
        //////////////////
        // first check if we need to create group of N rays, if not don't create
        LinkedList<Double> boundaryKtr = boundaryTransparency(lightSource, l, n, gp); // receive boundary's value
        double ktr;
        if (!IsKtrBoundaryDifferent(boundaryKtr)) {
            ktr = boundaryKtr.get(0);
        }
        else {
            ktr = transparency(lightSource, l, n, gp); // ktr = factor of light transparency to the GeoPoint.(average of the group)
        }
        //////////////////
       return sumOfKtr / lightRays.size();
    }

    /***
     *  function to evaluate the ktr, with given information about the intersection.
     * @param intersections list of intersection
     * @param ls the light source
     * @param geopoint point on the geometries
     * @return ktr
     */
    private double getKtr(ArrayList<GeoPoint> intersections, LightSource ls, GeoPoint geopoint){
        if (intersections == null) return 1.0;

        double lightDistance = ls.getDistance(geopoint.point);
        double ktr = 1.0;
        for (GeoPoint gp: intersections){
            if (alignZero(gp.point.distance(geopoint.point) -lightDistance) <= 0 ) {
                ktr *= gp.geometry.get_material().getkT();
                if (ktr < MIN_CALC_COLOR_K)
                    return 0.0;
            }
        }
        return ktr;
    }
    /***
     * function to evaluate the "Refracted Ray" - the ray that goes into the object,
     * and move a little bit. (for example: straw inside glass of water)
     * @param point the intersection of the ray with the object.
     * @param r the original ray from the camera.
     * @return the resulted ray, after the calculation.
     */
    private Ray constructRefractedRay(Vector n,Point3D point, Ray r) {
        return new Ray(point, r.get_dir() , n);
    }

    /***
     * function to evaluate the reflected ray from the object, (Mirror)
     * @param n vector normal for calculation
     * @param point3D the intersection point on the object.
     * @param r the original ray, from the camera to the object.
     * @return ray reflected to the original ray.
     */
    public Ray constructReflectedRay(Vector n, Point3D point3D, Ray r){
        Vector dir=new Vector(r.get_dir());
        dir.normalize();
        Vector vNormal=new Vector(n);
        vNormal.normalize();

        return new Ray(point3D , dir.subtruct(vNormal.scale(2*dir.dotProduct(vNormal))).normalized() ,n);
    }


    /***
     * the function find all the intersection of a given ray, and also the closest point to that given
     * ray. it doing the job of two given function: 1) get closest point, 2) find all intersection.
     * @param ray receive a ray to check all the intersection with the geometries objects.
     * @return point that is the closest intersection to the geometries.
     */
    private GeoPoint findCLosestIntersection(Ray ray){
        return getClosesPoint(ray,scene.get_geometries().findIntsersections(ray));
    }
}