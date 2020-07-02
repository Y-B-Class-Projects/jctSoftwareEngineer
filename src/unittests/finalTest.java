package unittests;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;
public class finalTest {
    @Test
    public void sphereDirectional() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(50, 0, -1000), new Vector(-0.05, 0, 1).normalized(), new Vector(0, -1, 0)));
        //scene.setCamera(new Camera(new Point3D(700, 0, -1000), new Vector(-0.7, -0.5, 1), new Vector(0,-1,0)));

        //scene.setCamera(new Camera(new Point3D(700, 0, -1000), new Vector(-700,-40,1000).normalized(),
        //        new Vector(69.67552368290157, -1219.321664450982, -8.192557743313955E-12).normalized()));

        scene.setDistance(900);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.white), 0.25));

        Point3D A = new Point3D(-100,-100,200);
        Point3D B = new Point3D(-100,100,200);
        Point3D C = new Point3D(110,100,200);
        Point3D D = new Point3D(110,-100,200);

        Point3D E = new Point3D(-100,100,-100);
        Point3D F = new Point3D(-100,-100,-100);

        Point3D G = new Point3D(110,100,-100);
        Point3D H = new Point3D(110,-100,-100);

        Material WallMaterial = new Material(1, 0, 80, 0,0);

        scene.addGeometries(new Triangle(new Color(java.awt.Color.black),WallMaterial , A,B,C));
        scene.addGeometries(new Triangle(new Color(java.awt.Color.black),WallMaterial, A,C,D));

        //שמאל
        scene.addGeometries(new Triangle(new Color(java.awt.Color.black),WallMaterial ,E,A,B));
        scene.addGeometries(new Triangle(new Color(java.awt.Color.black),WallMaterial, F, A,E));

        //רצפה
        scene.addGeometries(new Triangle(new Color(java.awt.Color.black),new Material(0, 0, 80, 0,0.8) , B,E,G));
        scene.addGeometries(new Triangle(new Color(java.awt.Color.black),new Material(0, 0, 80, 0,0.8) , B,G,C));

        scene.addGeometries(new Triangle(new Color(java.awt.Color.black),WallMaterial, G,H,D));
        scene.addGeometries(new Triangle(new Color(java.awt.Color.black),WallMaterial , G,D,C));

        scene.addGeometries(new Triangle(new Color(java.awt.Color.black),WallMaterial , A,H,D));
        scene.addGeometries(new Triangle(new Color(java.awt.Color.black),WallMaterial , A,H,F));

        Point3D Tr1_1 = new Point3D(0,90,100);
        Point3D Tr1_2 = new Point3D(-60,-10,100);
        Point3D Tr1_3 = new Point3D(60,-10,100);

        Point3D Tr1_center1 = Tr1_1.add((Tr1_2.subtruct(Tr1_1)).scale(0.5));
        Point3D Tr1_center2 = Tr1_2.add((Tr1_3.subtruct(Tr1_2)).scale(0.5));
        Point3D Tr1_center3 = Tr1_3.add((Tr1_1.subtruct(Tr1_3)).scale(0.5));

        Point3D Tr2_1 = new Point3D(0,-40,100);
        Point3D Tr2_2 = new Point3D(60,60,100);
        Point3D Tr2_3 = new Point3D(-60,60,100);

        Point3D Tr2_center1 = Tr2_1.add((Tr2_2.subtruct(Tr2_1)).scale(0.5));
        Point3D Tr2_center2 = Tr2_2.add((Tr2_3.subtruct(Tr2_2)).scale(0.5));
        Point3D Tr2_center3 = Tr2_3.add((Tr2_1.subtruct(Tr2_3)).scale(0.5));



        scene.addGeometries(new Cylinder(new Color(java.awt.Color.blue), new Material(0.5, 0.5, 60 , 0, 0) ,5 , Tr1_center1 ,Tr1_1.subtruct(Tr1_2),Tr1_1.distance(Tr1_2)));
        scene.addGeometries(new Cylinder(new Color(java.awt.Color.blue), new Material(0.5, 0.5, 60, 0, 0) ,5 , Tr1_center2 ,Tr1_2.subtruct(Tr1_3),Tr1_2.distance(Tr1_3)));
        scene.addGeometries(new Cylinder(new Color(java.awt.Color.blue), new Material(0.5, 0.5, 60, 0, 0) ,5 , Tr1_center3 ,Tr1_3.subtruct(Tr1_1),Tr1_3.distance(Tr1_1)));

        scene.addGeometries(new Cylinder(new Color(java.awt.Color.blue), new Material(0.5, 0.5, 60, 0, 0) ,5 , Tr2_center1 ,Tr2_1.subtruct(Tr2_2),Tr2_1.distance(Tr2_2)));
        scene.addGeometries(new Cylinder(new Color(java.awt.Color.blue), new Material(0.5, 0.5, 60, 0, 0) ,5 , Tr2_center2 ,Tr2_2.subtruct(Tr2_3),Tr2_2.distance(Tr2_3)));
        scene.addGeometries(new Cylinder(new Color(java.awt.Color.blue), new Material(0.5, 0.5, 60, 0, 0) ,5 , Tr2_center3 ,Tr2_3.subtruct(Tr2_1),Tr2_3.distance(Tr2_1)));


        scene.addGeometries(new Sphere(new Color(java.awt.Color.blue), new Material(0.5, 0.5, 60, 0, 0) , new Point3D(Tr1_1) , 5));
        scene.addGeometries(new Sphere(new Color(java.awt.Color.blue), new Material(0.5, 0.5, 60, 0, 0) , new Point3D(Tr1_2) , 5));
        scene.addGeometries(new Sphere(new Color(java.awt.Color.blue), new Material(0.5, 0.5, 60, 0, 0) , new Point3D(Tr1_3) , 5));

        scene.addGeometries(new Sphere(new Color(java.awt.Color.blue), new Material(0.5, 0.5, 60, 0, 0) , new Point3D(Tr2_1) , 5));
        scene.addGeometries(new Sphere(new Color(java.awt.Color.blue), new Material(0.5, 0.5, 60, 0, 0) , new Point3D(Tr2_2) , 5));
        scene.addGeometries(new Sphere(new Color(java.awt.Color.blue), new Material(0.5, 0.5, 60, 0, 0) , new Point3D(Tr2_3) , 5));

        //scene.addLights(new DirectionalLight(new Color(300, 150, 150) , new Vector(0,-1,-1)));
        scene.addLights(new pointLight(new Color(250, 250, 250), new Point3D(0, -90, 0),1, 0.0005, 0.0005 ,0));
        scene.addLights(new spotLight(new Color(300, 300, 300), new Point3D(0, 100, -250), new Vector(0,-55,250).normalized() ,1, 0.000001, 0.00001 , 0));


        ImageWriter imageWriter = new ImageWriter("fianlTest", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene).setMultithreading(16).setDebugPrint();

        render.renderImage();
        render.writeToImage();
    }
}
